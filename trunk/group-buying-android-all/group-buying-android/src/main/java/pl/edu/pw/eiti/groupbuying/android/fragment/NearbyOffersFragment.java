package pl.edu.pw.eiti.groupbuying.android.fragment;

import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.view.MapHoldingRelativeLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public final class NearbyOffersFragment extends SupportMapFragment {

	public static NearbyOffersFragment newInstance(String content) {
		NearbyOffersFragment fragment = new NearbyOffersFragment();
		return fragment;
	}

	private GoogleMap googleMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		if (googleMap == null) {
			googleMap = getMap();
			if (googleMap != null) {
				setUpMap();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_nearby_offers,
				container, false);
		final View mapFragmentLayout = super.onCreateView(inflater, container,
				savedInstanceState);
		MapHoldingRelativeLayout mapLayout = (MapHoldingRelativeLayout) rootView
				.findViewById(R.id.mapLayout);
		mapLayout.addView(mapFragmentLayout);
		return rootView;
	}
	@Override
	public void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	private void setUpMap() {
		googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.getUiSettings().setAllGesturesEnabled(true);
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(41.474856, -88.056928))
				.zoom(14.5f)
				.build();
		googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
}
