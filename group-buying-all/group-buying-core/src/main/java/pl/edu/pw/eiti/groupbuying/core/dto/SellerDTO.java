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
 * A DTO for an entity representing a publisher
 * @author Piotr Zawadzki
 *
 */
@SuppressWarnings("serial")
public class SellerDTO implements Serializable {
	
	/**
	 * Email address, this is also a unique identifier
	 */
	private String email;

	/**
	 * Name of the publisher
	 */
	private String name;

	/**
	 * Phone number
	 */
	private String phoneNumber;
	
	/**
	 * The name of the trade this seller specializes in
	 */
	private String trade;
	
	/**
	 * Information about the seller
	 */
	private String description;

	/**
	 * HQ's address
	 */
	private AddressDTO address = new AddressDTO();

	/**
	 * NIP number
	 */
	private String nip;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public SellerDTO(String email, String name, String phoneNumber,
			String trade, String description, AddressDTO address, String nip) {
		super();
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.trade = trade;
		this.description = description;
		this.address = address;
		this.nip = nip;
	}

	public SellerDTO() {
	}

}
