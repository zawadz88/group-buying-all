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

import pl.edu.pw.eiti.groupbuying.android.fragment.util.OffersFragmentAdapter;
import pl.edu.pw.eiti.groupbuying.android.gcm.GCMServerUtilities;
import pl.edu.pw.eiti.groupbuying.android.util.Constants;
import pl.edu.pw.eiti.groupbuying.android.view.MapFragmentScrollOverrideViewPager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gcm.GCMRegistrar;

public class MainMenuActivity extends AbstractGroupBuyingActivity {
    protected OffersFragmentAdapter mAdapter;
    protected MapFragmentScrollOverrideViewPager mPager;
	private AsyncTask<Void, Void, Void> mRegisterTask;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        ActionBar bar = getSupportActionBar();
        setTitle(getString(R.string.app_name));
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            bar.setDisplayShowTitleEnabled(true);        	
        } else {
            bar.setDisplayShowTitleEnabled(false);        	
        }
        
        mPager = (MapFragmentScrollOverrideViewPager) findViewById(R.id.pager);
        mAdapter = new OffersFragmentAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.offers_fragment_titles), bar, mPager, getApplicationContext().getSelectedCity(), getApplicationContext().getCities());

        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        int selectedPage = OffersFragmentAdapter.OFFERS_FROM_THE_CITY_FRAGMENT;
        if (savedInstanceState != null) {
        	selectedPage = savedInstanceState.getInt("tab", OffersFragmentAdapter.OFFERS_FROM_THE_CITY_FRAGMENT);
        }
        bar.setSelectedNavigationItem(selectedPage);
        mPager.setCurrentItem(selectedPage, false);
        
        registerToGCM();
    }
    
    private void registerToGCM() {
        System.out.println("registerToGCM");
    	GCMRegistrar.checkDevice(getApplicationContext());
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(getApplicationContext());
        final String regId = GCMRegistrar.getRegistrationId(getApplicationContext());
        System.out.println("regId: " + regId);
        if (regId.equals("")) {
            // Automatically registers application on startup.
        	System.out.println("Registering...");
            GCMRegistrar.register(getApplicationContext(), Constants.GCM_SENDER_ID);
        } else {
            // Device is already registered on GCM, check server.
            if (GCMRegistrar.isRegisteredOnServer(getApplicationContext())) {
                // Skips registration.
            	System.out.println("Already registered...");
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
            	System.out.println("Reregistering...");
                final Context context = getApplicationContext();
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        boolean registered = GCMServerUtilities.register(context, regId);
                        // At this point all attempts to register with the app
                        // server failed, so we need to unregister the device
                        // from GCM - the app will try to register again when
                        // it is restarted. Note that GCM will send an
                        // unregistered callback upon completion, but
                        // GCMIntentService.onUnregistered() will ignore it.
                        if (!registered) {
                            GCMRegistrar.unregister(context);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }
		
	}

	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getSupportActionBar().getSelectedNavigationIndex());
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.offer_list_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.options_menu_settings:
			break;
		case R.id.options_menu_coupons:
			Intent intent = new Intent(this, MyCouponsActivity.class);
			this.startActivity(intent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
}
