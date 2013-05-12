package pl.edu.pw.eiti.groupbuying.android;

import pl.edu.pw.eiti.groupbuying.android.gcm.GCMCommonUtilities;
import pl.edu.pw.eiti.groupbuying.android.gcm.GCMServerUtilities;
import pl.edu.pw.eiti.groupbuying.android.gcm.model.PushNotification;
import pl.edu.pw.eiti.groupbuying.android.util.Constants;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

/**
 * This class must be located in root package of application (pl.sport.live).
 */
public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";
	private static final long REGISTER_RETRY_DELAY = 10000;

	public GCMIntentService() {
		super(Constants.GCM_SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		System.out.println("calling onRegistered");
		GCMServerUtilities.register(context, registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		if (GCMRegistrar.isRegisteredOnServer(context)) {
			GCMServerUtilities.unregister(context, registrationId);
		} else {
			// This callback results from the call to unregister made on
			// ServerUtilities when the registration to the server failed.
			Log.i(TAG, "GCM. Ignoring unregister callback");
		}
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		messageReceived(context, intent);
	}

	public static void messageReceived(Context context, Intent intent) {
		if (intent.getExtras() != null) {
			Bundle b = intent.getExtras();
			PushNotification push = new PushNotification();
			push.setMessage(b.getString("message"));
			push.setOfferId(Integer.parseInt(b.getString("offerId")));

			GCMCommonUtilities.generateNotification(context, push);
		} else {
			// Do nothing.
		}
	}

	@Override
	public void onError(Context context, String errorId) {
		if ("SERVICE_NOT_AVAILABLE".equals(errorId)) {
			long delay = (long) (REGISTER_RETRY_DELAY + Math.random() * REGISTER_RETRY_DELAY);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
			GCMServerUtilities.register(context, GCMRegistrar.getRegistrationId(context));
			return;
		}
	}

}
