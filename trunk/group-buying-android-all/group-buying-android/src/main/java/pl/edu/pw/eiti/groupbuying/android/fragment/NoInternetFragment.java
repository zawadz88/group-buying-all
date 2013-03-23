package pl.edu.pw.eiti.groupbuying.android.fragment;

import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.fragment.util.NoInternetListener;
import pl.edu.pw.eiti.groupbuying.android.util.NetUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class NoInternetFragment extends Fragment {
	
	private NoInternetListener listener;
	
	public static NoInternetFragment newInstance(NoInternetListener listener) {
		NoInternetFragment fragment = new NoInternetFragment();
		fragment.listener = listener;
		return fragment;
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

	public NoInternetListener getListener() {
		return listener;
	}

	public void setListener(NoInternetListener listener) {
		this.listener = listener;
	}
		
}
