package com.ekyc.beans;
import java.io.Serializable;
import java.util.*;
import java.util.stream.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.function.*;
public class DocumentList implements Serializable{

	/*
	 */
	private static final long serialVersionUID = -714040022871159370L;


	@JsonProperty("documentPhoto")
	private String ekycPhotoUrl;
	@JsonProperty("selfie")
	private String originalPhotoUrl;
	@JsonProperty("thumbImpression")
	private String thumbImpressionUrl;
	@JsonProperty("verifiedPhoto")
	private String verifiedPhotoUrl;
	
	
	@JsonProperty("bank")
	private String document1Url;
	@JsonProperty("electricity")
	private String document2Url;
	@JsonProperty("permit")
	private String document3Url;
	@JsonProperty("other")
	private String document4Url;
	@JsonProperty("UID")
	private String document5Url;
	@JsonProperty("water")
	private String document6Url;
	@JsonProperty("telecom")
	private String document7Url;
	@JsonProperty("consent")
	private String document8Url;
	@JsonProperty("incorporationLetter")
	private String document9Url;
	@JsonProperty("vat")
	private String document10Url;
	@JsonProperty("brn")
	private String document11Url;
	@JsonProperty("utility")
	private String document12Url;
	@JsonProperty("authorizationLetter")
	private String document13Url;
	@JsonProperty("emp_utility_org")
	private String document14Url;
	@JsonProperty("touristCert")
	private String document15Url;
	@JsonProperty("authorizationLetter_part2")
	private String document16Url;
	@JsonProperty("workPermit")
	private String document17Url;
	@JsonProperty("authorizationLetter_part3")
	private String document18Url;
	@JsonProperty("policeMemo")
	private String document19Url;
	@JsonProperty("applicationWithDamageSim")
	private String document20Url;
	
	public String getEkycPhotoUrl() {
		return ekycPhotoUrl;
	}
	public void setEkycPhotoUrl(String ekycPhotoUrl) {
		this.ekycPhotoUrl = ekycPhotoUrl;
	}
	public String getOriginalPhotoUrl() {
		return originalPhotoUrl;
	}
	public void setOriginalPhotoUrl(String originalPhotoUrl) {
		this.originalPhotoUrl = originalPhotoUrl;
	}
	public String getThumbImpressionUrl() {
		return thumbImpressionUrl;
	}
	public void setThumbImpressionUrl(String thumbImpressionUrl) {
		this.thumbImpressionUrl = thumbImpressionUrl;
	}
	public String getVerifiedPhotoUrl() {
		return verifiedPhotoUrl;
	}
	public void setVerifiedPhotoUrl(String verifiedPhotoUrl) {
		this.verifiedPhotoUrl = verifiedPhotoUrl;
	}
	public String getDocument1Url() {
		return document1Url;
	}
	public void setDocument1Url(String document1Url) {
		this.document1Url = document1Url;
	}
	public String getDocument2Url() {
		return document2Url;
	}
	public void setDocument2Url(String document2Url) {
		this.document2Url = document2Url;
	}
	public String getDocument3Url() {
		return document3Url;
	}
	public void setDocument3Url(String document3Url) {
		this.document3Url = document3Url;
	}
	public String getDocument4Url() {
		return document4Url;
	}
	public void setDocument4Url(String document4Url) {
		this.document4Url = document4Url;
	}
	public String getDocument5Url() {
		return document5Url;
	}
	public void setDocument5Url(String document5Url) {
		this.document5Url = document5Url;
	}
	public String getDocument6Url() {
		return document6Url;
	}
	public void setDocument6Url(String document6Url) {
		this.document6Url = document6Url;
	}
	public String getDocument7Url() {
		return document7Url;
	}
	public void setDocument7Url(String document7Url) {
		this.document7Url = document7Url;
	}
	public String getDocument8Url() {
		return document8Url;
	}
	public void setDocument8Url(String document8Url) {
		this.document8Url = document8Url;
	}
	public String getDocument9Url() {
		return document9Url;
	}
	public void setDocument9Url(String document9Url) {
		this.document9Url = document9Url;
	}
	public String getDocument10Url() {
		return document10Url;
	}
	public void setDocument10Url(String document10Url) {
		this.document10Url = document10Url;
	}
	public String getDocument11Url() {
		return document11Url;
	}
	public void setDocument11Url(String document11Url) {
		this.document11Url = document11Url;
	}
	public String getDocument12Url() {
		return document12Url;
	}
	public void setDocument12Url(String document12Url) {
		this.document12Url = document12Url;
	}
	public String getDocument13Url() {
		return document13Url;
	}
	public void setDocument13Url(String document13Url) {
		this.document13Url = document13Url;
	}
	public String getDocument14Url() {
		return document14Url;
	}
	public void setDocument14Url(String document14Url) {
		this.document14Url = document14Url;
	}
	public String getDocument15Url() {
		return document15Url;
	}
	public void setDocument15Url(String document15Url) {
		this.document15Url = document15Url;
	}
	public String getDocument16Url() {
		return document16Url;
	}
	public void setDocument16Url(String document16Url) {
		this.document16Url = document16Url;
	}
	public String getDocument17Url() {
		return document17Url;
	}
	public void setDocument17Url(String document17Url) {
		this.document17Url = document17Url;
	}
	public String getDocument18Url() {
		return document18Url;
	}
	public void setDocument18Url(String document18Url) {
		this.document18Url = document18Url;
	}
	public String getDocument19Url() {
		return document19Url;
	}
	public void setDocument19Url(String document19Url) {
		this.document19Url = document19Url;
	}
	public String getDocument20Url() {
		return document20Url;
	}
	public void setDocument20Url(String document20Url) {
		this.document20Url = document20Url;
	}

	
	
	

}
