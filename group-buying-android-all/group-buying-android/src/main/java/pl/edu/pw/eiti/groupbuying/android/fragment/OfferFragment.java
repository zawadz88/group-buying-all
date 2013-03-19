package pl.edu.pw.eiti.groupbuying.android.fragment;

import pl.edu.pw.eiti.groupbuying.android.PaymentMethodActivity;
import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.androidquery.AQuery;

public class OfferFragment extends Fragment {
	
	private Offer offer;
	
	public static OfferFragment newInstance(Offer offer) {
		OfferFragment fragment = new OfferFragment();
		fragment.offer = offer;
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null && savedInstanceState.getSerializable("offer") != null) {
			offer = (Offer) savedInstanceState.getSerializable("offer");
		}
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_offer,
				container, false);
		AQuery aq = new AQuery(getActivity(), rootView);
		aq.id(R.id.offerImage).image(offer.getImageUrl());
		aq.id(R.id.offerTitle).text(offer.getTitle());
		aq.id(R.id.offerLead).text(offer.getLead());
		aq.id(R.id.offerDescription).text(offer.getDescription());
		aq.id(R.id.buyButton).clicked(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PaymentMethodActivity.class);
				intent.putExtra("offer", offer);
				startActivity(intent);				
			}
		});

		return rootView;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("offer", offer);
	}
		
}
