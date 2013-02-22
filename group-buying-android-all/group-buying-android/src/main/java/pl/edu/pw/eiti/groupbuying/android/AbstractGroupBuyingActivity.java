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

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * @author Roy Clarkson
 */
public abstract class AbstractGroupBuyingActivity extends SherlockActivity {
	
	protected static final String TAG = AbstractGroupBuyingActivity.class.getSimpleName();
	
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setTheme(R.style.Theme_Sherlock);
	}
	//***************************************
    // GreenhouseActivity methods
    //***************************************
	public GroupBuyingApplication getApplicationContext() {
		return (GroupBuyingApplication) super.getApplicationContext();
	}
	
	public void showProgressDialog() {
		showProgressDialog("Loading. Please wait...");
	}
	
	public void showProgressDialog(String message) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setIndeterminate(true);
		}
		
		progressDialog.setMessage(message);
		progressDialog.show();
	}
	
	public void dismissProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
	
	
	//***************************************
    // Protected methods
    //***************************************	
	protected void processException(Exception e) {
		if (e != null) {
			if (e instanceof ResourceAccessException) {
				displayNetworkError();
			} else if (e instanceof HttpClientErrorException) {
				HttpClientErrorException httpError = (HttpClientErrorException) e;
				if (httpError.getStatusCode() ==  HttpStatus.UNAUTHORIZED) {
					displayAuthorizationError();
				}
			}
		}
	}
	
	protected void displayNetworkError() {
		Toast toast = Toast.makeText(this, "A problem occurred with the network connection while attempting to communicate with Group Buying.", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	protected void displayAuthorizationError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You are not authorized to connect to Group Buying. Please reauthorize the app.");
		builder.setCancelable(false);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
		     	signOut();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	protected void signOut() {
    	getApplicationContext().getConnectionRepository().removeConnections(getApplicationContext().getConnectionFactory().getProviderId());
    	startActivity(new Intent(this, MainActivity.class));
    	finish();
    }

}
