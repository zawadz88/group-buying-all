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
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class MainMenuActivity extends AbstractGroupBuyingActivity {
    protected OffersFragmentAdapter mAdapter;
    protected MapFragmentScrollOverrideViewPager mPager;
    protected PageIndicator mIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mAdapter = new OffersFragmentAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.offers_fragment_titles));

        mPager = (MapFragmentScrollOverrideViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(OffersFragmentAdapter.OFFERS_FROM_THE_CITY_FRAGMENT, false);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
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
