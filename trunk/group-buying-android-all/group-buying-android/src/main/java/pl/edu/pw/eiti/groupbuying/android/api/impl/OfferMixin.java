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
package pl.edu.pw.eiti.groupbuying.android.api.impl;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import pl.edu.pw.eiti.groupbuying.android.api.Address;
import pl.edu.pw.eiti.groupbuying.android.api.Category;
import pl.edu.pw.eiti.groupbuying.android.api.Offer.State;

/**
 * Mixin class for adding Jackson annotations to Offer.
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
abstract class OfferMixin {	

	@JsonCreator
	OfferMixin(
			@JsonProperty("categoryId") int offerId, 
			@JsonProperty("title") String title, 
			@JsonProperty("lead") String lead, 
			@JsonProperty("description") String description, 
			@JsonProperty("address") Address address, 
			@JsonProperty("imageUrl") String imageUrl, 
			@JsonProperty("price") double price, 
			@JsonProperty("priceBeforeDiscount") double priceBeforeDiscount, 
			@JsonProperty("startDate") Date startDate, 
			@JsonProperty("endDate") Date endDate, 
			@JsonProperty("state") State state, 
			@JsonProperty("category") Category category, 
			@JsonProperty("username") String username) {}
}
