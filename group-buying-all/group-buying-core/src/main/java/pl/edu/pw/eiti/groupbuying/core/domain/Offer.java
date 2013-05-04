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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Latitude;
import org.hibernate.search.annotations.Longitude;
import org.hibernate.search.annotations.Spatial;
import org.springframework.format.annotation.DateTimeFormat;

import pl.edu.pw.eiti.groupbuying.core.dto.Category;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferEssentialDTO;
import pl.edu.pw.eiti.groupbuying.core.dto.OfferState;

@Entity
@Indexed
@Spatial(name="loc")
@Table(name = "offers")
public class Offer implements Serializable {

	@Id
	@Column(name="offer_id")
	private int offerId;

	@Column(name="title")
	private String title;

	@Column(name="lead")
	private String lead;

	@Column(name="description")
	private String description;

	@Column(name="conditions")
	private String conditions;

	@Embedded
	private Address address = new Address();

	@Column(name="image_url")
	private String imageUrl;

	@Column(name="price")
	private double price;

	@Column(name="price_before_discount")
	private double priceBeforeDiscount;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name="start_date")
	private Date startDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name="end_date")
	private Date endDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name="expiration_date")
	private Date expirationDate;

	@Column(name="state")
    @Enumerated(EnumType.ORDINAL)
	private OfferState state;

	@JoinColumn(name="category")
    @Enumerated(EnumType.STRING)
	private Category category;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="username")
	private Seller seller;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="city_id")
	private City city = new City();

	@Column(name = "latitude")
	@Latitude(of="loc")
	private Double latitude;

	@Column(name = "longitude")
	@Longitude(of="loc")
	private Double longitude;
	
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

	@Override
	public String toString() {
		return "Offer [offerId=" + offerId + ", title=" + title + ", lead=" + lead + ", description=" + description + ", conditions=" + conditions + ", address=" + address + ", imageUrl=" + imageUrl + ", price=" + price + ", priceBeforeDiscount=" + priceBeforeDiscount + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", expirationDate=" + expirationDate + ", state=" + state + ", category=" + category + ", seller=" + seller + ", city=" + city + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
	public OfferDTO getOfferDTO() {
		OfferDTO offerDTO = new OfferDTO(offerId, title, lead, description, conditions, address.getAddressDTO(), imageUrl, price, priceBeforeDiscount, startDate, endDate, expirationDate, state, category, seller.getSellerDTO(), city, latitude, longitude);
		return offerDTO;
	}
	
	public OfferEssentialDTO getOfferEssentialDTO() {
		OfferEssentialDTO offerEssentialDTO = new OfferEssentialDTO(offerId, title, imageUrl, price, priceBeforeDiscount, startDate, endDate, category, latitude, longitude);
		return offerEssentialDTO;
	}
	

}
