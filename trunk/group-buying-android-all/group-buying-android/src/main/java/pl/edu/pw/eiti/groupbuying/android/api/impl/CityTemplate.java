package pl.edu.pw.eiti.groupbuying.android.api.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import pl.edu.pw.eiti.groupbuying.android.api.City;
import pl.edu.pw.eiti.groupbuying.android.api.CityOperations;

public class CityTemplate extends AbstractGroupBuyingOperations implements CityOperations {

	private final RestTemplate restTemplate;

	public CityTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, String apiUrlBase) {
		super(isAuthorizedForUser, apiUrlBase);
		this.restTemplate = restTemplate;
	}

	@Override
	public City getDefaultCity() {
		return restTemplate.getForObject(buildUri("cities/default-city"), City.class);
	}

	@Override
	public City getNearestCity(double latitude, double longitude) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("latitude", Double.toString(latitude));
		params.set("longitude", Double.toString(longitude));
		return restTemplate.getForObject(buildUri("cities/nearest-city", params), City.class);
	}

	@Override
	public ArrayList<City> getCities() {
		// Set the Accept header for "application/json"
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(acceptableMediaTypes);

		// Populate the headers in an HttpEntity object to use for the request
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<City[]> responseEntity = restTemplate.exchange(
				buildUri("cities/list"),
				HttpMethod.GET,
				requestEntity,
				City[].class);
		City[] cities = responseEntity.getBody();
		if (cities != null) {			
			return new ArrayList<City>(Arrays.asList(cities));
		} else {
			return new ArrayList<City>();
		}		
	}

}
