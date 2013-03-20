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
import pl.edu.pw.eiti.groupbuying.android.fragment.util.AlertDialogListener;
import pl.edu.pw.eiti.groupbuying.android.task.AbstractGroupBuyingTask;
import pl.edu.pw.eiti.groupbuying.android.task.DownloadOfferListTask;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.android.task.util.TaskResult;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.androidquery.AQuery;

public final class ShoppingOffersFragment extends AbstractListFragment implements AsyncTaskListener, AlertDialogListener {

	private AlertDialogFragment dialogFragment;
	
	private List<OfferEssential> offerList;

	public static ShoppingOffersFragment newInstance() {
		ShoppingOffersFragment fragment = new ShoppingOffersFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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

		listView.setBackgroundResource(android.R.color.white);
		//listView.setDivider(getResources().getDrawable(android.R.color.white));
		//listView.setDividerHeight(1 * (int) (getResources().getDisplayMetrics().density + 0.5f));

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
			new DownloadOfferListTask("shopping", 0, this, application).execute();
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
		System.out.println("onStop");
		super.onStop();
	}
	
	@Override
	public void refreshList() {
		// TODO Auto-generated method stub

	}
	
	private void showAlertDialog(int title, int message, int okText) {
		System.out.println("showAlertDialog");
		Fragment fragment = getFragmentManager().findFragmentByTag("dialog");
		if(fragment != null) {
			System.out.println("fragment found");
			dialogFragment = (AlertDialogFragment) fragment;
		}
		if(dialogFragment == null) {
			System.out.println("fragment null");
		    dialogFragment = AlertDialogFragment.newInstance(title, message, okText, this);
		    dialogFragment.show(getFragmentManager(), "dialog");			
		} else {
			dialogFragment.addListener(this);
			if(!dialogFragment.isVisible()) {
				System.out.println("fragment inVisible");
				if(!dialogFragment.isAdded()) {
					dialogFragment.show(getFragmentManager(), "dialog");
				}	
			}
		}
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
				int title;
				int message;
				if(exception instanceof HttpClientErrorException || exception instanceof DuplicateConnectionException || exception instanceof ResourceAccessException) {
					title = R.string.network_problems_title;
					message = R.string.network_problems_message;
				} else {
					title = R.string.connection_error_title;
					message = R.string.connection_error_message;
				}
				showAlertDialog(title, message, R.string.retry);
			}
		}
	}

	@Override
	public void doPositiveClick() {
		System.out.println("doPositiveClick");
		setListViewState(ListViewState.LOADING);
		new DownloadOfferListTask("shopping", 0, this, application).execute();
	}

	@Override
	public void doNegativeClick() {
		System.out.println("doNegativeClick");
		if(offerList == null || offerList.size() == 0) {
			setListViewState(ListViewState.EMPTY);
		} else {
			setListViewState(ListViewState.CONTENT);
		}
	}
}
