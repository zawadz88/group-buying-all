package pl.edu.pw.eiti.groupbuying.android;

import java.util.ArrayList;

import pl.edu.pw.eiti.groupbuying.android.api.City;
import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;
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
        outState.putSerializable(CityOffersFragment.CITY_TAG, getApplicationContext().getSelectedCity());
        outState.putSerializable(CityOffersFragment.CITIES_TAG, getApplicationContext().getCities());
        super.onSaveInstanceState(outState);
    }
    
    @Override
	public GroupBuyingApplication getApplicationContext() {
		return (GroupBuyingApplication) super.getApplicationContext();
	}
	
	protected void signOut() {
		synchronized (GroupBuyingApi.class) {
			getApplicationContext().getConnectionRepository().removeConnections(getApplicationContext().getConnectionFactory().getProviderId());
		}
    }
	
    protected boolean isConnected() {
    	synchronized (GroupBuyingApi.class) {
    		return getApplicationContext().getConnectionRepository().findPrimaryConnection(GroupBuyingApi.class) != null;
    	}
	}
}
