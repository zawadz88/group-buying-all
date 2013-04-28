package pl.edu.pw.eiti.groupbuying.rest.service.impl;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.core.dao.CouponDAO;
import pl.edu.pw.eiti.groupbuying.core.domain.Client;
import pl.edu.pw.eiti.groupbuying.core.domain.Coupon;
import pl.edu.pw.eiti.groupbuying.core.domain.Coupon.CouponState;
import pl.edu.pw.eiti.groupbuying.core.domain.Offer;
import pl.edu.pw.eiti.groupbuying.core.dto.CouponDTO;
import pl.edu.pw.eiti.groupbuying.rest.service.CouponService;

@Service("couponService")
public class CouponServiceImpl implements CouponService {
	
	private static final Logger LOG = Logger.getLogger(CouponServiceImpl.class);

	@Autowired
	private CouponDAO couponDAO;

	@Override
	public boolean createCoupon(Client client, Offer offer) {
		Coupon coupon = new Coupon();
		coupon.setClient(client);
		coupon.setOffer(offer);
		coupon.setCouponState(CouponState.BOUGHT);
		coupon.setUseDate(null);
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		    messageDigest.reset();
			Random rand = new Random(System.currentTimeMillis());
			String randomString = Integer.toString(rand.nextInt(99999)) + client.getEmail();
		    messageDigest.update(randomString.getBytes(Charset.forName("UTF8")));
		    byte[] digestedBytes = messageDigest.digest();
			String securityKey = new String(Hex.encodeHex(digestedBytes)).substring(0, 10);
			coupon.setSecurityKey(securityKey);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getLocalizedMessage(), e);
		} 
		couponDAO.persistCoupon(coupon);
		return true;
	}
	
	@Override
	public List<CouponDTO> getCouponsForClientId(String email) {
		List<CouponDTO> couponDTOs = new ArrayList<CouponDTO>();
		List<Coupon> coupons = couponDAO.getCouponsForClientId(email);
		if(coupons != null) {
			for(Coupon coupon : coupons) {
				couponDTOs.add(coupon.getCouponDTO());
			}
		}
		return couponDTOs;
	}

	@Override
	public CouponDTO getCoupon(int couponId) {
		Coupon coupon = couponDAO.getCoupon(couponId);
		return coupon != null ? coupon.getCouponDTO() : null;
	}


}
