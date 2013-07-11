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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import pl.edu.pw.eiti.groupbuying.core.dto.SellerDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sellers")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region="pl.edu.pw.eiti.groupbuying.core.domain.Seller", include="non-lazy")
public class Seller implements Serializable {

	@Id
	@Column(name = "email")
	private String email;

	@Column(name = "name")
	private String name;

	@Column(name = "password")
	@JsonIgnore
	private String password;	

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "trade")
	private String trade;

	@Column(name = "description")
	private String description;

	@Embedded
	private Address address = new Address();

	@Column(name = "nip")
	private String nip;

	@Column(name = "salt")
	@JsonIgnore
	private String salt;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
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

	@Override
	public String toString() {
		return "Seller [email=" + email + ", name=" + name + ", password="
				+ password + ", phoneNumber=" + phoneNumber + ", trade="
				+ trade + ", description=" + description + ", address="
				+ address + ", nip=" + nip + ", salt=" + salt + "]";
	}
	
	public SellerDTO getSellerDTO() {
		SellerDTO sellerDTO = new SellerDTO(email, name, phoneNumber, trade, description, address.getAddressDTO(), nip);
		return sellerDTO;
	}
}
