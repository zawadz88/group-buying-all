package pl.edu.pw.eiti.groupbuying.android.api.impl;

import org.springframework.web.client.RestTemplate;

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
		return restTemplate.getForObject(buildUri("paypal/mecl/checkout/" + id, "drt", drt), String.class);
	}

}
