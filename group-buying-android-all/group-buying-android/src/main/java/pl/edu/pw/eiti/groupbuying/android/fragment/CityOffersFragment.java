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
import pl.edu.pw.eiti.groupbuying.android.api.City;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.androidquery.AQuery;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public final class CityOffersFragment extends AbstractListFragment implements AsyncTaskListener, OnScrollListener {

	private static final String CITY_TAG = "city";
	private static final String CITIES_TAG = "cities";
	private List<OfferEssential> offerList = new ArrayList<OfferEssential>();
    private City city;
    private ArrayList<City> cities;
	private String networkErrorTitle;
	private String networkErrorMessage;
	private String connectionErrorTitle;
	private String connectionErrorMessage;
	
	
	public static CityOffersFragment newInstance(City city, ArrayList<City> cities) {
		CityOffersFragment fragment = new CityOffersFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(CITY_TAG, city);
		bundle.putSerializable(CITIES_TAG, cities);
		fragment.setArguments(bundle);
		return fragment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		Bundle args = getArguments();
		if(args == null || args.getSerializable(CITY_TAG) == null || args.getSerializable(CITIES_TAG) == null) {
			if(savedInstanceState == null || !savedInstanceState.containsKey(CITY_TAG) || !savedInstanceState.containsKey(CITIES_TAG)) {
				throw new IllegalStateException("CityOffersFragment must have non-null city and cities!");
			} else {
				city = (City) savedInstanceState.getSerializable(CITY_TAG);
				cities = (ArrayList<City>) savedInstanceState.getSerializable(CITIES_TAG);
			}			
		} else {
			this.city = (City) args.getSerializable(CITY_TAG);
			this.cities = (ArrayList<City>) args.getSerializable(CITIES_TAG);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_city_offers, container, false);
		AQuery aq = new AQuery(getActivity(), rootView);
		listView = (PullToRefreshListView) aq.id(R.id.offerList).getView();
		loadingView = aq.id(R.id.list_loading).getProgressBar();
		emptyView = aq.id(R.id.list_empty).getTextView();
		noInternetLayout = (LinearLayout) aq.id(R.id.noInternetLayout).getView();
		
		setUpSpinner(aq);
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
				new DownloadOfferListTask("city/" + city.getCityId(), currentPage + 1, this, application).execute();	
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(CITY_TAG, city);
		outState.putSerializable(CITIES_TAG, cities);
		super.onSaveInstanceState(outState);
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
	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		OfferEssential selectedOffer = offerList.get(position - 1);
		Intent intent = new Intent(getActivity(), OfferActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("offerId", selectedOffer.getOfferId());
		startActivity(intent);
	}
	
	@Override
	public void refreshList() {
		if(getListAdapter() != null) {
			((BaseAdapter)((HeaderViewListAdapter) getListAdapter()).getWrappedAdapter()).notifyDataSetChanged();			
		}
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
		new DownloadOfferListTask("city/" + city.getCityId(), currentPage + 1, this, application).execute();
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
     		new DownloadOfferListTask("city/" + city.getCityId(), currentPage + 1, this, application).execute();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

	private void setUpRefreshListener() {
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				refetchOffers();
			}
		});
		
	}

	private void setUpSpinner(AQuery aq) {
		Spinner spinner = aq.id(R.id.citySelection).getSpinner();
		ArrayAdapter<City> dataAdapter = new ArrayAdapter<City>(getActivity(), R.layout.spinner_item, cities);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		int selectedCity = cities.indexOf(city);
		if(selectedCity == -1) {
			selectedCity = 0;
			city = cities.get(0);
			application.setSelectedCity(city);
			getArguments().putSerializable(CITY_TAG, city);
		}
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(!cities.get(position).equals(city)) {
					city = cities.get(position);
					application.setSelectedCity(city);
					getArguments().putSerializable(CITY_TAG, city);
					refetchOffers();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
			
		});
		spinner.setSelection(selectedCity);
	}
	
	private void refetchOffers() {
		offerList.clear();
		currentPage = -1;
		endOfItemsReached = false;
		loading = true;
		refreshList();
		new DownloadOfferListTask("city/" + city.getCityId(), currentPage + 1, CityOffersFragment.this, application).execute();
	}
}
