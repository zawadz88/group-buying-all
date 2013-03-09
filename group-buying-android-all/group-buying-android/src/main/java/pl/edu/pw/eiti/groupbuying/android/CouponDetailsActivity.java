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

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.encode.QRCodeEncoder;

public class CouponDetailsActivity extends AbstractGroupBuyingActivity {

	protected static final String TAG = CouponDetailsActivity.class.getSimpleName();

	private AQuery aq;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_details);
		aq = new AQuery(this);

		Bitmap qrcode = null;
		
		try {
			QRCodeEncoder qrCodeEncoder = new QRCodeEncoder("http://www.elka.pw.edu.pl", 400);
			qrcode = qrCodeEncoder.encodeAsBitmap();
		} catch (WriterException e) {
			Log.e(TAG, e.getLocalizedMessage(), e);
		}
		aq.id(R.id.qrCode).image(qrcode);
		aq.id(R.id.cancelButton).clicked(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();			
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.coupon_menu, menu);
		
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
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
			break;
		case R.id.options_menu_offers:
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

}
