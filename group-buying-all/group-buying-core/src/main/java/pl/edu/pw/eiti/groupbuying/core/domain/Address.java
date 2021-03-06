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

import javax.persistence.Column;
import javax.persistence.Embeddable;

import pl.edu.pw.eiti.groupbuying.core.dto.AddressDTO;

/**
 * An embeddable address entity consisting of fields such as city, street and postal code.
 * @author Piotr Zawadzki
 *
 */
@Embeddable
public class Address implements Serializable {

	/**
	 * The name of the city
	 */
	@Column(name = "city")
	private String city;

	/**
	 * The name of the stret
	 */
	@Column(name = "street")
	private String street;

	/**
	 * The value of the postal code
	 */
	@Column(name = "postal_code")
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
	
	/**
	 * Transforms this entity into an {@link AddressDTO} transfer object
	 * @return
	 */
	public AddressDTO getAddressDTO() {
		final AddressDTO addressDTO = new AddressDTO(city, street, postalCode);
		return addressDTO;
	}

}
