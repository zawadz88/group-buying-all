/*
 * Copyright 2011-2012 the original author or authors.
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

import org.springframework.social.connect.ConnectionRepository;

import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Roy Clarkson
 */
public class MainActivity extends AbstractGroupBuyingActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private ConnectionRepository connectionRepository;

	//***************************************
	// Activity methods
	//***************************************
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		connectionRepository = getApplicationContext().getConnectionRepository();

		Intent intent;
		if (isConnected()) {
			intent = new Intent(this, OfferActivity.class);
		} else {
			intent = new Intent(this, AuthorizeActivity.class);
		}
		startActivity(intent);
		finish();
	}

	//***************************************
	// Private methods
	//***************************************
	private boolean isConnected() {
		return connectionRepository.findPrimaryConnection(GroupBuyingApi.class) != null;
	}
	
}
