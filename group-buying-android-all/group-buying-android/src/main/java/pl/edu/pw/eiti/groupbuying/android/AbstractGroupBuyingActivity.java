package pl.edu.pw.eiti.groupbuying.android;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import pl.edu.pw.eiti.groupbuying.android.api.City;
import pl.edu.pw.eiti.groupbuying.android.fragment.CityOffersFragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public abstract class AbstractGroupBuyingActivity extends SherlockFragmentActivity {
		
	private ProgressDialog progressDialog;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
        	if(getApplicationContext().getCities() == null && getApplicationContext().getSelectedCity() == null && savedInstanceState.containsKey(CityOffersFragment.CITY_TAG) && savedInstanceState.containsKey(CityOffersFragment.CITIES_TAG)) {
            	getApplicationContext().setSelectedCity((City) savedInstanceState.getSerializable(CityOffersFragment.CITY_TAG));
        		getApplicationContext().setCities((ArrayList<City>) savedInstanceState.getSerializable(CityOffersFragment.CITIES_TAG));
        	}
        }
	}
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CityOffersFragment.CITY_TAG, getApplicationContext().getSelectedCity());
        outState.putSerializable(CityOffersFragment.CITIES_TAG, getApplicationContext().getCities());
    }
    
	public GroupBuyingApplication getApplicationContext() {
		return (GroupBuyingApplication) super.getApplicationContext();
	}
	
	public void showProgressDialog() {
		showProgressDialog("Loading. Please wait...");
	}
	
	public void showProgressDialog(String message) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
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
	
	protected void processException(Exception e) {
		if (e != null) {
			if (e instanceof ResourceAccessException) {
				displayNetworkError();
			} else if (e instanceof HttpClientErrorException) {
				HttpClientErrorException httpError = (HttpClientErrorException) e;
				if (httpError.getStatusCode() ==  HttpStatus.UNAUTHORIZED) {
					displayAuthorizationError();
				}
			}
		}
	}
	
	protected void displayNetworkError() {
		Toast toast = Toast.makeText(this, "A problem occurred with the network connection while attempting to communicate with Group Buying.", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	protected void displayAuthorizationError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You are not authorized to connect to Group Buying. Please reauthorize the app.");
		builder.setCancelable(false);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
		     	signOut();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	protected void signOut() {
    	getApplicationContext().getConnectionRepository().removeConnections(getApplicationContext().getConnectionFactory().getProviderId());
    	startActivity(new Intent(this, MyCouponsIntermediateActivity.class));
    	finish();
    }

}
