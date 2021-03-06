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

/**
 * A DTO for an entity containing selective information about an offer
 * @author Piotr Zawadzki
 *
 */
@SuppressWarnings("serial")
public class OfferEssentialDTO implements Serializable {
	
	/**
	 * Unique identifier
	 */
	private int offerId;
	
	/**
	 * Title of the offer
	 */
	private String title;

	/**
	 * URL address of an image
	 */
	private String imageUrl;

	/**
	 * Price of this offer
	 */
	private double price;

	/**
	 * Price of the offer before the discount
	 */
	private double priceBeforeDiscount;

	/**
	 * Time when this offer started being available
	 */
	private Date startDate;

	/**
	 * Time when this offer stopped being available
	 */
	private Date endDate;

	/**
	 * Category this offer belongs to
	 */
	private Category category;

	/**
	 * Latitude of the place
	 */
	private Double latitude;

	/**
	 * Longitude of the place
	 */
	private Double longitude;	

	/**
	 * Number of offers sold
	 */
	private int soldCount;
	
	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPriceBeforeDiscount() {
		return priceBeforeDiscount;
	}

	public void setPriceBeforeDiscount(double priceBeforeDiscount) {
		this.priceBeforeDiscount = priceBeforeDiscount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public int getSoldCount() {
		return soldCount;
	}

	public void setSoldCount(int soldCount) {
		this.soldCount = soldCount;
	}

	public OfferEssentialDTO(int offerId, String title, String imageUrl,
			double price, double priceBeforeDiscount, Date startDate,
			Date endDate, Category category, Double latitude, Double longitude, int soldCount) {
		super();
		this.offerId = offerId;
		this.title = title;
		this.imageUrl = imageUrl;
		this.price = price;
		this.priceBeforeDiscount = priceBeforeDiscount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.category = category;
		this.latitude = latitude;
		this.longitude = longitude;
		this.soldCount = soldCount;
	}	

}
