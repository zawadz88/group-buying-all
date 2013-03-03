
package pl.edu.pw.eiti.groupbuying.android;

import org.springframework.social.connect.ConnectionRepository;

import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;
import android.content.Intent;
import android.os.Bundle;

public class MyCouponsIntermediateActivity extends AbstractGroupBuyingActivity {

	private static final String TAG = MyCouponsIntermediateActivity.class.getSimpleName();

	private ConnectionRepository connectionRepository;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		connectionRepository = getApplicationContext().getConnectionRepository();

		Intent intent;
		if (isConnected()) {
			intent = new Intent(this, OfferActivity.class);
		} else {
			intent = new Intent(this, SignInActivity.class);
		}
		startActivity(intent);
		finish();
	}

	private boolean isConnected() {
		return connectionRepository.findPrimaryConnection(GroupBuyingApi.class) != null;
	}
	
}
