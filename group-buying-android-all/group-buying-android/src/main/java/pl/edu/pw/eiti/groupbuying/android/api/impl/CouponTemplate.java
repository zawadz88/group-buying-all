package pl.edu.pw.eiti.groupbuying.android.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pl.edu.pw.eiti.groupbuying.android.api.Coupon;
import pl.edu.pw.eiti.groupbuying.android.api.CouponOperations;

public class CouponTemplate extends AbstractGroupBuyingOperations implements CouponOperations {

	private final RestTemplate restTemplate;

	public CouponTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, String apiUrlBase) {
		super(isAuthorizedForUser, apiUrlBase);
		this.restTemplate = restTemplate;
	}

	@Override
	public Coupon[] getCoupons() {
		// Set the Accept header for "application/json"
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(acceptableMediaTypes);
		requestHeaders.add("Connection", "close");

		// Populate the headers in an HttpEntity object to use for the request
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<Coupon[]> responseEntity = restTemplate.exchange(buildUri("account/coupon/coupons"), HttpMethod.GET, requestEntity, Coupon[].class);
		Coupon[] offers = responseEntity.getBody();
		if (offers != null) {
			return offers;
		} else {
			return new Coupon[0];
		}
	}

}
