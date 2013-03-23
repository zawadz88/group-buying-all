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

import java.util.List;

import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import pl.edu.pw.eiti.groupbuying.android.OfferActivity;
import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.adapter.OfferEssentialListAdapter;
import pl.edu.pw.eiti.groupbuying.android.api.OfferEssential;
import pl.edu.pw.eiti.groupbuying.android.fragment.util.NoInternetListener;
import pl.edu.pw.eiti.groupbuying.android.task.AbstractGroupBuyingTask;
import pl.edu.pw.eiti.groupbuying.android.task.DownloadOfferListTask;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.android.task.util.TaskResult;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.androidquery.AQuery;

public final class BasicOffersFragment extends AbstractListFragment implements AsyncTaskListener, NoInternetListener {
	
	private static final String CATEGORY_TAG = "category";
	private List<OfferEssential> offerList;
	private String category;

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
		// if ((savedInstanceState != null) &&
		// savedInstanceState.containsKey(KEY_CONTENT)) { }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_basic_offers,
				container, false);
		AQuery aq = new AQuery(getActivity(), rootView);
		listView = aq.id(android.R.id.list).getListView();
		loadingView = aq.id(R.id.list_loading).getProgressBar();
		emptyView = aq.id(R.id.list_empty).getTextView();
		noInternetLayout = (LinearLayout) aq.id(R.id.noInternetLayout).getView();
		setUpNoInternetButton(noInternetLayout, this);

		listView.setOnItemClickListener(this);

		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (offerList != null && offerList.size() > 0) {
			// Set new adapter
			setListAdapter(new OfferEssentialListAdapter(getActivity(), 0, offerList));
			setListViewState(ListViewState.CONTENT);
		} else if(offerList != null && offerList.size() == 0) {
			setListViewState(ListViewState.EMPTY);			
		} else {
			setListViewState(ListViewState.LOADING);			
			new DownloadOfferListTask(category, 0, this, application).execute();
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
	}
	
	@Override
	public void onTaskFinished(AbstractGroupBuyingTask<?> task,	TaskResult result) {
		//TODO obsluzyc doczytywanie ofert
		if(result.equals(TaskResult.SUCCESSFUL)) {
			List<OfferEssential> downloadedOffers = ((DownloadOfferListTask) task).getOfferList();
			if(downloadedOffers == null || downloadedOffers.isEmpty()) {
				setListViewState(ListViewState.EMPTY);
			} else {
				offerList = downloadedOffers;
				setListAdapter(new OfferEssentialListAdapter(getActivity(), 0, offerList));
				setListViewState(ListViewState.CONTENT);
			}
		} else if(result.equals(TaskResult.FAILED)) {
			if(offerList == null || offerList.size() == 0) {
				setListViewState(ListViewState.EMPTY);
			} else {
				setListViewState(ListViewState.CONTENT);
			}
			Exception exception = task.getException();
			if(exception != null) {
				final String title;
				final String message;
				if(exception instanceof HttpClientErrorException || exception instanceof DuplicateConnectionException || exception instanceof ResourceAccessException) {
					title = getString(R.string.network_problems_title);
					message = getString(R.string.network_problems_message);
				} else {
					title = getString(R.string.connection_error_title);
					message = getString(R.string.connection_error_message);
				}
				
				setListViewState(ListViewState.NO_INTERNET, title, message);
			}
		}
	}

	@Override
	public void onDeviceOnline() {
		setListViewState(ListViewState.LOADING);
		new DownloadOfferListTask(category, 0, this, application).execute();
	}

	@Override
	public void onDeviceOffline() {		
	}
}
