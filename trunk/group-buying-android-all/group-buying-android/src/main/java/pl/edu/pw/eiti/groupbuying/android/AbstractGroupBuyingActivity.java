package pl.edu.pw.eiti.groupbuying.android;

import java.util.ArrayList;

import pl.edu.pw.eiti.groupbuying.android.api.City;
import pl.edu.pw.eiti.groupbuying.android.fragment.CityOffersFragment;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public abstract class AbstractGroupBuyingActivity extends SherlockFragmentActivity {
			
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

}
