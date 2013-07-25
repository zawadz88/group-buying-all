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


/**
 * DAO for operations on {@link Coupon} entities
 * @author Piotr Zawadzki
 *
 */
public interface CouponDAO {

	/**
	 * Persists a coupon
	 * @param coupon
	 * @return
	 */
	boolean persistCoupon(Coupon coupon);
	
	/**
	 * Updates coupon info
	 * @param coupon
	 * @return
	 */
	boolean updateCoupon(Coupon coupon);

	/**
	 * Gets of the client's coupons
	 * @param email client's ID
	 * @return
	 */
	List<Coupon> getCouponsForClientId(String email);

	/**
	 * Returns a coupons from coupon's ID
	 * @param couponId
	 * @return a coupon object or null if it does not exist
	 */
	Coupon getCoupon(int couponId);

}
