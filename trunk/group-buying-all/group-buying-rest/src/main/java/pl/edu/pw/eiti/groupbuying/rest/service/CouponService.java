package pl.edu.pw.eiti.groupbuying.rest.service;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.dto.CouponDTO;

/**
 * Service for coupon operations
 * @author Piotr Zawadzki
 *
 */
public interface CouponService {
	
	/**
	 * Creates a coupon
	 * @param client client to which the coupon belongs to
	 * @param offer offer that the coupon was purchased for
	 * @return
	 */
	boolean createCoupon(Client client, Offer offer);
	
	/**
	 * Returns a list of available coupons for a given email address
	 * @param email user's email
	 * @return
	 */
	List<CouponDTO> getCouponsForClientId(String email);
	
	/**
	 * Returns a coupon based on it's ID
	 * @param couponId
	 * @return
	 */
	CouponDTO getCoupon(int couponId);
}
