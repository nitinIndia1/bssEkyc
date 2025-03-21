package com.ekyc.service;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ekyc.beans.IdRequest;
import com.ekyc.beans.NoKYC;
import com.ekyc.beans.OcrId;
import com.ekyc.model.CustomerDetail;
import com.ekyc.utils.CoreResponseHandler;

public interface CustomerDetailService {

	ResponseEntity<CoreResponseHandler> saveNoKYCCustomer(NoKYC ocrId,String self);
	ResponseEntity<CoreResponseHandler> saveCustomer(OcrId ocrId,IdRequest idRequest,String self);
	CustomerDetail updateCustomer(CustomerDetail customerDetail,String strDate);
	
	CustomerDetail findByToken(String token);
	
	CustomerDetail findByID(String id);
	
	void deleteDuplicateCustomer(CustomerDetail customerDetail);
	
	
	List<CustomerDetail> fetchByDate(String date);
	
	CustomerDetail findByMsisdn(String msisdn);
	List<CustomerDetail> findByTokenOrName(String value);
	
}
