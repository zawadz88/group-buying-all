package pl.edu.pw.eiti.groupbuying.android.fragment;

import pl.edu.pw.eiti.groupbuying.android.ConfirmPaymentActivity;
import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import android.app.Activity;
import android.support.v4.app.Fragment;

public abstract class AbstractPaymentFragment extends Fragment {

	protected GroupBuyingApplication application;
	protected ConfirmPaymentActivity activity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		application = (GroupBuyingApplication) activity.getApplication();
		this.activity = (ConfirmPaymentActivity) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		this.activity = null;
	}
}
