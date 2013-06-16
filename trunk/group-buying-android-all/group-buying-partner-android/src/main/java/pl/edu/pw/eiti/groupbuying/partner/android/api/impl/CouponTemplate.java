package pl.edu.pw.eiti.groupbuying.partner.android.api.impl;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import pl.edu.pw.eiti.groupbuying.partner.android.api.ClaimResponse;
import pl.edu.pw.eiti.groupbuying.partner.android.api.CouponInfo;
import pl.edu.pw.eiti.groupbuying.partner.android.api.CouponOperations;

public class CouponTemplate extends AbstractGroupBuyingOperations implements CouponOperations {

	private final RestTemplate restTemplate;

	public CouponTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, String apiUrlBase) {
		super(isAuthorizedForUser, apiUrlBase);
		this.restTemplate = restTemplate;
	}

	@Override
	public ClaimResponse claimCoupon(CouponInfo couponInfo) {
		ClaimResponse claimResponse = null;
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		claimResponse = restTemplate.postForObject(buildUri("account/coupon/claim"), couponInfo, ClaimResponse.class);
		return claimResponse;

	}

}
