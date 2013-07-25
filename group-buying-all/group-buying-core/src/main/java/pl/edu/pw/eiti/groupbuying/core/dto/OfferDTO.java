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

import pl.edu.pw.eiti.groupbuying.core.domain.City;

/**
 * A DTO for an entity containing information about an offer
 * @author Piotr Zawadzki
 *
 */
@SuppressWarnings("serial")
public class OfferDTO implements Serializable {
	
	/**
	 * Unique identifier
	 */
	private int offerId;
	
	/**
	 * Title of the offer
	 */
	private String title;
	
	/**
	 * Short description
	 */
	private String lead;
	
	/**
	 * Long description
	 */
	private String description;
	
	/**
	 * Conditions for buying and usage of this offer
	 */
	private String conditions;
	
	/**
	 * Address where this offer can be claimed
	 */
	private AddressDTO address = new AddressDTO();
	
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
	 * Time when this offer can be last claimed
	 */
	private Date expirationDate;
	
	/**
	 * Offer's status
	 */
	private OfferState state;
	
	/**
	 * Category this offer belongs to
	 */
	private Category category;
	
	/**
	 * Seller who published the offer
	 */
	private SellerDTO seller;
	
	/**
	 * City this offer can be published in
	 */
	private City city;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public OfferState getState() {
		return state;
	}

	public void setState(OfferState state) {
		this.state = state;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public double getPriceBeforeDiscount() {
		return priceBeforeDiscount;
	}

	public void setPriceBeforeDiscount(double priceBeforeDiscount) {
		this.priceBeforeDiscount = priceBeforeDiscount;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public SellerDTO getSeller() {
		return seller;
	}

	public void setSeller(SellerDTO seller) {
		this.seller = seller;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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

	public OfferDTO() {
	}

	public OfferDTO(int offerId, String title, String lead, String description,
			String conditions, AddressDTO address, String imageUrl,
			double price, double priceBeforeDiscount, Date startDate,
			Date endDate, Date expirationDate, OfferState state,
			Category category, SellerDTO seller, City city, Double latitude, Double longitude, int soldCount) {
		super();
		this.offerId = offerId;
		this.title = title;
		this.lead = lead;
		this.description = description;
		this.conditions = conditions;
		this.address = address;
		this.imageUrl = imageUrl;
		this.price = price;
		this.priceBeforeDiscount = priceBeforeDiscount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.expirationDate = expirationDate;
		this.state = state;
		this.category = category;
		this.seller = seller;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.soldCount = soldCount;
	}

	@Override
	public String toString() {
		return "OfferDTO [offerId=" + offerId + ", title=" + title + ", lead="
				+ lead + ", description=" + description + ", conditions="
				+ conditions + ", address=" + address + ", imageUrl="
				+ imageUrl + ", price=" + price + ", priceBeforeDiscount="
				+ priceBeforeDiscount + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", expirationDate=" + expirationDate
				+ ", state=" + state + ", category=" + category + ", seller="
				+ seller + ", city=" + city + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", soldCount=" + soldCount + "]";
	}

}
