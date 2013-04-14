package pl.edu.pw.eiti.groupbuying.android.fragment;

import pl.edu.pw.eiti.groupbuying.android.ConfirmPaymentActivity;
import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.androidquery.AQuery;

public class PaymentSummaryFragment extends AbstractPaymentFragment {
		
	private Offer offer;
	
	public static PaymentSummaryFragment newInstance(Offer offer) {
		PaymentSummaryFragment fragment = new PaymentSummaryFragment();
		fragment.offer = offer;
		return fragment;
	}
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null && savedInstanceState.getSerializable("offer") != null) {
			offer = (Offer) savedInstanceState.getSerializable("offer");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_payment_summary, container, false);
		AQuery aq = new AQuery(getActivity(), rootView);
		aq.id(R.id.selectedOfferTitle).text(offer.getTitle());
		aq.id(R.id.paymentSumValue).text(Double.toString(offer.getPrice()));

		aq.id(R.id.confirmPurchaseButton).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(getActivity() != null) {
					activity.showFragment(ConfirmPaymentActivity.PAYPAL_PAYMENT, true);
				}
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
