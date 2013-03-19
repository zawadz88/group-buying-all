package pl.edu.pw.eiti.groupbuying.android.fragment;

import pl.edu.pw.eiti.groupbuying.android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;

public class LoadingFragment extends Fragment {
	
	private String message;
	
	public static LoadingFragment newInstance(String message) {
		LoadingFragment fragment = new LoadingFragment();
		fragment.message = message;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.fragment_loading, container, false);
	    AQuery aq = new AQuery(getActivity(), rootView);
	    aq.id(R.id.loadingMessage).text(message);
	    
	    return rootView;
	}
		
}
