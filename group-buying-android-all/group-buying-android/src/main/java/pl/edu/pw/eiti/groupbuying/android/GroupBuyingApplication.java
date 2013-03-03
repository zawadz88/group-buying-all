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

import org.springframework.security.crypto.encrypt.AndroidEncryptors;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.sqlite.SQLiteConnectionRepository;
import org.springframework.social.connect.sqlite.support.SQLiteConnectionRepositoryHelper;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;

import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;
import pl.edu.pw.eiti.groupbuying.android.connect.GroupBuyingConnectionFactory;
import android.app.Application;


public class GroupBuyingApplication extends Application {

	@SuppressWarnings("unused")
	private static final String TAG = GroupBuyingApplication.class.getSimpleName();

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
