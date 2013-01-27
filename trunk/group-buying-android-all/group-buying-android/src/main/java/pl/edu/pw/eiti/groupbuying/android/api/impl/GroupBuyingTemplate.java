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
package pl.edu.pw.eiti.groupbuying.android.api.impl;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;
import pl.edu.pw.eiti.groupbuying.android.api.OfferOperations;

/**
 * This is the central class for interacting with Greenhouse.
 * @author Roy Clarkson
 */
public class GroupBuyingTemplate extends AbstractOAuth2ApiBinding implements GroupBuyingApi {
	
	private final String apiUrlBase;
		
	private OfferOperations offerOperations;

	public GroupBuyingTemplate(String accessToken, String apiUrlBase) {
		super(accessToken);
		this.apiUrlBase = apiUrlBase;
		registerGreenhouseJsonModule();
		getRestTemplate().setErrorHandler(new GroupBuyingErrorHandler());
		initSubApis();
	}
	
	public OfferOperations offerOperations() {
		return offerOperations;
	}
	
	// private helper 

	private void registerGreenhouseJsonModule() {
		List<HttpMessageConverter<?>> converters = getRestTemplate().getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if(converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				ObjectMapper objectMapper = new ObjectMapper();				
				objectMapper.registerModule(new GroupBuyingModule());
				jsonConverter.setObjectMapper(objectMapper);
			}
		}
	}
	
	private String getApiUrlBase() {
		return apiUrlBase;
	}
	
	private void initSubApis() {
			this.offerOperations = new OfferTemplate(getRestTemplate(), isAuthorized(), getApiUrlBase());
	}
	
}