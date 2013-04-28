package pl.edu.pw.eiti.groupbuying.rest.service;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.dto.CouponDTO;

public interface CouponService {
	
	boolean createCoupon(Client client, Offer offer);
	
	List<CouponDTO> getCouponsForClientId(String email);
	
	CouponDTO getCoupon(int couponId);
}
