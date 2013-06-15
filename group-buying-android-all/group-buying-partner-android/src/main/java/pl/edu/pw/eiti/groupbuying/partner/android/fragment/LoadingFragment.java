package pl.edu.pw.eiti.groupbuying.partner.android.fragment;

import pl.edu.pw.eiti.groupbuying.partner.android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;

public class LoadingFragment extends Fragment {
		
	public static LoadingFragment newInstance(String message) {
		LoadingFragment fragment = new LoadingFragment();
		Bundle args = new Bundle();
		args.putString("message", message);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.fragment_loading, container, false);
	    AQuery aq = new AQuery(getActivity(), rootView);
	    aq.id(R.id.loadingMessage).text(getArguments().getString("message"));
	    
	    return rootView;
	}
		
}
