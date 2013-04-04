package pl.edu.pw.eiti.groupbuying.android.api.impl;

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
		return restTemplate.getForObject(buildUri("cities/city-config", params), CityConfig.class);
	}

}
