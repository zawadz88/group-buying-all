/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.edu.pw.eiti.groupbuying.android;

import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * @author Roy Clarkson
 */
public class OfferActivity extends AbstractGroupBuyingActivity {
	
	protected static final String TAG = OfferActivity.class.getSimpleName();
	
	private Offer offer;
	
	private TextView offerView;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_activity);
		offerView = (TextView) findViewById(R.id.offer);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		if (offer == null) {
			downloadOffer();
		}
	}
	
		
	private void downloadOffer() {
		new DownloadOfferTask().execute();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class DownloadOfferTask extends AsyncTask<Void, Void, Offer> {
		
		private Exception exception;
		
		@Override
		protected void onPreExecute() {
			showProgressDialog(); 
		}
		
		@Override
		protected Offer doInBackground(Void... params) {
			try {
				return getApplicationContext().getGroupBuyingApi().offerOperations().getOfferById(13);
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				exception = e;
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Offer result) {
			dismissProgressDialog();
			processException(exception);
			System.out.println("Offer: " + result.toString());
			offerView.setText(result.toString());
		}
	}
}