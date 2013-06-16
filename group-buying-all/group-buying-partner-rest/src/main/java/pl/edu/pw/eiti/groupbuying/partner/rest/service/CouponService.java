package pl.edu.pw.eiti.groupbuying.partner.rest.service;

import pl.edu.pw.eiti.groupbuying.partner.rest.model.ClaimResponse;

public interface CouponService {

	ClaimResponse claimCoupon(int couponId, String securityKey, String username);
}
