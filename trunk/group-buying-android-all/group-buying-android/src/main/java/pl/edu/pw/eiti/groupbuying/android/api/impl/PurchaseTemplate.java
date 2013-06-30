package pl.edu.pw.eiti.groupbuying.android.api.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pl.edu.pw.eiti.groupbuying.android.api.OfferEssential;
import pl.edu.pw.eiti.groupbuying.android.api.PurchaseOperations;

public class PurchaseTemplate extends AbstractGroupBuyingOperations implements PurchaseOperations {

	private final RestTemplate restTemplate;

	public PurchaseTemplate(RestTemplate restTemplate,
			boolean isAuthorizedForUser, String apiUrlBase) {
		super(isAuthorizedForUser, apiUrlBase);
		this.restTemplate = restTemplate;
	}

	@Override
	public String getPaypalRedirectURI(int id, String drt) {
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(acceptableMediaTypes);
		requestHeaders.add("Connection", "close");
		
		// Populate the headers in an HttpEntity object to use for the request
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				buildUri("paypal/mecl/checkout/" + id, "drt", drt),
				HttpMethod.GET,
				requestEntity,
				String.class);
		String redirectURI = responseEntity.getBody();		
		return redirectURI;
	}

}
