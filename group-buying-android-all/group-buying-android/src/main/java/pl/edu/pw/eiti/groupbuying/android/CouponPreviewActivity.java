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

import pl.edu.pw.eiti.groupbuying.android.api.Coupon;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;

public class CouponPreviewActivity extends AbstractGroupBuyingActivity {

	protected static final String TAG = CouponPreviewActivity.class.getSimpleName();

	private Coupon coupon;

	private AQuery aq;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_preview);
		aq = new AQuery(this);
		coupon = (Coupon) getIntent().getSerializableExtra("coupon");
		aq.id(R.id.offerImage).image(coupon.getOffer().getImageUrl());
		aq.id(R.id.offerTitle).text(coupon.getOffer().getTitle());
		aq.id(R.id.offerConditions).text(coupon.getOffer().getDescription());
		aq.id(R.id.viewCouponButton).clicked(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CouponPreviewActivity.this, CouponDetailsActivity.class);
				intent.putExtra("coupon", coupon);
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
		getSupportMenuInflater().inflate(R.menu.coupon_menu, menu);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.options_menu_settings:
			break;
		case R.id.options_menu_coupons:
			intent = new Intent(this, MyCouponsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
			break;
		case R.id.options_menu_offers:
			intent = new Intent(this, MainMenuActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

}
