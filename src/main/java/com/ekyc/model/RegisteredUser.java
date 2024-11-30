package com.ekyc.model;
import java.io.Serializable;
import java.util.*;
import java.util.stream.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.function.*;
@Entity
@Table(name="registered_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RegisteredUser implements Serializable{
	/*
	 */
	private static final long serialVersionUID = 8275984599787580879L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="District")
	private String district;
	@Column(name="Gender")
	private String gender;
	@Column(name="Family_Name")
	private String familyName;
	@Column(name="Registered_Name")
	private String registeredName;

public RegisteredUser() {
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getDistrict() {
	return district;
}

public void setDistrict(String district) {
	this.district = district;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public String getFamilyName() {
	return familyName;
}

public void setFamilyName(String familyName) {
	this.familyName = familyName;
}

public String getRegisteredName() {
	return registeredName;
}

public void setRegisteredName(String registeredName) {
	this.registeredName = registeredName;
}

@Override
public String toString() {
	return "{\"id\":\"" + id + "\", \"district\":\"" + district
			+ "\", \"gender\":\"" + gender + "\", \"familyName\":\""
			+ familyName + "\", \"registeredName\":\"" + registeredName + "\"}";
}

}
