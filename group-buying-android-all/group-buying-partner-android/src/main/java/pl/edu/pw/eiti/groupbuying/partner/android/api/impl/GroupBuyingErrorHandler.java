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
package pl.edu.pw.eiti.groupbuying.partner.android.api.impl;

import java.io.IOException;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import pl.edu.pw.eiti.groupbuying.partner.android.api.ApiError;
import pl.edu.pw.eiti.groupbuying.partner.android.util.ErrorCodeDeserializer;

public class GroupBuyingErrorHandler extends DefaultResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		ApiError apiError = null;
		try {
			if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				ObjectMapper mapper = new ObjectMapper();
				SimpleModule errorCodeModule = new SimpleModule("MyModule", new Version(1, 0, 0, null)).addDeserializer(ApiError.ErrorCode.class, new ErrorCodeDeserializer());
				mapper.registerModule(errorCodeModule);
				apiError = mapper.readValue(response.getBody(), ApiError.class);				
			}		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("apierror:" + apiError);
			if(apiError != null) {
				throw new ApiErrorException(apiError);
			} else {
				super.handleError(response);
			}
		}
	}
	
}
