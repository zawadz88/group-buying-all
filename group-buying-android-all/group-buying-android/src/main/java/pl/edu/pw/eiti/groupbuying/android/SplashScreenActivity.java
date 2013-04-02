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

import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.eiti.groupbuying.android.api.City;
import pl.edu.pw.eiti.groupbuying.android.task.AbstractGroupBuyingTask;
import pl.edu.pw.eiti.groupbuying.android.task.DownloadCitiesTask;
import pl.edu.pw.eiti.groupbuying.android.task.DownloadDefaultCityTask;
import pl.edu.pw.eiti.groupbuying.android.task.DownloadNearestCityTask;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.android.task.util.TaskResult;
import pl.edu.pw.eiti.groupbuying.android.util.Constants;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.analytics.tracking.android.EasyTracker;


public class SplashScreenActivity extends Activity implements AsyncTaskListener {

	private SplashThread splashTread = null;
	private String cityId;
	private ArrayList<City> cities;
	
	private boolean shouldStartNextActivity = false;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PREFERENCES_KEY, Context.MODE_PRIVATE);
		cityId = sharedPreferences.getString(Constants.CITY_ID_KEY, null);

		if(cityId == null) {
			// Get the location manager
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			Criteria criteria = new Criteria();
			String bestProvider = locationManager.getBestProvider(criteria, true);
			if(bestProvider != null) {
				Location location = locationManager.getLastKnownLocation(bestProvider);
				if(location != null) {
					final double longitude = location.getLongitude();
					final double latidude = location.getLatitude();
					new DownloadNearestCityTask(latidude, longitude, this, (GroupBuyingApplication) getApplicationContext()).execute();
				}
			} else {
				new DownloadDefaultCityTask(this, (GroupBuyingApplication) getApplicationContext()).execute();
			}
		} 
		
		new DownloadCitiesTask(this, (GroupBuyingApplication) getApplicationContext()).execute();
		
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

	@Override
	public synchronized void onTaskFinished(AbstractGroupBuyingTask<?> task, TaskResult result) {
		if(result.equals(TaskResult.FAILED)) {
			Exception exception = task.getException();
			if(exception != null) {
				//TODO show alert dialog
				finish();
			}
		} else if(result.equals(TaskResult.SUCCESSFUL)) {
			if(task instanceof DownloadCitiesTask) {
				cities = ((DownloadCitiesTask) task).getCities();
				if(cityId != null && shouldStartNextActivity) {
					startNextActivity();
				}
			} else if(task instanceof DownloadDefaultCityTask) {
				City city =  ((DownloadDefaultCityTask) task).getCity();	
				cityId = city.getCityId();
		        saveCityId();
				if(cities != null && shouldStartNextActivity) {
					startNextActivity();
				}
			} else if(task instanceof DownloadNearestCityTask) {
				City city =  ((DownloadNearestCityTask) task).getCity();	
				cityId = city.getCityId();
		        saveCityId();
				if(cities != null && shouldStartNextActivity) {
					startNextActivity();
				}
			}			
		}
	}

	private void saveCityId() {
		SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PREFERENCES_KEY, Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
		prefsEditor.putString("cityId", cityId);
		prefsEditor.commit();
	}
	
	public String getCityId() {
		return cityId;
	}
	
	public List<City> getCities() {
		return cities;
	}
	
	public void setShouldStartNextActivity(boolean shouldStartNextActivity) {
		this.shouldStartNextActivity = shouldStartNextActivity;
	}
	
	public void startNextActivity() {
		Intent intent = new Intent(this, MainMenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("cities", cities);
		intent.putExtra("cityId", cityId);
		this.startActivity(intent);
		finish();
	}
	
	private static class SplashThread extends AsyncTask<Void, Void, Void> {
		private static final long SPLASH_TIME = 2000;
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
			SystemClock.sleep(SPLASH_TIME);
			return null;
		}

		@Override
		protected void onPostExecute(Void nthg) {
			if (activity != null) {
				if(activity.getCities() != null && activity.getCityId() != null) {
					activity.startNextActivity();	
				} else {
					activity.setShouldStartNextActivity(true);
				}
			} else {
				activityNotPresent = true;
			}
		}

		public boolean isActivityNotPresent() {
			return activityNotPresent;
		}
		
	}
	
}
