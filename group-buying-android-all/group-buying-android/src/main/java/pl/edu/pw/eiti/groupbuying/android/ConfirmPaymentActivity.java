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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;

public class ConfirmPaymentActivity extends AbstractGroupBuyingActivity {

	protected static final String TAG = ConfirmPaymentActivity.class.getSimpleName();

	private Offer offer;

	private AQuery aq;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_payment);
		aq = new AQuery(this);
		if (getIntent().getSerializableExtra("offer") != null) {
			offer = (Offer) getIntent().getSerializableExtra("offer");
		} else {
			finish();
		}
		
		aq.id(R.id.selectedOfferTitle).text(offer.getTitle());
		aq.id(R.id.paymentSumValue).text(Double.toString(offer.getPrice()));
		
		aq.id(R.id.confirmPurchaseButton).clicked(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ConfirmPaymentActivity.this, PaymentConfirmedActivity.class);
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
		getSupportMenuInflater().inflate(R.menu.payment_method_menu, menu);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.options_menu_offers:
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

}
