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

import pl.edu.pw.eiti.groupbuying.android.api.Offer;
import pl.edu.pw.eiti.groupbuying.android.api.OfferEssential;
import pl.edu.pw.eiti.groupbuying.android.api.OfferOperations;

public class OfferTemplate extends AbstractGroupBuyingOperations implements
		OfferOperations {

	private final RestTemplate restTemplate;

	public OfferTemplate(RestTemplate restTemplate,
			boolean isAuthorizedForUser, String apiUrlBase) {
		super(isAuthorizedForUser, apiUrlBase);
		this.restTemplate = restTemplate;
	}

	@Override
	public Offer getOfferById(int id) {
		return restTemplate.getForObject(buildUri("offers/offer/" + id), Offer.class);
	}

	@Override
	public List<OfferEssential> getOffers(String category, int pageNumber) {
		// Set the Accept header for "application/json"
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(acceptableMediaTypes);
		requestHeaders.add("Connection", "close");

		// Populate the headers in an HttpEntity object to use for the request
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<OfferEssential[]> responseEntity = restTemplate.exchange(
				buildUri("offers/" + category, "page",Integer.toString(pageNumber)),
				HttpMethod.GET,
				requestEntity,
				OfferEssential[].class);
		OfferEssential[] offers = responseEntity.getBody();
		if (offers != null) {
			return Arrays.asList(offers);
		} else {
			return new ArrayList<OfferEssential>();
		}
	}

}
