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
package pl.edu.pw.eiti.groupbuying.android.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import pl.edu.pw.eiti.groupbuying.android.api.GroupBuyingApi;

/**
 * @author Roy Clarkson
 */
public class GroupBuyingApiAdapter implements ApiAdapter<GroupBuyingApi> {

	public boolean test(GroupBuyingApi greenhouse) {
		/*try {
			greenhouse.userOperations().getUserProfile();
			return true;
		} catch (ApiException e) {
			return false;
		}*/
		return true;
	}

	public void setConnectionValues(GroupBuyingApi api, ConnectionValues values) {
		System.out.println("setConnectionValues");
		//TODO
		/*GreenhouseProfile profile = api.userOperations().getUserProfile();
		values.setProviderUserId(Long.toString(profile.getAccountId()));
		values.setDisplayName(profile.getDisplayName());
		values.setProfileUrl(profile.getProfileUrl());
		values.setImageUrl(profile.getPictureUrl());*/
	}

	public UserProfile fetchUserProfile(GroupBuyingApi api) {
		//GreenhouseProfile profile = api.userOperations().getUserProfile();
		return new UserProfileBuilder().setName("XXX").build();
	}

	public void updateStatus(GroupBuyingApi api, String message) {
		// TODO: add update status functionality
	}
}