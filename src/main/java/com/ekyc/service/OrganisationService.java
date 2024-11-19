package com.ekyc.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ekyc.beans.AdminLogin;
import com.ekyc.beans.AdminRegister;
import com.ekyc.beans.AdminSave;
import com.ekyc.beans.AgentActivation_;
import com.ekyc.beans.DocumentUpdateRequest;
import com.ekyc.beans.IdRequest;
import com.ekyc.beans.IdRequest2;
import com.ekyc.beans.OrgMsisdn_;
import com.ekyc.beans.OrganisationSaveDetail;
import com.ekyc.model.Organisation;
import com.ekyc.utils.CoreResponseHandler;

public interface OrganisationService {

	ResponseEntity<CoreResponseHandler> setUpOrganisation(OrganisationSaveDetail organisationSaveDetail);
	ResponseEntity<CoreResponseHandler> updateOrganisationDocument(DocumentUpdateRequest documentUpdateRequest,String type);
	ResponseEntity<CoreResponseHandler> organisationGet(String orgToken,String orgPin);

	Organisation organisationGet_(String orgToken,String orgPin);
	
	ResponseEntity<CoreResponseHandler> getOrganisation(String msisdn);
	
	ResponseEntity<CoreResponseHandler> processOrganisationDocument(IdRequest2 idRequest2);
	
	
	ResponseEntity<CoreResponseHandler> organisationTypeDate(String orgType,String date);
	
	
	ResponseEntity<CoreResponseHandler> adminSave(String orgToken,String orgPin,AdminSave adminSave);
	
	ResponseEntity<CoreResponseHandler> organisationType(String orgType);
	
	ResponseEntity<CoreResponseHandler> getEmployeeByAdmin(String userName,String password);
	
	ResponseEntity<CoreResponseHandler> saveAdminActivation(AgentActivation_ agentActivation_);
	
	ResponseEntity<CoreResponseHandler> adminGet(String msisdn);
	
	ResponseEntity<CoreResponseHandler> checkAcctid(String acctId);
	
	ResponseEntity<CoreResponseHandler> checkBrn(String brn);
	
	ResponseEntity<CoreResponseHandler> checkCert(String cert);
	ResponseEntity<CoreResponseHandler> checkExpiryDate(String expiryDate);
	ResponseEntity<CoreResponseHandler> moveAdminToEmployee(String token);
	ResponseEntity<CoreResponseHandler> updateOrganisationAdmin(String find_token,String set_token);
	ResponseEntity<CoreResponseHandler> setOrganisationAdmin(AdminRegister adminRegister);
	ResponseEntity<CoreResponseHandler> organisationadminemployeelogin(AdminLogin adminLogin);
	ResponseEntity<CoreResponseHandler> allocateMsisdnsToOrganisation(OrgMsisdn_ orgMsisdn_);
	ResponseEntity<CoreResponseHandler> organisationmsisdns(String acctId);
	ResponseEntity<CoreResponseHandler> organisationemployees(String acctId);
	ResponseEntity<CoreResponseHandler> checkemployeemsisdnstatus(String msisdn);
	ResponseEntity<CoreResponseHandler> readwritecsv();
	
	
	
	
	
	
}
