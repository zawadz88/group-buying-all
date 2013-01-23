package pl.edu.pw.eiti.groupbuying.core.domain;

import java.io.Serializable;

public class Seller implements Serializable{

	private String email;
	
	private String name;
	
	private String password;	

	private String phoneNumber;
	
	private String trade;
	
	private String description;

	private Address address = new Address();
	
	private String nip;
	
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

}
