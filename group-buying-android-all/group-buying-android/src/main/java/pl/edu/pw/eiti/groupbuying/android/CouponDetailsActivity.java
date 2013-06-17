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
import pl.edu.pw.eiti.groupbuying.android.nfc.NfcHelper;
import pl.edu.pw.eiti.groupbuying.android.nfc.NfcHelperJellyBean;
import pl.edu.pw.eiti.groupbuying.android.util.NfcMessageSentListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.encode.QRCodeEncoder;

public class CouponDetailsActivity extends AbstractGroupBuyingActivity implements NfcMessageSentListener {

	protected static final String TAG = CouponDetailsActivity.class.getSimpleName();
	
    private static final int MESSAGE_SENT = 1;

	private AQuery aq;

	private Coupon coupon;
	
	private NfcHelper nfcHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_details);
		coupon = (Coupon) getIntent().getSerializableExtra("coupon");
		aq = new AQuery(this);

		Bitmap qrcode = null;
		
		try {
			QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(coupon.getCouponId() + ";" + coupon.getSecurityKey(), 400);
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
		aq.id(R.id.couponId).text(getString(R.string.coupon_id_prefix) + coupon.getCouponId());
		aq.id(R.id.securityCode).text(getString(R.string.security_code_prefix) + coupon.getSecurityKey());
		
		nfcHelper = NfcHelper.createInstance(this, coupon.getCouponId() + ";" + coupon.getSecurityKey());
		nfcHelper.initAdapter(this);
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
		case R.id.options_menu_logout:
			signOut();
			intent = new Intent(this, MyCouponsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void sendSuccessful() {
		mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();		
	}
	
	 /** This handler receives a message from sendSuccessful */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	
            switch (msg.what) {
            case MESSAGE_SENT:
                Toast.makeText(CouponDetailsActivity.this, getString(R.string.nfc_message_sent), Toast.LENGTH_LONG).show();
                break;
            }
        }
    };

}
