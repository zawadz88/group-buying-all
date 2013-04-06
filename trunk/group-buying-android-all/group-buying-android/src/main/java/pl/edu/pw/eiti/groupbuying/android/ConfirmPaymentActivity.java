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

import java.math.BigDecimal;
import java.util.Currency;

import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import pl.edu.pw.eiti.groupbuying.android.util.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalInvoiceItem;
import com.paypal.android.MEP.PayPalPayment;

public class ConfirmPaymentActivity extends AbstractGroupBuyingActivity {

	protected static final String TAG = ConfirmPaymentActivity.class.getSimpleName();

	private Offer offer;

	private AQuery aq;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_payment);
		
		if(PayPal.getInstance() == null) {
			PayPal pp = PayPal.initWithAppID(this.getBaseContext(), getString(R.string.paypal_application_id), PayPal.ENV_SANDBOX);
			pp.setShippingEnabled(false);
		}
		
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
				PayPalPayment newPayment = new PayPalPayment();
				newPayment.setSubtotal(new BigDecimal(offer.getPrice()));
				newPayment.setCurrencyType(Currency.getInstance(getString(R.string.currency)));
				newPayment.setCustomID(Integer.toString(offer.getOfferId()));
				PayPalInvoiceData invoiceData = new PayPalInvoiceData();
				PayPalInvoiceItem invoiceItem = new PayPalInvoiceItem();
				invoiceItem.setTotalPrice(new BigDecimal(offer.getPrice()));
				invoiceItem.setName(offer.getTitle());
				invoiceData.add(invoiceItem);
				newPayment.setInvoiceData(invoiceData);
				newPayment.setRecipient(getString(R.string.paypal_seller_email_id));
				newPayment.setIpnUrl(getString(R.string.paypal_ipn_url));
				newPayment.setMerchantName(getString(R.string.paypal_merchant_name));
				Intent paypalIntent = PayPal.getInstance().checkout(newPayment, ConfirmPaymentActivity.this);
				ConfirmPaymentActivity.this.startActivityForResult(paypalIntent, 1);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case Activity.RESULT_OK:
			// The payment succeeded
			//String payKey = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
			// Tell the user their payment succeeded
			Intent intent = new Intent(ConfirmPaymentActivity.this,
			PaymentConfirmedActivity.class); intent.putExtra("offer", offer); 
			startActivity(intent);
			break;
		case Activity.RESULT_CANCELED:
			// The payment was canceled
			// Tell the user their payment was canceled
			break;
		case PayPalActivity.RESULT_FAILURE:
			// The payment failed -- we get the error from the EXTRA_ERROR_ID
			// and EXTRA_ERROR_MESSAGE
			String errorID = data.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
			String errorMessage = data.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
			Log.e(Constants.TAG, "errorID: " + errorID + ", errorMessage: " + errorMessage);
			// Tell the user their payment was failed.
		}
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
