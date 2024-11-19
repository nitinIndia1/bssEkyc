package com.ekyc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="other_docs_required_count")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OtherDocRequiredCount {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="user_type")
	private String userType;

	
	@Column(name="counts")
	private String counts;


	public OtherDocRequiredCount(int id, String userType, String counts) {
		this.id = id;
		this.userType = userType;
		this.counts = counts;
	}


	public OtherDocRequiredCount() {
	
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public String getCounts() {
		return counts;
	}


	public void setCounts(String counts) {
		this.counts = counts;
	}

	
	
}
