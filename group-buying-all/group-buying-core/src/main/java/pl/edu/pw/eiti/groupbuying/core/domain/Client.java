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

/**
 * An entity representing a client account
 * @author Piotr Zawadzki
 *
 */
@Entity
@Table(name="clients")
public class Client implements Serializable {
	
	/**
	 * Client's email, this is also a unique ID
	 */
	@Id
	@Column(name = "email")
	private String email;

	/**
	 * Account's password
	 */
	@Column(name = "password")
	private String password;
	
	/**
	 * First name
	 */
	@Column(name = "first_name")
	private String firstName;

	/**
	 * Last name
	 */
	@Column(name = "last_name")
	private String lastName;
	
	/**
	 * Client's address
	 */
	@Embedded
	private Address address = new Address();

	/**
	 * Client's phone number
	 */
	@Column(name = "phone_number")
	private String phoneNumber;

	/**
	 * Salt used for extra security when encoding the password
	 */
	@Column(name = "salt")
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "Client [email=" + email + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", address=" + address + ", phoneNumber=" + phoneNumber
				+ ", salt=" + salt + "]";
	}


}
