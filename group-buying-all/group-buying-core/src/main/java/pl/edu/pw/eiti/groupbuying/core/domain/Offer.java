package pl.edu.pw.eiti.groupbuying.core.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Offer implements Serializable {

	private int offerId;

	private String title;

	private String lead;

	private String description;

	private Address address = new Address();

	private String imageUrl;

	private double price;

	private double priceBeforeDiscount;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date startDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date endDate;

	private State state;

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

	public State getState() {
		return state;
	}

	public void setState(State state) {
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

	@Override
	public String toString() {
		return "Offer [offerId=" + offerId + ", title=" + title + ", lead="
				+ lead + ", description=" + description + ", address="
				+ address + ", imageUrl=" + imageUrl + ", price=" + price
				+ ", priceBeforeDiscount=" + priceBeforeDiscount
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", state=" + state + ", category=" + category + ", username="
				+ username + "]";
	}

	public static enum State {
		WAITING(0), ACTIVE(1), FINISHED(2);
		private int value;

		private State(final int number) {
			this.value = number;
		}

		public int getValue() {
			return value;
		}

		public static State getState(final int value) {
			State state = null;
			switch (value) {
			case 0:
				state = WAITING;
				break;
			case 1:
				state = ACTIVE;
				break;
			case 2:
				state = FINISHED;
				break;
			default:
				throw new IllegalArgumentException("value: '" + value
						+ "' not permitted for Offer.State!");
			}
			return state;
		}

	}

}
