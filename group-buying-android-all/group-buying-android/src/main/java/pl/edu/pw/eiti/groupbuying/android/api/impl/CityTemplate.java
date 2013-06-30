package pl.edu.pw.eiti.groupbuying.android.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import pl.edu.pw.eiti.groupbuying.android.api.CityConfig;
import pl.edu.pw.eiti.groupbuying.android.api.CityOperations;

public class CityTemplate extends AbstractGroupBuyingOperations implements CityOperations {

	private final RestTemplate restTemplate;

	public CityTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, String apiUrlBase) {
		super(isAuthorizedForUser, apiUrlBase);
		this.restTemplate = restTemplate;
	}

	@Override
	public CityConfig getCityConfig(Double latitude, Double longitude) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		if(latitude != null && longitude != null) {
			params.set("latitude", latitude.toString());
			params.set("longitude", longitude.toString());			
		}		
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(acceptableMediaTypes);
		requestHeaders.add("Connection", "close");
		
		// Populate the headers in an HttpEntity object to use for the request
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<CityConfig> responseEntity = restTemplate.exchange(
				buildUri("cities/city-config", params),
				HttpMethod.GET,
				requestEntity,
				CityConfig.class);
		CityConfig cityConfig = responseEntity.getBody();		
		return cityConfig;
	}

}
