package pl.edu.pw.eiti.groupbuying.rest.controller;

import java.security.Principal;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.pw.eiti.groupbuying.core.dto.CouponDTO;
import pl.edu.pw.eiti.groupbuying.rest.exception.InternalServerErrorException;
import pl.edu.pw.eiti.groupbuying.rest.model.ApiError.ErrorCode;
import pl.edu.pw.eiti.groupbuying.rest.service.CouponService;

/**
 * Controller for handling coupon operations
 * @author Piotr Zawadzki
 *
 */
@Controller
@RequestMapping("/account/coupon")
public class CouponController {

	private static final Logger LOG = Logger.getLogger(CouponController.class);
	
	@Autowired
	private CouponService couponService;
	
	/**
	 * Returns a list of client's coupons
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/coupons")
	public @ResponseBody List<CouponDTO> getCoupons(Principal principal) {
		
		List<CouponDTO> coupons = null;		
		
		try {
			coupons = couponService.getCouponsForClientId(principal.getName());
		} catch (DataAccessException e) {
			LOG.error("DB error occured in getCoupons", e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (PersistenceException e) {
			LOG.error("DB error occured in getCoupons", e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (Exception e) {
			LOG.error("Internal server error occured in getCoupons", e);
			throw new InternalServerErrorException("Unknown error", ErrorCode.UNKNOWN_ERROR);
		}
		
		return coupons;			
	}
	
}
