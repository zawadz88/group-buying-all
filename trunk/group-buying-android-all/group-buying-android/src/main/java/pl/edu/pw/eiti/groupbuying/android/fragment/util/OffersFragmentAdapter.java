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
package pl.edu.pw.eiti.groupbuying.android.fragment.util;

import pl.edu.pw.eiti.groupbuying.android.fragment.BasicOffersFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.CityOffersFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.NearbyOffersFragment;
import pl.edu.pw.eiti.groupbuying.android.view.MapFragmentScrollOverrideViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;

public class OffersFragmentAdapter extends FragmentPagerAdapter implements OnPageChangeListener, TabListener {

	private static final String TAB_TAG_PREFIX = "tab";
	public final static int OFFERS_NEARBY_FRAGMENT = 0;
	public final static int OFFERS_FROM_THE_CITY_FRAGMENT = 1;
	public final static int OFFERS_SHOPPING_FRAGMENT = 2;
	public final static int OFFERS_TRAVEL_FRAGMENT = 3;

	public final static int FRAGMENT_COUNT = OFFERS_TRAVEL_FRAGMENT + 1;

	protected String[] fragmentTitles;
	private ActionBar bar;
	private MapFragmentScrollOverrideViewPager viewPager;

	public OffersFragmentAdapter(FragmentManager fm, String[] titles, ActionBar bar, MapFragmentScrollOverrideViewPager pager) {
		super(fm);
		this.fragmentTitles = titles;
		this.bar = bar;
		viewPager = pager;
		viewPager.setAdapter(this);
		viewPager.setOnPageChangeListener(this);
		for (int i = 0; i < titles.length; i++) {
			Tab tab = bar.newTab();
			tab.setText(titles[i]);
			tab.setTag(TAB_TAG_PREFIX + i);
			tab.setTabListener(this);
			bar.addTab(tab);
		}
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position) {
		case OFFERS_NEARBY_FRAGMENT:
			fragment = NearbyOffersFragment.newInstance(fragmentTitles[position]);
			break;
		case OFFERS_FROM_THE_CITY_FRAGMENT:
			fragment = CityOffersFragment.newInstance();
			break;
		case OFFERS_SHOPPING_FRAGMENT:
			fragment = BasicOffersFragment.newInstance("shopping");
			break;
		case OFFERS_TRAVEL_FRAGMENT:
			fragment = BasicOffersFragment.newInstance("travel");
			break;
		default:
			throw new IllegalArgumentException("Unsupported position: " + position);
		}

		return fragment;
	}

	@Override
	public int getCount() {
		return FRAGMENT_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return fragmentTitles[position];
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Object tag = tab.getTag();
		for (int i = 0; i < fragmentTitles.length; i++) {
			if ((TAB_TAG_PREFIX + i).equals(tag)) {
				if(viewPager.getCurrentItem() != i) {
					viewPager.setCurrentItem(i);
				}
				break;
			}
		}

	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		bar.setSelectedNavigationItem(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
}
