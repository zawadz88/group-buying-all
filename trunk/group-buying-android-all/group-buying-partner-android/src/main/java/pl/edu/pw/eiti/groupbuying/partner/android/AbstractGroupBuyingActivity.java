package pl.edu.pw.eiti.groupbuying.partner.android;

import pl.edu.pw.eiti.groupbuying.partner.android.api.GroupBuyingApi;
import android.support.v4.app.FragmentActivity;

public abstract class AbstractGroupBuyingActivity extends FragmentActivity {
    
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
