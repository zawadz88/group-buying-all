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

@SuppressWarnings("serial")
public class OfferEssentialDTO implements Serializable {

	private int offerId;

	private String title;

	private String imageUrl;
	
	private double price;

	private double priceBeforeDiscount;
	
	private Date startDate;
	
	private Date endDate;
	
	private Category category;

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

	public OfferEssentialDTO(int offerId, String title, String imageUrl,
			double price, double priceBeforeDiscount, Date startDate,
			Date endDate, Category category) {
		super();
		this.offerId = offerId;
		this.title = title;
		this.imageUrl = imageUrl;
		this.price = price;
		this.priceBeforeDiscount = priceBeforeDiscount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.category = category;
	}	

}
