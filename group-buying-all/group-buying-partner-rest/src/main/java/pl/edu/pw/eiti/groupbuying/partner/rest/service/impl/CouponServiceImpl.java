package pl.edu.pw.eiti.groupbuying.partner.rest.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pw.eiti.groupbuying.core.dao.CouponDAO;
import pl.edu.pw.eiti.groupbuying.partner.rest.service.CouponService;

@Service("couponService")
public class CouponServiceImpl implements CouponService {
	
	private static final Logger LOG = Logger.getLogger(CouponServiceImpl.class);

	@Autowired
	private CouponDAO couponDAO;

}
