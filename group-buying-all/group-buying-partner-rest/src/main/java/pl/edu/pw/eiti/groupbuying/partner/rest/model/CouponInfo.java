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
package pl.edu.pw.eiti.groupbuying.partner.rest.model;

import java.io.Serializable;

/**
 * POJO containing basic coupon operations, i.e. ID and security key
 * @author Piotr Zawadzki
 *
 */
public class CouponInfo implements Serializable {

	/**
	 * Coupon's ID
	 */
	private int couponId;

	/**
	 * Security key
	 */
	private String securityKey;	


	public int getCouponId() {
		return couponId;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	@Override
	public String toString() {
		return "CouponInfo [couponId=" + couponId + ", securityKey=" + securityKey + "]";
	}
	

}
