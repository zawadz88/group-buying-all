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

import pl.edu.pw.eiti.groupbuying.android.GroupBuyingApplication;
import pl.edu.pw.eiti.groupbuying.android.MainMenuActivity;
import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.fragment.util.NoInternetListener;
import pl.edu.pw.eiti.groupbuying.android.util.NetUtils;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

public abstract class AbstractListFragment extends Fragment implements OnItemClickListener, NoInternetListener {

	protected static final int VISIBLE_ITEM_THRESHOLD = 3;
	
	protected PullToRefreshListView listView;
	protected View loadingView;
	protected View emptyView;
	protected LinearLayout noInternetLayout;
	protected GroupBuyingApplication application;
	protected MainMenuActivity mainActivity;
	protected boolean endOfItemsReached = false;

    protected int currentPage = -1;
    protected boolean loading = false;
    protected boolean connectionAvailable = true;
    
	public enum ListViewState {
		LOADING, CONTENT, EMPTY, NO_INTERNET
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		application = (GroupBuyingApplication) activity.getApplication();
		mainActivity = (MainMenuActivity) activity;
	}
	
	public PullToRefreshListView getListView() {
		return listView;
	}

	public void setListAdapter(ListAdapter adapter) {
		listView.getRefreshableView().setAdapter(adapter);
	}

	protected ListAdapter getListAdapter() {
		if (listView == null) {
			return null;
		}
		return listView.getRefreshableView().getAdapter();
	}

	protected void setListViewState(final ListViewState newState) {
		setListViewState(newState, null, null);		
	}
	
	protected void setListViewState(final ListViewState newState, final String title, final String message) {
		switch (newState) {
		case LOADING:
			listView.setVisibility(View.GONE);
			emptyView.setVisibility(View.GONE);
			loadingView.setVisibility(View.VISIBLE);
			noInternetLayout.setVisibility(View.GONE);
			break;
		case CONTENT:
			listView.setVisibility(View.VISIBLE);
			emptyView.setVisibility(View.GONE);
			loadingView.setVisibility(View.GONE);
			noInternetLayout.setVisibility(View.GONE);
			break;
		case EMPTY:
			listView.setVisibility(View.GONE);
			emptyView.setVisibility(View.VISIBLE);
			loadingView.setVisibility(View.GONE);
			noInternetLayout.setVisibility(View.GONE);
			break;
		case NO_INTERNET:
			listView.setVisibility(View.GONE);
			emptyView.setVisibility(View.GONE);
			loadingView.setVisibility(View.GONE);
			if(title != null) {
				TextView noInternetTextTop = (TextView) noInternetLayout.findViewById(R.id.noInternetTextTop);
				noInternetTextTop.setText(title);
			}
			if(message != null) {
				TextView noInternetTextBottom = (TextView) noInternetLayout.findViewById(R.id.noInternetTextBottom);
				noInternetTextBottom.setText(message);
			}
			noInternetLayout.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	public void setUpNoInternetButton(final View noInternetView, final NoInternetListener listener) {
		noInternetView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(NetUtils.isOnline(getActivity())) {
					listener.onDeviceOnline();
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	public abstract void refreshList();

	public boolean isLoading() {
		return loading;
	}
	
	public boolean isConnectionAvailable() {
		return connectionAvailable;
	}
}
