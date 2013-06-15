/*******************************************************************************
 * Copyright (c) 2013 Piotr Zawadzki.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Piotr Zawadzki - initial API and implementation
 ******************************************************************************/
package pl.edu.pw.eiti.groupbuying.partner.android;

import org.springframework.http.HttpStatus;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import pl.edu.pw.eiti.groupbuying.partner.android.api.ClaimResponse;
import pl.edu.pw.eiti.groupbuying.partner.android.api.CouponInfo;
import pl.edu.pw.eiti.groupbuying.partner.android.fragment.LoadingFragment;
import pl.edu.pw.eiti.groupbuying.partner.android.fragment.NoInternetFragment;
import pl.edu.pw.eiti.groupbuying.partner.android.fragment.SelectClaimOptionFragment;
import pl.edu.pw.eiti.groupbuying.partner.android.fragment.SignInFragment;
import pl.edu.pw.eiti.groupbuying.partner.android.fragment.util.AlertDialogListener;
import pl.edu.pw.eiti.groupbuying.partner.android.fragment.util.NoInternetListener;
import pl.edu.pw.eiti.groupbuying.partner.android.fragment.util.SignInListener;
import pl.edu.pw.eiti.groupbuying.partner.android.task.AbstractGroupBuyingTask;
import pl.edu.pw.eiti.groupbuying.partner.android.task.ClaimCouponTask;
import pl.edu.pw.eiti.groupbuying.partner.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.partner.android.task.util.TaskResult;
import pl.edu.pw.eiti.groupbuying.partner.android.util.NetUtils;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public class ClaimCouponsActivity extends AbstractGroupBuyingActivity implements SignInListener, AlertDialogListener, NoInternetListener, AsyncTaskListener {

    public static final int OPTIONS = 0;
    public static final int SIGN_IN = 1;
	private static final int LOADING = 2;
    private static final int NO_INTERNET = 3;
    private static final int FRAGMENT_COUNT = NO_INTERNET + 1;
    private static final String FRAGMENT_PREFIX = "fragment";
    private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
    
	protected static final String TAG = ClaimCouponsActivity.class.getSimpleName();

    private boolean restoredFragment = false;
	private CouponInfo couponInfo;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_claim_coupon);
		
		for(int i = 0; i < fragments.length; i++) {
            restoreFragment(savedInstanceState, i);
        }
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		FragmentManager manager = getSupportFragmentManager();
		// Since we're only adding one Fragment at a time, we can only save one.
		Fragment f = manager.findFragmentById(R.id.fragment_content);
		for (int i = 0; i < fragments.length; i++) {
			if (fragments[i] == f) {
				manager.putFragment(outState, getBundleKey(i), fragments[i]);
				break;
			}
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (restoredFragment) {
            return;
        }
		FragmentManager manager = getSupportFragmentManager();
		if(manager.getBackStackEntryCount() == 0) {
			showFragment(OPTIONS, false);
		}
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
                case OPTIONS:
                    fragments[OPTIONS] = SelectClaimOptionFragment.newInstance();  
                    break;                
                case SIGN_IN:
                    fragments[SIGN_IN] = SignInFragment.newInstance();
                    break;     
                case LOADING:
                    fragments[LOADING] = LoadingFragment.newInstance("Sending info to server");
                    break;       
                case NO_INTERNET:
                    fragments[NO_INTERNET] = NoInternetFragment.newInstance();
                    break;
                default:
                    Log.w(TAG, "ClaimCouponsActivity: invalid fragment index: " + fragmentIndex);
                    break;
            }
        }
	}
	
	public void showFragment(int fragmentNo, boolean addToBackStack) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.fragment_content, fragments[fragmentNo]);
		if(addToBackStack) {
			transaction.addToBackStack(null);
		}
		transaction.commit();
	}
	
    private String getBundleKey(int index) {
        return FRAGMENT_PREFIX + Integer.toString(index);
    }

	@Override
	public void onSignInSuccessful() {
		FragmentManager manager = getSupportFragmentManager();
		manager.popBackStackImmediate();
		showFragment(LOADING, true);
		new ClaimCouponTask(this, getApplicationContext(), couponInfo).execute();	
	}
	
	@Override
	public void onSignInCancelled() {
		FragmentManager manager = getSupportFragmentManager();
		manager.popBackStackImmediate();
		showFragment(OPTIONS, false);
		couponInfo = null;
	}
	
	@Override
	public void doPositiveClick(Object... params) {
		if(params != null && params.length == 2 && params[0] != null && params[1] != null) {
			couponInfo = new CouponInfo();
			couponInfo.setCouponId(Integer.parseInt((String) params[0]));
			couponInfo.setSecurityKey((String) params[1]);
			if(NetUtils.isOnline(this)) {
            	if(isConnected()) {
            		showFragment(LOADING, true);
        			new ClaimCouponTask(this, getApplicationContext(), couponInfo).execute();	
            	} else {
            		showFragment(SIGN_IN, true);
            	}	
            } else {
    			showFragment(NO_INTERNET, true);	
            }
		}
		
	}

	@Override
	public void doNegativeClick() {		
		couponInfo = null;
	}
	
	@Override
	public void onDeviceOnline() {
		FragmentManager manager = getSupportFragmentManager();
		manager.popBackStackImmediate();
		if(isConnected()) {
			showFragment(LOADING, true);
			new ClaimCouponTask(this, getApplicationContext(), couponInfo).execute();	
    	} else {
    		showFragment(SIGN_IN, true);
    	}		
	}

	@Override
	public void onDeviceOffline() {		
	}
	
	@Override
	public void onBackPressed() {
		couponInfo = null;
		super.onBackPressed();
	}

	@Override
	public void onTaskFinished(AbstractGroupBuyingTask<?> task, TaskResult result) {
		FragmentManager manager = getSupportFragmentManager();
		manager.popBackStackImmediate();
		if (result.equals(TaskResult.SUCCESSFUL)) {
			couponInfo = null;
			showFragment(OPTIONS, false);
			ClaimResponse claimResponse = ((ClaimCouponTask) task).getClaimResponse();
			switch (claimResponse) {
			case CLAIMED:
				Toast.makeText(getApplicationContext(), "Coupon claimed successfully!", Toast.LENGTH_LONG).show();	
				break;
			case ALREADY_USED:
				Toast.makeText(getApplicationContext(), "Coupon already used!", Toast.LENGTH_LONG).show();	
				break;
			case EXPIRED:
				Toast.makeText(getApplicationContext(), "Coupon expired!", Toast.LENGTH_LONG).show();	
				break;
			}
		} else if (result.equals(TaskResult.FAILED)) {
			Exception e = task.getException();
			if (e instanceof ResourceAccessException) {
				showFragment(NO_INTERNET, true);
			} else if (e instanceof HttpClientErrorException) {
				HttpClientErrorException httpError = (HttpClientErrorException) e;
				if (httpError.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					getApplicationContext().getConnectionRepository().removeConnections(getApplicationContext().getConnectionFactory().getProviderId());
					showFragment(SIGN_IN, true);
				}
			} else if (e instanceof ExpiredAuthorizationException) {
				getApplicationContext().getConnectionRepository().removeConnections(getApplicationContext().getConnectionFactory().getProviderId());
				showFragment(SIGN_IN, true);
			} else {
				Toast.makeText(getApplicationContext(), "Unknown error occurred. Please try again later.", Toast.LENGTH_LONG).show();	
				couponInfo = null;
				showFragment(OPTIONS, false);
			}

		}
		
	}
}