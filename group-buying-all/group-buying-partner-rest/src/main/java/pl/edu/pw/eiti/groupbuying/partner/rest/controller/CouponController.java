package pl.edu.pw.eiti.groupbuying.partner.rest.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public @ResponseBody ClaimResponse claim(Principal principal, @RequestBody CouponInfo couponInfo ) {
		
		System.out.println("I am " + principal.getName());
		System.out.println("Coupon info: " + couponInfo);
		
		
		/*try {
		} catch (DataAccessException e) {
			LOG.error("DB error occured in getCoupons", e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (PersistenceException e) {
			LOG.error("DB error occured in getCoupons", e);
			throw new InternalServerErrorException("Database error", ErrorCode.DATABASE_ERROR);
		} catch (Exception e) {
			LOG.error("Internal server error occured in getCoupons", e);
			throw new InternalServerErrorException("Unknown error", ErrorCode.UNKNOWN_ERROR);
		}*/
		
		return ClaimResponse.CLAIMED;			
	}
	
}
