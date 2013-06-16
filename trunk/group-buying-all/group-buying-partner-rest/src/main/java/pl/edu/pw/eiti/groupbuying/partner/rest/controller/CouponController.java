package pl.edu.pw.eiti.groupbuying.partner.rest.controller;

import java.security.Principal;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.partner.rest.exception.InternalServerErrorException;
import pl.edu.pw.eiti.groupbuying.partner.rest.model.ApiError.ErrorCode;
import pl.edu.pw.eiti.groupbuying.partner.rest.model.ClaimResponse;
import pl.edu.pw.eiti.groupbuying.partner.rest.model.CouponInfo;
import pl.edu.pw.eiti.groupbuying.partner.rest.service.CouponService;

@Controller
@RequestMapping("/account/coupon")
public class CouponController {

	private static final Logger LOG = Logger.getLogger(CouponController.class);
	
	@Autowired
	private CouponService couponService;
	
	@RequestMapping(value = "/claim")
	public @ResponseBody ClaimResponse claimCoupon(Principal principal, @RequestBody CouponInfo couponInfo ) {
		ClaimResponse response = null;
		try {
			response = couponService.claimCoupon(couponInfo.getCouponId(), couponInfo.getSecurityKey(), principal.getName());
		} catch (DataAccessException e) {
			LOG.error("DB error occured in claimCoupon", e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (PersistenceException e) {
			LOG.error("DB error occured in claimCoupon", e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		}
		
		return response;			
	}
	
}
