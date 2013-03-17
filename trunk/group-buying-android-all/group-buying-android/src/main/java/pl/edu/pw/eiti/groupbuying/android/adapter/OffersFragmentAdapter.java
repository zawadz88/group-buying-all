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
package pl.edu.pw.eiti.groupbuying.android.adapter;

import pl.edu.pw.eiti.groupbuying.android.fragment.CityOffersFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.NearbyOffersFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.ShoppingOffersFragment;
import pl.edu.pw.eiti.groupbuying.android.fragment.TravelOffersFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class OffersFragmentAdapter extends FragmentPagerAdapter {

	public final static int OFFERS_NEARBY_FRAGMENT = 0;
	public final static int OFFERS_FROM_THE_CITY_FRAGMENT = 1;
	public final static int OFFERS_SHOPPING_FRAGMENT = 2;
	public final static int OFFERS_TRAVEL_FRAGMENT = 3;
	
	private final static int FRAGMENT_COUNT = OFFERS_TRAVEL_FRAGMENT + 1;
	
    protected String[] fragmentTitles;


    public OffersFragmentAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.fragmentTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
    	Fragment fragment = null;
    	switch(position) {
    	case OFFERS_NEARBY_FRAGMENT:
    		fragment = NearbyOffersFragment.newInstance(fragmentTitles[position]);
    		break;
    	case OFFERS_FROM_THE_CITY_FRAGMENT:
    		fragment = CityOffersFragment.newInstance(fragmentTitles[position]);
    		break;
    	case OFFERS_SHOPPING_FRAGMENT:
    		fragment = ShoppingOffersFragment.newInstance();
    		break;
    	case OFFERS_TRAVEL_FRAGMENT:
    		fragment = TravelOffersFragment.newInstance(fragmentTitles[position]);
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
}
