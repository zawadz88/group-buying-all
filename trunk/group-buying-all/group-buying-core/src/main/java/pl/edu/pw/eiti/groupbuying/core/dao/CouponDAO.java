/*******************************************************************************
 * Copyright (c) 2013 Piotr Zawadzki.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Piotr Zawadzki - initial API and implementation
 ******************************************************************************/
package pl.edu.pw.eiti.groupbuying.core.dao;

import java.util.List;

import pl.edu.pw.eiti.groupbuying.core.domain.Coupon;


public interface CouponDAO {

	boolean persistCoupon(Coupon coupon);
	
	boolean updateCoupon(Coupon coupon);

	List<Coupon> getCouponsForClientId(String email);

	Coupon getCoupon(int couponId);

}
