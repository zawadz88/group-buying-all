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
package pl.edu.pw.eiti.groupbuying.android.adapter;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.android.R;
import pl.edu.pw.eiti.groupbuying.android.api.OfferEssential;
import pl.edu.pw.eiti.groupbuying.android.fragment.AbstractListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.androidquery.AQuery;

public class OfferEssentialListAdapter extends ArrayAdapter<OfferEssential> {

	private static final int LOADING_VIEW = 0;
	private static final int OFFER_VIEW = 1;
	private static final int NO_INTERNET_VIEW = 2;

	private LayoutInflater inflater;
	
	private List<OfferEssential> offerList;
	AbstractListFragment fragment;

	public OfferEssentialListAdapter(AbstractListFragment fragment, int textViewResourceId, List<OfferEssential> objects) {
		super(fragment.getActivity(), textViewResourceId, objects);
		this.fragment = fragment;
		inflater = LayoutInflater.from(getContext());
		this.offerList = objects;
	}

	@Override
	public int getCount() {
		return offerList.size() + (fragment.isLoading() ? 1 : 0) + (fragment.isConnectionAvailable() ? 0 : 1);
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public int getItemViewType(int position) {
		int count = getCount();
		int type;
		if(fragment.isLoading() && position == count - 1) {
			type = LOADING_VIEW;
		} else if(!fragment.isConnectionAvailable() && position == count - 1) {
			type = NO_INTERNET_VIEW;
		} else {
			type = OFFER_VIEW;
		}
		return type;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (fragment.isLoading() && position == getCount() - 1) {
			View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.loading_more_offers_row, null);
			loadingView.setTag(R.id.tag_view_type, LOADING_VIEW);
			return loadingView;
		}
		
		if (!fragment.isConnectionAvailable() && position == getCount() - 1) {
			View noInternetView = LayoutInflater.from(getContext()).inflate(R.layout.no_internet_row, null);
			noInternetView.setTag(R.id.tag_view_type, NO_INTERNET_VIEW);
			Button retryButton = (Button) noInternetView.findViewById(R.id.noInternetRetryButton);
			fragment.setUpNoInternetButton(retryButton, fragment);
			return noInternetView;
		}		

		final OfferEssential offer = offerList.get(position);
		View hView = convertView;
		if (convertView == null || (Integer) convertView.getTag(R.id.tag_view_type) != OFFER_VIEW) {
			hView = inflater.inflate(R.layout.offer_row, null);
			hView.setTag(R.id.tag_view_type, OFFER_VIEW);
		}
		
		AQuery aq = new AQuery(hView);
		aq.id(R.id.offerImage).image(offer.getImageUrl()).getImageView();
		aq.id(R.id.offerTitle).text(offer.getTitle()).getTextView();
		aq.id(R.id.offerPrice).text(String.valueOf(offer.getPrice())).getTextView();
		aq.id(R.id.offersSold).text(String.valueOf(offer.getSoldCount()) + " sold").getTextView();
		
		return hView;
	}

}
