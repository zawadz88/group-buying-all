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

import org.springframework.web.client.RestTemplate;

import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import pl.edu.pw.eiti.groupbuying.android.api.OfferOperations;

public class OfferTemplate extends AbstractGroupBuyingOperations implements OfferOperations {
	
	private final RestTemplate restTemplate;

	public OfferTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, String apiUrlBase) {
		super(isAuthorizedForUser, apiUrlBase);
		this.restTemplate = restTemplate;
	}

	@Override
	public Offer getOfferById(int id) {
		return restTemplate.getForObject(buildUri("offers/" + id), Offer.class);
	}
}
