package pl.edu.pw.eiti.groupbuying.android;

import org.springframework.http.HttpStatus;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import pl.edu.pw.eiti.groupbuying.android.api.Coupon;
import pl.edu.pw.eiti.groupbuying.android.fragment.CouponListFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.LoadingFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.NoInternetFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.SignInFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.util.NoInternetListener;
import pl.edu.pw.eiti.groupbuying.android.fragment.util.SignInListener;
import pl.edu.pw.eiti.groupbuying.android.task.AbstractGroupBuyingTask;
import pl.edu.pw.eiti.groupbuying.android.task.DownloadCouponListTask;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.android.task.util.TaskResult;
import pl.edu.pw.eiti.groupbuying.android.util.NetUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MyCouponsActivity extends AbstractGroupBuyingActivity implements SignInListener, AsyncTaskListener, NoInternetListener {
	private static final String COUPONS_TAG = "coupons";

	protected static final String TAG = MyCouponsActivity.class.getSimpleName();

	private static final int LOADING = 0;
	private static final int COUPONS = 1;
	private static final int SIGN_IN = 2;
	private static final int NO_INTERNET = 3;
	private static final int FRAGMENT_COUNT = NO_INTERNET + 1;
	private static final String FRAGMENT_PREFIX = "fragment";
	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	private boolean restoredFragment = false;

	private Coupon[] coupons;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_coupons);

		if (savedInstanceState != null && savedInstanceState.containsKey(COUPONS_TAG)) {
			coupons = (Coupon[]) savedInstanceState.getSerializable(COUPONS_TAG);
		}

		for (int i = 0; i < fragments.length; i++) {
			restoreFragment(savedInstanceState, i);
		}

		if (coupons == null && NetUtils.isOnline(this) && isConnected()) {
			new DownloadCouponListTask(this, getApplicationContext()).execute();
		}

	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		if (restoredFragment) {
			return;
		}
		if (coupons != null) {
			showFragment(COUPONS);
		} else {
			if (NetUtils.isOnline(this)) {
				if (isConnected()) {
					showFragment(LOADING);
				} else {
					showFragment(SIGN_IN);
				}
			} else {
				showFragment(NO_INTERNET);
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		FragmentManager manager = getSupportFragmentManager();
		// Since we're only adding one Fragment at a time, we can only save one.
		Fragment f = manager.findFragmentById(R.id.body_frame);
		for (int i = 0; i < fragments.length; i++) {
			if (fragments[i] == f) {
				manager.putFragment(outState, getBundleKey(i), fragments[i]);
				break;
			}
		}
		outState.putSerializable(COUPONS_TAG, coupons);
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.my_coupons_menu, menu);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case android.R.id.home:
			intent = new Intent(this, MainMenuActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.options_menu_settings:
			break;
		case R.id.options_menu_offers:
			intent = new Intent(this, MainMenuActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.options_menu_logout:
			signOut();
			coupons = null;
			showFragment(SIGN_IN);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void restoreFragment(Bundle savedInstanceState, int fragmentIndex) {
		Fragment fragment = null;
		if (savedInstanceState != null) {
			FragmentManager manager = getSupportFragmentManager();
			fragment = manager.getFragment(savedInstanceState, getBundleKey(fragmentIndex));
		}
		if (fragment != null) {
			fragments[fragmentIndex] = fragment;
			restoredFragment = true;
		} else {
			switch (fragmentIndex) {
			case COUPONS:
				if (coupons != null) {
					fragments[COUPONS] = CouponListFragment.newInstance(coupons);
				}
				break;
			case NO_INTERNET:
				fragments[NO_INTERNET] = NoInternetFragment.newInstance();
				break;
			case LOADING:
				fragments[LOADING] = LoadingFragment.newInstance(getString(R.string.loading_coupons_message));
				break;
			case SIGN_IN:
				fragments[SIGN_IN] = SignInFragment.newInstance();
				break;
			default:
				Log.w(TAG, "OfferActivity: invalid fragment index: " + fragmentIndex);
				break;
			}
		}
	}

	public void showFragment(int fragmentNo) {
		if (fragmentNo == COUPONS && fragments[COUPONS] == null) {
			fragments[COUPONS] = CouponListFragment.newInstance(coupons);
		}
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.body_frame, fragments[fragmentNo]).commit();
	}

	private String getBundleKey(int index) {
		return FRAGMENT_PREFIX + Integer.toString(index);
	}

	@Override
	public void onSignInSuccessful() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				showFragment(LOADING);
				new DownloadCouponListTask(MyCouponsActivity.this, getApplicationContext()).execute();
			}
		});

	}

	@Override
	public void onSignInCancelled() {
		finish();
	}

	@Override
	public void onDeviceOnline() {
		if (isConnected()) {
			showFragment(LOADING);
			new DownloadCouponListTask(this, getApplicationContext()).execute();
		} else {
			showFragment(SIGN_IN);
		}
	}

	@Override
	public void onDeviceOffline() {
	}

	@Override
	public void onTaskFinished(final AbstractGroupBuyingTask<?> task, final TaskResult result) {

		if (result.equals(TaskResult.SUCCESSFUL)) {
			Coupon[] downloadedCoupons = ((DownloadCouponListTask) task).getCouponList();
			if (downloadedCoupons == null) {
				// TODO show error stub?
			} else {
				coupons = downloadedCoupons;
				showFragment(COUPONS);
			}
		} else if (result.equals(TaskResult.FAILED)) {
			Exception e = task.getException();
			if (e instanceof ResourceAccessException) {
				showFragment(NO_INTERNET);
			} else if (e instanceof HttpClientErrorException) {
				HttpClientErrorException httpError = (HttpClientErrorException) e;
				if (httpError.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					getApplicationContext().getConnectionRepository().removeConnections(getApplicationContext().getConnectionFactory().getProviderId());
					showFragment(ConfirmPaymentActivity.SIGN_IN);
				}
			} else if (e instanceof ExpiredAuthorizationException) {
				getApplicationContext().getConnectionRepository().removeConnections(getApplicationContext().getConnectionFactory().getProviderId());
				showFragment(ConfirmPaymentActivity.SIGN_IN);
			} else {
				showFragment(NO_INTERNET);
			}

		}

	}

}
