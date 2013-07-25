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
package pl.edu.pw.eiti.groupbuying.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import pl.edu.pw.eiti.groupbuying.core.dto.CityDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.CouponDTO;

/**
 * An entity for a coupon bought by the client
 * @author Piotr Zawadzki
 *
 */
@Entity
@Table(name = "coupons")
public class Coupon implements Serializable {

	/**
	 * Coupon's unique identifier
	 */
	@Id
	@Column(name = "id")
	private int couponId;

	/**
	 * Date when the coupon was claimed
	 */
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "use_date")
	private Date useDate;

	/**
	 * Security key
	 */
	@Column(name = "security_key")
	private String securityKey;

	/**
	 * A client to which this coupon belongs to
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "email")
	private Client client;

	/**
	 * An offer for which this coupon was bought
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "offer_id")
	private Offer offer;
	
	/**
	 * State of the coupon
	 */
	@Column(name="coupon_state")
    @Enumerated(EnumType.ORDINAL)
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
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

	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", useDate=" + useDate
				+ ", securityKey=" + securityKey + ", client=" + client
				+ ", offer=" + offer + ", couponState=" + couponState + "]";
	}
	
	/**
	 * Represents different states of the coupon, which are {@code BOUGHT}, {@code REDEEMED} and {@code EXPIRED}
	 * @author Piotr Zawadzki
	 *
	 */
	public static enum CouponState {
		BOUGHT,
		REDEEMED,
		EXPIRED;

	}
	
	/**
	 * Transforms this entity into an {@link CouponDTO} transfer object
	 * @return
	 */
	public CouponDTO getCouponDTO() {
		return new CouponDTO(couponId, useDate, securityKey, offer.getOfferDTO(), couponState);
	}

}
