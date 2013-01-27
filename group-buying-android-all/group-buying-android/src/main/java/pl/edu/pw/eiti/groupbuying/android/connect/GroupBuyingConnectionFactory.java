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
package pl.edu.pw.eiti.groupbuying.android.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;

/**
 * Greenhouse ConnectionFactory implementation.
 * @author Roy Clarkson
 */
public class GroupBuyingConnectionFactory extends OAuth2ConnectionFactory<GroupBuyingApi> {

	/**
	 * Creates a GreenhouseConnectionFactory for the given client ID and secret.
	 * Using this constructor, no application namespace is set
	 * @param clientId The application's Client ID as assigned by Greenhouse 
	 * @param clientSecret The application's Client Secret as assigned by Greenhouse
	 */
	public GroupBuyingConnectionFactory(String clientId, String clientSecret) {
		super("groupbuying", new GroupBuyingServiceProvider(clientId, clientSecret), new GroupBuyingApiAdapter());
	}

	/**
	 * Creates a GreenhouseConnectionFactory for the given application ID, secret, and namespace.
	 * @param clientId The application's Client ID as assigned by Greenhouse 
	 * @param clientSecret The application's Client Secret as assigned by Greenhouse
	 * @param apiUrlBase The application's API base URL
	 */
	public GroupBuyingConnectionFactory(String clientId, String clientSecret, String apiUrlBase) {
		super("groupbuying", new GroupBuyingServiceProvider(clientId, clientSecret, apiUrlBase), new GroupBuyingApiAdapter());
	}
	
}
