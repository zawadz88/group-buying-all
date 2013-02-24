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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
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
	private State state;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="category_id")
	private Category category = new Category();

	@Column(name="username")
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

	@Override
	public String toString() {
		return "Offer [offerId=" + offerId + ", title=" + title + ", lead="
				+ lead + ", description=" + description + ", conditions="
				+ conditions + ", address=" + address + ", imageUrl="
				+ imageUrl + ", price=" + price + ", priceBeforeDiscount="
				+ priceBeforeDiscount + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", expirationDate=" + expirationDate
				+ ", state=" + state + ", category=" + category + ", username="
				+ username + "]";
	}

	public static enum State {
		WAITING, ACTIVE, FINISHED;

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
