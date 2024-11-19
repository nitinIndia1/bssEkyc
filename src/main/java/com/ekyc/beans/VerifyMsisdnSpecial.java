package com.ekyc.beans;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class VerifyMsisdnSpecial implements Serializable{


	private static final long serialVersionUID = -5804967026465636114L;

	@NotBlank(message = "msisdn can not be blank")
	@Length(min = 1)
	private String msisdn;
	
	public VerifyMsisdnSpecial() {}
	
	public VerifyMsisdnSpecial(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	@Override
	public String toString() {
		return "VerifyMsisdnSpecial [msisdn=" + msisdn + "]";
	}
	
}
