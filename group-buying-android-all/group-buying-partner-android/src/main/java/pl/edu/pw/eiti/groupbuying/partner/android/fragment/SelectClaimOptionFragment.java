package pl.edu.pw.eiti.groupbuying.partner.android.fragment;

import pl.edu.pw.eiti.groupbuying.partner.android.R;
import pl.edu.pw.eiti.groupbuying.partner.android.fragment.util.AlertDialogListener;
import pl.edu.pw.eiti.groupbuying.partner.android.util.IntentIntegrator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.androidquery.AQuery;

public class SelectClaimOptionFragment extends Fragment {

	public static SelectClaimOptionFragment newInstance() {
		SelectClaimOptionFragment fragment = new SelectClaimOptionFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_claim_options, container, false);
		AQuery aq = new AQuery(getActivity(), rootView);
		aq.id(R.id.manualClaimButton).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				ManualClaimAlertDialogFragment dialog = ManualClaimAlertDialogFragment.newInstance();
				dialog.show(fm, "dialog");
			}
		});
		aq.id(R.id.qrClaimButton).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IntentIntegrator integrator = new IntentIntegrator(getActivity());
				integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
			}
		});
		aq.id(R.id.nfcClaimButton).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return rootView;
	}

}
