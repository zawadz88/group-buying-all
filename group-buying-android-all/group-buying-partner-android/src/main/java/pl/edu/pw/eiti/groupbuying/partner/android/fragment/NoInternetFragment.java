package pl.edu.pw.eiti.groupbuying.partner.android.fragment;

import pl.edu.pw.eiti.groupbuying.partner.android.R;
import pl.edu.pw.eiti.groupbuying.partner.android.fragment.util.NoInternetListener;
import pl.edu.pw.eiti.groupbuying.partner.android.util.NetUtils;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class NoInternetFragment extends Fragment {
	
	private NoInternetListener listener;
	
	public static NoInternetFragment newInstance() {
		NoInternetFragment fragment = new NoInternetFragment();
		return fragment;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (NoInternetListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NoInternetListener");
        }
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_no_internet, container, false);
	    view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(NetUtils.isOnline(getActivity())) {
					listener.onDeviceOnline();
				}
			}
		});
	    return view;
	}
		
}
