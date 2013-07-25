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

/**
 * A DTO for address entity consisting of fields such as city, street and postal code.
 * @author Piotr Zawadzki
 *
 */
@SuppressWarnings("serial")
public class AddressDTO implements Serializable {
	
	/**
	 * The name of the city
	 */
	private String city;
	
	/**
	 * The name of the stret
	 */
	private String street;
	
	/**
	 * The value of the postal code
	 */
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

	public AddressDTO(String city, String street, String postalCode) {
		super();
		this.city = city;
		this.street = street;
		this.postalCode = postalCode;
	}

	public AddressDTO() {
	}

	@Override
	public String toString() {
		return "AddressDTO [city=" + city + ", street=" + street
				+ ", postalCode=" + postalCode + "]";
	}

}
