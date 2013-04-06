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
import pl.edu.pw.eiti.groupbuying.android.view.MapFragmentScrollOverrideViewPager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainMenuActivity extends AbstractGroupBuyingActivity {
    protected OffersFragmentAdapter mAdapter;
    protected MapFragmentScrollOverrideViewPager mPager;
    
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
        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab"));
        }
        mPager.setCurrentItem(OffersFragmentAdapter.OFFERS_FROM_THE_CITY_FRAGMENT, false);
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
			Intent intent = new Intent(this, MyCouponsIntermediateActivity.class);
			this.startActivity(intent);
			break;
		case R.id.options_menu_refresh:
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
}
