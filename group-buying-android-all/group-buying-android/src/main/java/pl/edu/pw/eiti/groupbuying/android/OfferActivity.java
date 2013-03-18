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

import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import pl.edu.pw.eiti.groupbuying.android.task.AbstractGroupBuyingTask;
import pl.edu.pw.eiti.groupbuying.android.task.DownloadOfferTask;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.android.task.util.TaskResult;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.androidquery.AQuery;

public class OfferActivity extends AbstractGroupBuyingActivity implements AsyncTaskListener {

	protected static final String TAG = OfferActivity.class.getSimpleName();

	private Offer offer;

	private AQuery aq;

	private ShareActionProvider actionProvider;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offer);
		aq = new AQuery(this);
		int offerId = -1;
		if (getIntent().getSerializableExtra("offerId") != null) {
			offerId = getIntent().getIntExtra("offerId", -1);
			if(offerId == -1) {
				finish();
				return;
			}
		}
		if (getIntent().getSerializableExtra("offer") != null) {
			offer = (Offer) getIntent().getSerializableExtra("offer");
			initOfferView();
		} else {
			new DownloadOfferTask(offerId, this, getApplicationContext()).execute();
			//TODO jakis progressbar
		}
		
	}

	private void initOfferView() {
		aq.id(R.id.offerImage).image(offer.getImageUrl());
		aq.id(R.id.offerTitle).text(offer.getTitle());
		aq.id(R.id.offerLead).text(offer.getLead());
		aq.id(R.id.offerDescription).text(offer.getDescription());
		aq.id(R.id.buyButton).clicked(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(OfferActivity.this, PaymentMethodActivity.class);
				intent.putExtra("offer", offer);
				startActivity(intent);				
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.offer_menu, menu);
		MenuItem actionItem = menu.findItem(R.id.menu_item_share);
	    actionProvider = (ShareActionProvider) actionItem.getActionProvider();
		
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
			Intent intent = new Intent(this, MyCouponsIntermediateActivity.class);
			this.startActivity(intent);
			break;
		case R.id.options_menu_offers:
			break;
		default:
			if(offer == null) {//do not share if offer is not initialized
				return true;
			} else {
				return super.onOptionsItemSelected(item);
			}
		}
		return true;
	}
	
	@Override
	public void onTaskFinished(AbstractGroupBuyingTask<?> task, TaskResult result) {
		if(result.equals(TaskResult.SUCCESSFUL)) {
			Offer downloadedOffer = ((DownloadOfferTask) task).getOffer();
			if(downloadedOffer == null) {
				//TODO show error stub?
			} else {
				offer = downloadedOffer;
				actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
			    
			    Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_SUBJECT, offer.getTitle());
				//TODO zastapic jakims realnym linkiem
				shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.google.com");			    
			    actionProvider.setShareIntent(shareIntent);
			    initOfferView();
			}
		} else if(result.equals(TaskResult.FAILED)) {
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
				//TODO wyswietlic fragment braku netu tak jak w ladnej czesci wanny
			}
		}
		
	}

}
