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

import org.springframework.security.crypto.encrypt.AndroidEncryptors;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.sqlite.SQLiteConnectionRepository;
import org.springframework.social.connect.sqlite.support.SQLiteConnectionRepositoryHelper;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;

import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;
import pl.edu.pw.eiti.groupbuying.android.connect.GroupBuyingConnectionFactory;
import android.app.Application;

/**
 * @author Roy Clarkson
 */
public class MainApplication extends Application {

	@SuppressWarnings("unused")
	private static final String TAG = MainApplication.class.getSimpleName();

	private GroupBuyingConnectionFactory connectionFactory;

	private ConnectionRepository connectionRepository;

	

	//***************************************
	// Application methods
	//***************************************
	@Override
	public void onCreate() {
		super.onCreate();
		connectionFactory = new GroupBuyingConnectionFactory(getClientId(), getClientSecret(), getApiUrlBase());
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(connectionFactory);
		connectionRepository = new SQLiteConnectionRepository(new SQLiteConnectionRepositoryHelper(this), registry,
				AndroidEncryptors.text(getEncryptionPassword(), getEncryptionSalt()));
	}

	//***************************************
	// Private methods
	//***************************************
	private String getEncryptionPassword() {
		return getString(R.string.connection_repository_encryption_password);
	}

	private String getEncryptionSalt() {
		return getString(R.string.connection_repository_encryption_salt);
	}
	
	public String getClientId() {
		return getString(R.string.client_id);
	}

	public String getClientSecret() {
		return getString(R.string.client_secret);
	}

	public String getApiUrlBase() {
		return getString(R.string.base_url);
	}

	//***************************************
	// Public methods
	//***************************************
	public ConnectionRepository getConnectionRepository() {
		return connectionRepository;
	}

	public GroupBuyingConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public Connection<GroupBuyingApi> getPrimaryConnection() {
		return getConnectionRepository().findPrimaryConnection(GroupBuyingApi.class);
	}

	public GroupBuyingApi getGroupBuyingApi() {
		Connection<GroupBuyingApi> connection = getPrimaryConnection();
		if (connection != null) {
			return connection.getApi();
		}

		return null;
	}

}
