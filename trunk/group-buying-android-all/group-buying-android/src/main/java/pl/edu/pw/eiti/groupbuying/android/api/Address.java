package pl.edu.pw.eiti.groupbuying.android.api;

import java.io.Serializable;

public class Address implements Serializable {

	private String city;

	private String street;
	
	private String postalCode;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "Address [city=" + city + ", street=" + street + ", postalCode="
				+ postalCode + "]";
	}

}
