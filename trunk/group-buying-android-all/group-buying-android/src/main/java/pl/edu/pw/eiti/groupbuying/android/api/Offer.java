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
package pl.edu.pw.eiti.groupbuying.android.api;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Offer implements Serializable {

	private int offerId;

	private String title;

	private String lead;

	private String description;

	private String conditions;

	private Address address = new Address();

	private String imageUrl;

	private double price;

	private double priceBeforeDiscount;

	private Date startDate;

	private Date endDate;

	private Date expirationDate;

	private OfferState state;

	private Category category;

	private Seller seller;
	
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
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

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public int getSoldCount() {
		return soldCount;
	}

	public void setSoldCount(int soldCount) {
		this.soldCount = soldCount;
	}

	public Offer() {
	}

	public Offer(int offerId, String title, String lead, String description,
			String conditions, Address address, String imageUrl,
			double price, double priceBeforeDiscount, Date startDate,
			Date endDate, Date expirationDate, OfferState state,
			Category category, Seller seller, int soldCount) {
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
		this.soldCount = soldCount;
	}

	@Override
	public String toString() {
		return "Offer [offerId=" + offerId + ", title=" + title + ", lead="
				+ lead + ", description=" + description + ", conditions="
				+ conditions + ", address=" + address + ", imageUrl="
				+ imageUrl + ", price=" + price + ", priceBeforeDiscount="
				+ priceBeforeDiscount + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", expirationDate=" + expirationDate
				+ ", state=" + state + ", category=" + category + ", seller="
				+ seller + ", soldCount=" + soldCount + "]";
	}

}
