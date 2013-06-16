package pl.edu.pw.eiti.groupbuying.partner.rest.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.core.dao.CouponDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Coupon;
import pl.edu.pw.eiti.groupbuying.core.domain.Coupon.CouponState;
import pl.edu.pw.eiti.groupbuying.partner.rest.exception.BadRequestException;
import pl.edu.pw.eiti.groupbuying.partner.rest.model.ApiError.ErrorCode;
import pl.edu.pw.eiti.groupbuying.partner.rest.model.ClaimResponse;
import pl.edu.pw.eiti.groupbuying.partner.rest.service.CouponService;

@Service("couponService")
public class CouponServiceImpl implements CouponService {
	
	private static final Logger LOG = Logger.getLogger(CouponServiceImpl.class);

	@Autowired
	private CouponDAO couponDAO;


	@Override
	public ClaimResponse claimCoupon(int couponId, String securityKey, String username) {
		Coupon coupon = couponDAO.getCoupon(couponId);
		if(coupon == null) {
			throw new BadRequestException("Coupon with ID: '" + couponId + "' not found.", ErrorCode.COUPON_NOT_FOUND);
		}
		if(!coupon.getOffer().getSeller().getEmail().equals(username)) {
			throw new BadRequestException("Coupon '" + couponId + "' belongs to another partner.", ErrorCode.INVALID_PARTNER);
		}
		if(!coupon.getSecurityKey().equals(securityKey)) {
			throw new BadRequestException("Invalid security key.", ErrorCode.INVALID_SECURITY_KEY);
		}
		ClaimResponse response = null;
		switch (coupon.getCouponState()) {
		case BOUGHT:
			response = ClaimResponse.CLAIMED;
			coupon.setUseDate(new Date());
			coupon.setCouponState(CouponState.REDEEMED);
			couponDAO.updateCoupon(coupon);
			break;
		case EXPIRED:
			response = ClaimResponse.EXPIRED;
			break;
		case REDEEMED:
			response = ClaimResponse.ALREADY_USED;
			break;
		}
		
		return response;
	}

}
