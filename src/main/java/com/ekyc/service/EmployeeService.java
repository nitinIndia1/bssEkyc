package com.ekyc.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

import com.ekyc.beans.DocumentUpdateRequest;
import com.ekyc.beans.EmployeeOrganisationMsisdn_;
import com.ekyc.beans.EmployeeSaveDetail;
import com.ekyc.beans.IdRequest;
import com.ekyc.beans.IdRequest3;
import com.ekyc.beans.MsisdnUpdate;
import com.ekyc.beans.OcrId;
import com.ekyc.beans.OrganisationSaveDetail;
import com.ekyc.model.Organisation;
import com.ekyc.utils.CoreResponseHandler;

public interface EmployeeService {
	ResponseEntity<CoreResponseHandler> callFirstOcr(String orgToken,String orgPin,IdRequest idRequest);
	ResponseEntity<CoreResponseHandler> updateEmployeeDocument(DocumentUpdateRequest documentUpdateRequest);
	ResponseEntity<CoreResponseHandler> employeeGet(String orgToken,String orgPin);
	
	
	
	ResponseEntity<CoreResponseHandler> updateEmployeeWithSelfie(OcrId  ocrId);
	
	ResponseEntity<CoreResponseHandler> nicVerify(JSONObject  obj);
	
	ResponseEntity<CoreResponseHandler> passportVerify(JSONObject  obj);
	ResponseEntity<CoreResponseHandler> emputility(IdRequest3  obj);
	ResponseEntity<CoreResponseHandler> empauthorizationletter(IdRequest3  obj);
	
	
	ResponseEntity<CoreResponseHandler> findByOrganisationByAdmin(Organisation organisation);
	ResponseEntity<CoreResponseHandler> msisdnUpdate(MsisdnUpdate msisdnUpdate);
	
	ResponseEntity<CoreResponseHandler> address(String type,String token,String address,String email,String alternateNumber);
	
	ResponseEntity<CoreResponseHandler> employeeorganisationmsisdnsave(EmployeeOrganisationMsisdn_ employeeOrganisationMsisdn_);

	ResponseEntity<CoreResponseHandler> employeeorganisationmsisdnupdate(String empToken,String orgToken,String msisdn,String status);

	

}
