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
package pl.edu.pw.eiti.groupbuying.android;

import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.androidquery.AQuery;

public class OfferActivity extends AbstractGroupBuyingActivity {

	protected static final String TAG = OfferActivity.class.getSimpleName();

	private Offer offer;

	private AQuery aq;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offer);
		aq = new AQuery(this);
		if (getIntent().getSerializableExtra("offer") != null) {
			offer = (Offer) getIntent().getSerializableExtra("offer");
		}
		aq.id(R.id.offerImage).image(offer.getImageUrl());
		aq.id(R.id.offerTitle).text(offer.getTitle());
		aq.id(R.id.offerLead).text(offer.getLead());
		aq.id(R.id.offerDescription).text(offer.getDescription());

	}

	@Override
	public void onStart() {
		super.onStart();

		if (offer == null) {
			downloadOffer();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.offer_menu, menu);
		
		MenuItem actionItem = menu.findItem(R.id.menu_item_share);
	    ShareActionProvider actionProvider = (ShareActionProvider) actionItem.getActionProvider();
	    actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
	    
	    Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, offer.getTitle());
		shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.google.com");
	    
	    actionProvider.setShareIntent(shareIntent);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.options_menu_settings:
			break;
		case R.id.options_menu_coupons:
			break;
		case R.id.options_menu_offers:
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void downloadOffer() {
		new DownloadOfferTask().execute();
	}

	private class DownloadOfferTask extends AsyncTask<Void, Void, Offer> {

		private Exception exception;

		@Override
		protected void onPreExecute() {
			showProgressDialog();
		}

		@Override
		protected Offer doInBackground(Void... params) {
			try {
				return getApplicationContext().getGroupBuyingApi()
						.offerOperations().getOfferById(13);
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				exception = e;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Offer result) {
			dismissProgressDialog();
			processException(exception);
			if (result != null) {
				// aq.id(R.id.offer).text(result.toString());
			}
		}
	}
}
