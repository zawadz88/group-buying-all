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
import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;

public class OfferListAdapter extends ArrayAdapter<Offer> {

	private LayoutInflater inflater;
	
	private List<Offer> offerList;
	Activity activity;

	public OfferListAdapter(Activity activity, int textViewResourceId, List<Offer> objects) {
		super(activity, textViewResourceId, objects);
		this.activity = activity;
		inflater = LayoutInflater.from(getContext());
		this.offerList = objects;
	}

	@Override
	public int getCount() {
		return offerList.size();
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final Offer offer = offerList.get(position);
		View hView = convertView;
		if (convertView == null) {
			hView = inflater.inflate(R.layout.offer_row, null);

		}
		AQuery aq = new AQuery(activity, hView);

		aq.id(R.id.offerImage).image(offer.getImageUrl()).getImageView();
		aq.id(R.id.offerTitle).text(offer.getTitle()).getTextView();
		aq.id(R.id.offerPrice).text(String.valueOf(offer.getPrice())).getTextView();
		aq.id(R.id.offersSold).text(String.valueOf(24) + " sold").getTextView();

		return hView;
	}

}
