package pl.edu.pw.eiti.groupbuying.partner.rest.service;

import pl.edu.pw.eiti.groupbuying.partner.rest.model.ClaimResponse;

/**
 * Service that handles the processing of coupon operations
 * @author Piotr Zawadzki
 *
 */
public interface CouponService {

	/**
	 * Claims a coupon
	 * @param couponId ID of the coupon
	 * @param securityKey security key
	 * @param username seller's username
	 * @return
	 */
	ClaimResponse claimCoupon(int couponId, String securityKey, String username);
}
