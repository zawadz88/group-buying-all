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

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import pl.edu.pw.eiti.groupbuying.android.OfferActivity;
import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.adapter.OfferEssentialListAdapter;
import pl.edu.pw.eiti.groupbuying.android.api.OfferEssential;
import pl.edu.pw.eiti.groupbuying.android.task.AbstractGroupBuyingTask;
import pl.edu.pw.eiti.groupbuying.android.task.DownloadOfferListTask;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.android.task.util.TaskResult;
import pl.edu.pw.eiti.groupbuying.android.util.Constants;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public final class BasicOffersFragment extends AbstractListFragment implements AsyncTaskListener, OnScrollListener {
	
	private static final String CATEGORY_TAG = "category";
	private List<OfferEssential> offerList = new ArrayList<OfferEssential>();
	private String category;
	private String networkErrorTitle;
	private String networkErrorMessage;
	private String connectionErrorTitle;
	private String connectionErrorMessage;
	
	public static BasicOffersFragment newInstance(String category) {
		BasicOffersFragment fragment = new BasicOffersFragment();
		Bundle bundle = new Bundle();
		bundle.putString(CATEGORY_TAG, category);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if(args == null || args.getString(CATEGORY_TAG) == null) {
			throw new IllegalStateException("BasicOffersFragment must have a non-null category argument!");
		}
		this.category = args.getString(CATEGORY_TAG);
		setRetainInstance(true);
		// if ((savedInstanceState != null) &&
		// savedInstanceState.containsKey(KEY_CONTENT)) { }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_basic_offers,
				container, false);
		AQuery aq = new AQuery(getActivity(), rootView);
		listView = (PullToRefreshListView) aq.id(R.id.offerList).getView();
		loadingView = aq.id(R.id.list_loading).getProgressBar();
		emptyView = aq.id(R.id.list_empty).getTextView();
		noInternetLayout = (LinearLayout) aq.id(R.id.noInternetLayout).getView();
		setUpNoInternetButton(noInternetLayout, this);
		setUpRefreshListener();
		listView.setOnItemClickListener(this);

		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		networkErrorTitle = getString(R.string.network_error_title);
		networkErrorMessage = getString(R.string.network_error_message);
		connectionErrorTitle = getString(R.string.connection_error_title);
		connectionErrorMessage = getString(R.string.connection_error_message);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (!offerList.isEmpty()) {
			setListAdapter(new OfferEssentialListAdapter(this, 0, offerList));
			listView.setOnScrollListener(this);
			setListViewState(ListViewState.CONTENT);
		} else if(offerList.isEmpty() && endOfItemsReached) {
			setListViewState(ListViewState.EMPTY);			
		} else {
			setListViewState(ListViewState.LOADING);
			if(!loading) {
				loading = true;
				new DownloadOfferListTask(category, currentPage + 1, this, application).execute();	
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// outState.putString(KEY_CONTENT, mContent);
	}

	@Override
	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		OfferEssential selectedOffer = offerList.get(position);
		Intent intent = new Intent(getActivity(), OfferActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("offerId", selectedOffer.getOfferId());
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void refreshList() {
		((BaseAdapter)((HeaderViewListAdapter) getListAdapter()).getWrappedAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onTaskFinished(AbstractGroupBuyingTask<?> task,	TaskResult result) {
		loadingMoreItems = false;
		loading = false;
		listView.onRefreshComplete();
		if(result.equals(TaskResult.SUCCESSFUL)) {
			connectionAvailable = true;
			List<OfferEssential> downloadedOffers = ((DownloadOfferListTask) task).getOfferList();
			if(downloadedOffers == null || downloadedOffers.isEmpty()) {
				endOfItemsReached = true;
				if(offerList.isEmpty()) {
					setListViewState(ListViewState.EMPTY);					
				} else {
					setListViewState(ListViewState.CONTENT);
					refreshList();
				}
			} else {
		        currentPage++;
				if(downloadedOffers.size() < Constants.OFFERS_PAGE_SIZE) { //do not download more offers, none will be available anyways
					endOfItemsReached = true;
				}
				if(offerList.isEmpty()) {
					offerList.addAll(downloadedOffers);
					if(getActivity() != null) {
						setListAdapter(new OfferEssentialListAdapter(this, 0, offerList));
					}			
					listView.setOnScrollListener(this);	
				} else {
					offerList.addAll(downloadedOffers);
					refreshList();
				}
				setListViewState(ListViewState.CONTENT);
			}
		} else if(result.equals(TaskResult.FAILED)) {
			
			Exception exception = task.getException();
			if(exception != null) {
				if(offerList.isEmpty()) {
					if(exception instanceof HttpClientErrorException || exception instanceof DuplicateConnectionException || exception instanceof ResourceAccessException) {
						setListViewState(ListViewState.NO_INTERNET, networkErrorTitle, networkErrorMessage);
					} else {
						setListViewState(ListViewState.NO_INTERNET, connectionErrorTitle, connectionErrorMessage);
					}
				} else {
					setListViewState(ListViewState.CONTENT);
					connectionAvailable = false;
					refreshList();
				}
			}
		}
	}

	@Override
	public void onDeviceOnline() {
		if(offerList.isEmpty()) {
			setListViewState(ListViewState.LOADING);			
		} else {
			loadingMoreItems = true;
			loading = true;
			connectionAvailable = true;
			refreshList(); //needed to show loading view
		}
		new DownloadOfferListTask(category, currentPage + 1, this, application).execute();
	}

	@Override
	public void onDeviceOffline() {		
	}
	
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(!loadingMoreItems && visibleItemCount != totalItemCount && (totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_ITEM_THRESHOLD) && !endOfItemsReached && connectionAvailable) { //visibleItemCount != totalItemCount is needed for when new items are added this is false
        	loadingMoreItems = true;
			loading = true;
			refreshList(); //needed to show loading view
     		new DownloadOfferListTask(category, currentPage + 1, this, application).execute();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

	private void setUpRefreshListener() {
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				clearOffers();
				currentPage = -1;
				endOfItemsReached = false;
				loading = true;
				refreshList();
				new DownloadOfferListTask(category, currentPage + 1, BasicOffersFragment.this, application).execute();
			}
		});
		
	}
	
	private void clearOffers() {
		offerList.clear();
	}
	    
}
