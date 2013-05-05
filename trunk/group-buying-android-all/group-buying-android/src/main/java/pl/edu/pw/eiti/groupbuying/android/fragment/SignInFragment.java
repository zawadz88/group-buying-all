package pl.edu.pw.eiti.groupbuying.android.fragment;

import static pl.edu.pw.eiti.groupbuying.android.util.Constants.TAG;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;
import pl.edu.pw.eiti.groupbuying.android.connect.GroupBuyingConnectionFactory;
import pl.edu.pw.eiti.groupbuying.android.fragment.util.SignInListener;
import pl.edu.pw.eiti.groupbuying.android.task.AbstractGroupBuyingTask;
import pl.edu.pw.eiti.groupbuying.android.task.SignInTask;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.android.task.util.TaskResult;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.androidquery.AQuery;

public class SignInFragment extends Fragment implements AsyncTaskListener {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private ProgressDialog progressDialog;
	private GroupBuyingApplication application;
	private SignInListener signInListener;

	public static SignInFragment newInstance() {
		SignInFragment fragment = new SignInFragment();
		return fragment;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.application = (GroupBuyingApplication) activity.getApplicationContext();
		try {
			signInListener = (SignInListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SignInListener");
        }
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		signInListener = null;
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
		AQuery aq = new AQuery(getActivity(), rootView);
		usernameEditText = aq.id(R.id.username).getEditText();
		passwordEditText = aq.id(R.id.password).getEditText();
		aq.id(R.id.cancel_button).clicked(new OnClickListener() {
			@Override
			public void onClick(final View view) {
				signInListener.onSignInCancelled();
			}
		});

		aq.id(R.id.sign_in_button).clicked(new OnClickListener() {
			@Override
			public void onClick(final View view) {
				if (validateFormData()) {
					showProgressDialog("Signing in...");
					MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
					String username = usernameEditText.getText().toString().trim();
					String password = passwordEditText.getText().toString().trim();
					formData.add("grant_type", "password");
					formData.add("username", username);
					formData.add("password", password);
					formData.add("client_id", application.getClientId());
					formData.add("client_secret", application.getClientSecret());
					formData.add("scope", "read,write");
					InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(usernameEditText.getWindowToken(), 0);
					imm.hideSoftInputFromWindow(passwordEditText.getWindowToken(), 0);
					new SignInTask(application.getApiUrlBase() + "oauth/token", formData, SignInFragment.this).execute();
				} else {
					displayAppAuthorizationError("Your email or password was entered incorrectly.");
				}
			}
		});
		return rootView;
	}

	private boolean validateFormData() {
		String username = usernameEditText.getText().toString().trim();
		String password = passwordEditText.getText().toString().trim();
		if (username.length() > 0 && password.length() > 0) {
			return true;
		}
		return false;
	}

	private void displayAppAuthorizationError(String message) {
		if (getActivity() != null) {
			new AlertDialog.Builder(getActivity()).setMessage(message).setCancelable(false).setPositiveButton("OK", null).create().show();
		}
	}

	private AccessGrant extractAccessGrant(Map<String, Object> result) {
		return new AccessGrant((String) result.get("access_token"), (String) result.get("scope"), (String) result.get("refresh_token"), getIntegerValue(result, "expires_in"));
	}

	// Retrieves object from map into an Integer, regardless of the object's
	// actual type. Allows for flexibility in object type (eg, "3600" vs 3600).
	private Integer getIntegerValue(Map<String, Object> map, String key) {
		try {
			return Integer.valueOf(String.valueOf(map.get(key))); // normalize
																	// to String
																	// before
																	// creating
																	// integer
																	// value;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public void onTaskFinished(AbstractGroupBuyingTask<?> task, TaskResult result) {
		dismissProgressDialog();
		if (result.equals(TaskResult.SUCCESSFUL)) {
			try {
				Map<String, Object> responseBody = ((SignInTask) task).getResponseBody();
				// Persist the connection and AccessGrant to the repository
				AccessGrant accessGrant = extractAccessGrant(responseBody);
				GroupBuyingConnectionFactory connectionFactory = application.getConnectionFactory();
				ConnectionRepository connectionRepository = application.getConnectionRepository();
				Connection<GroupBuyingApi> connection = connectionFactory.createConnection(accessGrant);
				connectionRepository.addConnection(connection);
				if (signInListener != null) {
					signInListener.onSignInSuccessful();
				}
			} catch (Exception e) {
				String message = "Signin error occured. Please try again in a few minutes.";
				Log.e(TAG, message, e);
				displayAppAuthorizationError(message);
			}
		} else if (result.equals(TaskResult.FAILED)) {
			Exception exception = task.getException();
			if (exception != null) {
				String message;
				if (exception instanceof HttpClientErrorException) {
					if ((((HttpClientErrorException) exception).getStatusCode() == HttpStatus.BAD_REQUEST) || ((HttpClientErrorException) exception).getStatusCode() == HttpStatus.UNAUTHORIZED) {
						message = "Your email or password was entered incorrectly.";
					} else {
						message = "A problem occurred with the network connection. Please try again in a few minutes.";
					}
				} else if (exception instanceof DuplicateConnectionException) {
					message = "The connection already exists.";
				} else if (exception instanceof ResourceAccessException) {
					message = "Server is not responding. Please try again in a few minutes.";
				} else {
					message = "Unexpected error occured. Please try again in a few minutes.";
				}

				displayAppAuthorizationError(message);
			}
		}
	}

	public void showProgressDialog() {
		showProgressDialog("Loading. Please wait...");
	}

	public void showProgressDialog(String message) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setIndeterminate(true);
		}

		progressDialog.setMessage(message);
		progressDialog.show();
	}

	public void dismissProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
}
