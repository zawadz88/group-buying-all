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
package pl.edu.pw.eiti.groupbuying.android.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.android.OfferActivity;
import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.api.OfferEssential;
import pl.edu.pw.eiti.groupbuying.android.task.AbstractGroupBuyingTask;
import pl.edu.pw.eiti.groupbuying.android.task.DownloadOfferListTask;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.android.task.util.TaskResult;
import pl.edu.pw.eiti.groupbuying.android.view.MapHoldingRelativeLayout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public final class NearbyOffersFragment extends SupportMapFragment implements AsyncTaskListener, LocationListener, OnInfoWindowClickListener {

	private static final LatLng DEFAULT_LOCATION = new LatLng(52.22985, 21.012243);
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	private static final float DISTANCE_CHANGE_THRESHOLD = 100f;
	private static final long TIME_CHANGE_THRESHOLD = 100;
	private static final float ACCURACY_THRESHOLD = 10f;
	private static final long DETERMINE_LOCATION_PERIOD = 4000L;

	private GoogleMap googleMap;
	private LocationManager locationManager;
	private Location currentBestLocation;
	private boolean locationDetermined = false;
	private Map<Marker, OfferEssential> markers = new HashMap<Marker, OfferEssential>();
	private List<OfferEssential> offerList = new ArrayList<OfferEssential>();
	private String networkErrorTitle;
	private String networkErrorMessage;
	private String connectionErrorTitle;
	private String connectionErrorMessage;
	private GroupBuyingApplication application;
	private View mapFragmentLayout;
	private MapHoldingRelativeLayout mapLayout;

	public static NearbyOffersFragment newInstance(String content) {
		NearbyOffersFragment fragment = new NearbyOffersFragment();
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		application = (GroupBuyingApplication) activity.getApplication();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setUpMapIfNeeded();
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_nearby_offers, container, false);
		mapFragmentLayout = super.onCreateView(inflater, container, savedInstanceState);
		mapLayout = (MapHoldingRelativeLayout) rootView.findViewById(R.id.mapLayout);
		mapLayout.addView(mapFragmentLayout);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		networkErrorTitle = getString(R.string.network_error_title);
		networkErrorMessage = getString(R.string.network_error_message);
		connectionErrorTitle = getString(R.string.connection_error_title);
		connectionErrorMessage = getString(R.string.connection_error_message);
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

		if (currentBestLocation == null) {
			currentBestLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}

		if (currentBestLocation == null)
			System.out.println("\nLocation[unknown]\n\n");
		else
			System.out.println("\n\n" + currentBestLocation.toString());
		if (!locationDetermined) {
						
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					System.out.println("handler.postDelayed");
					locationDetermined = true;
					locationManager.removeUpdates(NearbyOffersFragment.this);
					if (currentBestLocation != null) {
						new DownloadOfferListTask("nearby/" + currentBestLocation.getLatitude() + "/" + currentBestLocation.getLongitude(), 1, NearbyOffersFragment.this, application).execute();
					} else {
						// TODO display message 'Couldn't get your location'
					}
				}
			}, DETERMINE_LOCATION_PERIOD);

		}
	}

	@Override
	public void onResume() {
		super.onResume();
		setUpMapIfNeeded();

		if (!locationDetermined) {
			System.out.println("locationManager.requestLocationUpdates");
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, TIME_CHANGE_THRESHOLD, DISTANCE_CHANGE_THRESHOLD, this);
		}
		if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			Toast.makeText(getActivity(), "No provider", Toast.LENGTH_SHORT).show();
			//TODO display message 'Enable location services'
			/*Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(gpsOptionsIntent);*/
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onLocationChanged(Location location) {
		boolean updated = updateLocation(location);
		System.out.println("onLocationChanged: " + location + ", updated: " + updated);
	}

	@Override
	public void onProviderDisabled(String provider) {
		System.out.println("onProviderDisabled: " + provider);
	}

	@Override
	public void onProviderEnabled(String provider) {
		System.out.println("onProviderEnabled: " + provider);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		System.out.println("onStatusChanged: " + provider + ", status: " + status);
	}

	@Override
	public void onTaskFinished(AbstractGroupBuyingTask<?> task, TaskResult result) {
		if (result.equals(TaskResult.SUCCESSFUL)) {
			List<OfferEssential> downloadedOffers = ((DownloadOfferListTask) task).getOfferList();
			System.out.println("downloaded: " + downloadedOffers);
			if (downloadedOffers == null || downloadedOffers.isEmpty()) {
				//TODO display message 'No offers available nearby'
			} else {
				offerList.addAll(downloadedOffers);
				addMarkersToGoogleMap();
			}
		} else if (result.equals(TaskResult.FAILED)) {
			Exception exception = task.getException();
			if (exception != null) {
				if (exception instanceof HttpClientErrorException || exception instanceof DuplicateConnectionException || exception instanceof ResourceAccessException) {
					//TODO setListViewState(ListViewState.NO_INTERNET, networkErrorTitle, networkErrorMessage);
				} else {
					//TODO setListViewState(ListViewState.NO_INTERNET, connectionErrorTitle, connectionErrorMessage);
				}
			}
		}
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		OfferEssential selectedOffer = markers.get(marker);
		Intent intent = new Intent(getActivity(), OfferActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("offerId", selectedOffer.getOfferId());
		startActivity(intent);
	}
	
	private void addMarkersToGoogleMap() {
		googleMap.clear();
		markers.clear();
		googleMap.setOnInfoWindowClickListener(this);
        Builder boundsBuilder = new LatLngBounds.Builder();
		for(OfferEssential offer : offerList) {
			Marker marker = googleMap.addMarker(new MarkerOptions()
		    .position(new LatLng(offer.getLatitude(), offer.getLongitude()))
		    .title(offer.getTitle())
		    .snippet("Price: " + offer.getPrice()));
			boundsBuilder.include(marker.getPosition());
			markers.put(marker, offer);
		}
		final LatLngBounds bounds = boundsBuilder.build();
		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
		googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));               
	}
	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 * @return true if location was updated
	 */
	protected synchronized boolean updateLocation(Location location) {
		if (currentBestLocation == null) {
			currentBestLocation = location;
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than five minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			currentBestLocation = location;
			return true;
			// If the new location is more than five minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > ACCURACY_THRESHOLD;
		boolean isMoreAccurate = accuracyDelta < -ACCURACY_THRESHOLD;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			currentBestLocation = location;
			return true;
		} else if (isNewer && !isLessAccurate) {
			currentBestLocation = location;
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			currentBestLocation = location;
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	private void setUpMapIfNeeded() {
		if (googleMap == null) {
			googleMap = getMap();
			if (googleMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.getUiSettings().setAllGesturesEnabled(true);
		if(offerList != null && !offerList.isEmpty()) {
			addMarkersToGoogleMap();
		} else {
			CameraPosition cameraPosition = new CameraPosition.Builder().target(DEFAULT_LOCATION).zoom(14.5f).build();
			googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));			
		}
	}
	
}