package com.ekyc.beans;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class NoKYC implements Serializable{

	private static final long serialVersionUID = 1L;

	private String countryCode;
	@NotBlank(message = "dob can not be blank")
	private String dob;
	@NotBlank(message = "firstname can not be blank")
	private String firstName;
	@NotBlank(message = "gender can not be blank")
	private String gender;
	private String id;
	private String lastName;
//	@NotBlank(message = "selfie64 can not be blank")
//	private String selfie64;
	
	
	@NotBlank(message = "address can not be blank")
	private String address;
	@NotBlank(message = "email can not be blank")
	private String email;
	//@NotBlank(message = "token can not be blank")
	private String alternateNumber;

	
	
	public NoKYC() {}

	
	
	
	
	public String getCountryCode() {
		return countryCode;
	}

public NoKYC(
		 String countryCode,
		@NotBlank(message = "dob can not be blank") String dob,
		@NotBlank(message = "firstName can not be blank") String firstName,
		@NotBlank(message = "gender can not be blank") String gender,
		String id, String lastName,
		@NotBlank(message = "address can not be blank") String address,
		@NotBlank(message = "email can not be blank") String email,
		String alternateNumber) {
	this.countryCode = countryCode;
	this.dob = dob;
	this.firstName = firstName;
	this.gender = gender;
	this.id = id;
	this.lastName = lastName;
	this.address = address;
	this.email = email;
	this.alternateNumber = alternateNumber;
}

public String getDob() {
	return dob;
}

public void setDob(String dob) {
	this.dob = dob;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getAlternateNumber() {
	return alternateNumber;
}

public void setAlternateNumber(String alternateNumber) {
	this.alternateNumber = alternateNumber;
}

public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
}


	
}
