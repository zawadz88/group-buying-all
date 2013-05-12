package pl.edu.pw.eiti.groupbuying.android.gcm;

import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.SplashScreenActivity;
import pl.edu.pw.eiti.groupbuying.android.gcm.model.PushNotification;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

;

public class GCMCommonUtilities {

	
	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	public static void generateNotification(Context context, PushNotification push) {
		int icon = R.drawable.icon;
		long when = System.currentTimeMillis();
		String title = context.getString(R.string.app_name);
		Intent notificationIntent = new Intent(context, SplashScreenActivity.class);
		notificationIntent.putExtra("pushData", push);
		notificationIntent.setData(Uri.parse("sth://" + System.currentTimeMillis()));

		// set intent so it does not start a new activity
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
			.setSmallIcon(icon)
			.setContentTitle(title)
			.setWhen(when)
			.setContentIntent(intent)
			.setContentText(push.getMessage());
		

		Notification notification = notificationBuilder.build();

		notification.defaults = Notification.DEFAULT_ALL;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}
}
