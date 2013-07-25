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
package pl.edu.pw.eiti.groupbuying.core.dto;

import java.io.Serializable;
import java.util.Date;

import pl.edu.pw.eiti.groupbuying.core.domain.Coupon.CouponState;
/**
 * A DTO for an entity for a coupon bought by the client
 * @author Piotr Zawadzki
 *
 */
public class CouponDTO implements Serializable {
	
	/**
	 * Coupon's unique identifier
	 */
	private int couponId;
	
	/**
	 * Date when the coupon was claimed
	 */
	private Date useDate;

	/**
	 * Security key
	 */
	private String securityKey;

	/**
	 * A client to which this coupon belongs to
	 */
	private OfferDTO offer;	

	/**
	 * State of the coupon
	 */
	private CouponState couponState;

	public int getCouponId() {
		return couponId;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	public OfferDTO getOffer() {
		return offer;
	}

	public void setOffer(OfferDTO offer) {
		this.offer = offer;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public CouponState getCouponState() {
		return couponState;
	}

	public void setCouponState(CouponState couponState) {
		this.couponState = couponState;
	}

	public CouponDTO(int couponId, Date useDate, String securityKey, OfferDTO offer, CouponState couponState) {
		super();
		this.couponId = couponId;
		this.useDate = useDate;
		this.securityKey = securityKey;
		this.offer = offer;
		this.couponState = couponState;
	}

	@Override
	public String toString() {
		return "CouponDTO [couponId=" + couponId + ", useDate=" + useDate
				+ ", securityKey=" + securityKey + ", offer=" + offer + ", couponState=" + couponState + "]";
	}

}
