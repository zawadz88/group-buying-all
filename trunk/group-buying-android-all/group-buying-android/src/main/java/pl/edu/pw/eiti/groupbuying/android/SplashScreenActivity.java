package pl.edu.pw.eiti.groupbuying.android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.analytics.tracking.android.EasyTracker;


public class SplashScreenActivity extends Activity {

	private SplashThread splashTread = null;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		splashTread = (SplashThread) getLastNonConfigurationInstance();
        
		if (splashTread == null) {
			splashTread = new SplashThread(this);
			splashTread.execute();
		} else if(!splashTread.isActivityNotPresent()){
			splashTread.attach(this);
		} else {
			startNextActivity();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
	    EasyTracker.getInstance().activityStop(this);
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		splashTread.detach();
		return splashTread;
	}
	
	public void startNextActivity() {
		Intent intent = new Intent(this, MainMenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		this.startActivity(intent);
	}
	
	static class SplashThread extends AsyncTask<Void, Void, Void> {
		private static final long SPLASH_TIME = 1500;
		private SplashScreenActivity activity = null;
		private boolean activityNotPresent = false;
		
		public SplashThread(final SplashScreenActivity splashScreenActivity) {
			this.activity = splashScreenActivity;
		}

		public void detach() {
			activity = null;
		}

		public void attach(final SplashScreenActivity splashScreenActivity) {
			activity = splashScreenActivity;
		}

		@Override
		protected Void doInBackground(final Void... arg0) {
			final long startTime = System.currentTimeMillis();
			
			final long dbFetchTime = System.currentTimeMillis() - startTime;
			if(dbFetchTime < SPLASH_TIME) {
				SystemClock.sleep(SPLASH_TIME - dbFetchTime);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void nthg) {
			if (activity != null) {
				activity.startNextActivity();
			} else {
				activityNotPresent = true;
			}
		}

		public boolean isActivityNotPresent() {
			return activityNotPresent;
		}
		
	}
	
}
