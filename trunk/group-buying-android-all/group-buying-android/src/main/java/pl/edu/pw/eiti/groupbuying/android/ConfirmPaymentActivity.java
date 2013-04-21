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
package pl.edu.pw.eiti.groupbuying.android;

import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;
import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import pl.edu.pw.eiti.groupbuying.android.fragment.PayPalPaymentFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.PaymentSummaryFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.SignInFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.util.SignInListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ConfirmPaymentActivity extends AbstractGroupBuyingActivity implements SignInListener {

    public static final int SUMMARY = 0;
    public static final int PAYPAL_PAYMENT = 1;
    public static final int SIGN_IN = 2;
    private static final int FRAGMENT_COUNT = SIGN_IN + 1;
    private static final String FRAGMENT_PREFIX = "fragment";
    private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
    
	protected static final String TAG = ConfirmPaymentActivity.class.getSimpleName();

    private boolean restoredFragment = false;
	
	private Offer offer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_payment);
		
		if (getIntent().getSerializableExtra("offer") != null) {
			offer = (Offer) getIntent().getSerializableExtra("offer");
		} else {
			finish();
		}

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
			showFragment(SUMMARY, false);
		}
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.payment_method_menu, menu);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.options_menu_offers:
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
                case SUMMARY:
                    fragments[SUMMARY] = PaymentSummaryFragment.newInstance(offer);  
                    break;
                case PAYPAL_PAYMENT:
                    fragments[PAYPAL_PAYMENT] = PayPalPaymentFragment.newInstance(offer);
                    break;
                case SIGN_IN:
                    fragments[SIGN_IN] = SignInFragment.newInstance();
                    break;
                default:
                    Log.w(TAG, "ConfirmPaymentActivity: invalid fragment index: " + fragmentIndex);
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
		if(fragmentNo == SIGN_IN) {
			((SignInFragment)fragments[SIGN_IN]).setSignInListener(this);
		}
	}
	
    private String getBundleKey(int index) {
        return FRAGMENT_PREFIX + Integer.toString(index);
    }
    
    public boolean isConnected() {
		return getApplicationContext().getConnectionRepository().findPrimaryConnection(GroupBuyingApi.class) != null;
	}

	@Override
	public void onSignInSuccessful() {
		FragmentManager manager = getSupportFragmentManager();
		manager.popBackStackImmediate();
		showFragment(PAYPAL_PAYMENT, true);
	}
		
}