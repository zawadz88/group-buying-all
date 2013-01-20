package pl.edu.pw.eiti.groupbuying.core.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Offer implements Serializable {

	public static final String STATE_WAITING = "0";
	
	public static final String STATE_ACTIVE = "1";

	public static final String STATE_FINISHED = "2";
	
	private int offerId;
	
	private String title;
	
	private String description;
	
	private Address address = new Address();
	
	private String imageUrl;
	
	private double price;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date startDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date endDate;
	
	private String state;
	
	private Category category = new Category();
	
	private String username;
	
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


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Offer [offerId=" + offerId + ", title=" + title
				+ ", description=" + description + ", address=" + address
				+ ", imageUrl=" + imageUrl + ", price=" + price
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", state=" + state + ", category=" + category + ", username="
				+ username + "]";
	}
	
}
