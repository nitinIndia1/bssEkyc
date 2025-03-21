package com.ekyc.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import com.ekyc.beans.AgentActivation_;
import com.ekyc.beans.AgentLogin;
import com.ekyc.beans.Agent_;
import com.ekyc.beans.DocumentUpdateRequest;
import com.ekyc.beans.ExtraInfo;
import com.ekyc.beans.FetchResponse;
import com.ekyc.beans.IdRequest;
import com.ekyc.beans.IdRequest2;
import com.ekyc.beans.IdRequest3;
import com.ekyc.beans.MsisdnUpdate;
import com.ekyc.beans.OcrAddress_;
import com.ekyc.beans.OcrId;
import com.ekyc.beans.SimCase;
import com.ekyc.beans.SpecialMsisdns_;
import com.ekyc.beans.StatusUpdate;
import com.ekyc.beans.VerifiedMsisdn_;
import com.ekyc.beans.VerifyMsisdnSpecial;
import com.ekyc.model.Agent;
import com.ekyc.model.SimLostDamageDetail;
import com.ekyc.utils.CoreResponseHandler;

public interface EkycService {

	
	ResponseEntity<CoreResponseHandler> callFirstOCR(IdRequest idRequest,String self);
	
	ResponseEntity<CoreResponseHandler> updateCustomerWithSelfie(OcrId ocrId,String type);
	
	
	
	

	ResponseEntity<CoreResponseHandler> callSecondOCR(IdRequest2 idRequest);
	ResponseEntity<CoreResponseHandler> updateAddress(OcrAddress_ ocrAddress);
	
	

	ResponseEntity<CoreResponseHandler> removeall();
	
	
	List<FetchResponse> fetchAll2(String applicationDate);
	
	
	ResponseEntity<CoreResponseHandler> savePermit(IdRequest3 idRequest,String value);
	
	
	ResponseEntity<CoreResponseHandler> saveVisa(IdRequest3 idRequest);
	ResponseEntity<CoreResponseHandler> saveWater(IdRequest3 idRequest);
	ResponseEntity<CoreResponseHandler> saveConsent(IdRequest3 idRequest);
	ResponseEntity<CoreResponseHandler> saveTelecom(IdRequest3 idRequest);
	ResponseEntity<CoreResponseHandler> updateDob(String token,String dob);
	
	
	
	ResponseEntity<CoreResponseHandler> saveUID(IdRequest3 idRequest);
	
	
	ResponseEntity<CoreResponseHandler> documentUpdate(DocumentUpdateRequest documentUpdateRequest);
	
	ResponseEntity<CoreResponseHandler> msisdnUpdate(MsisdnUpdate msisdnUpdate);
	ResponseEntity<CoreResponseHandler> statusUpdate(StatusUpdate statusUpdate);
	
	ResponseEntity<CoreResponseHandler> saveAgentActivation(AgentActivation_ agentActivation_);	
	ResponseEntity<CoreResponseHandler> specialMsisdnSave(SpecialMsisdns_ specialMsisdns_);	
	ResponseEntity<CoreResponseHandler> specialMsisdnVerify(VerifyMsisdnSpecial verifyMsisdnSpecial);	
	
	ResponseEntity<CoreResponseHandler> checkAgent(String msisdn);
	ResponseEntity<CoreResponseHandler> agentGet(String msisdn);	

	ResponseEntity<CoreResponseHandler> agentLogin(AgentLogin agentLogin);

	ResponseEntity<CoreResponseHandler> agentDealerGet(String type);	
	ResponseEntity<CoreResponseHandler> saveAgent(Agent_ agent);
	
	ResponseEntity<CoreResponseHandler> savePermitValue(String token,String permitValue);
	
	ResponseEntity<CoreResponseHandler> nicVerify(JSONObject obj);
	ResponseEntity<CoreResponseHandler> passportVerify(JSONObject obj);
	
	
	ResponseEntity<CoreResponseHandler> agentCustom();
	
	ResponseEntity<CoreResponseHandler> test();

	ResponseEntity<CoreResponseHandler> updateVerified(String token);
	
	ResponseEntity<CoreResponseHandler> checkbymsisdnnewcustomer(String msisdn,String newcustomer,String type);
	ResponseEntity<CoreResponseHandler> checkSimMsisdn(String msisdn);
	
	ResponseEntity<CoreResponseHandler> checkKyc(String msisdn,String flag);
	ResponseEntity<CoreResponseHandler> processbase64s(String docId);
	
	ResponseEntity<CoreResponseHandler> simLostDamageCase(SimCase simCase);
	
	ResponseEntity<CoreResponseHandler>  findByMsisdn(String msisdn);
	
	ResponseEntity<CoreResponseHandler>  findByToken(String token);
	
	ResponseEntity<CoreResponseHandler>  findByCase_(String case_);
	
	
	ResponseEntity<CoreResponseHandler>  findByDateRange(String from,String to);
	
	ResponseEntity<CoreResponseHandler>  checkVerifiedMsisdn(String msisdn);
	
	ResponseEntity<CoreResponseHandler>  findByDocId(String id);
	
	
	ResponseEntity<CoreResponseHandler>  ekycActivate(String token,ExtraInfo extraInfo);
	
	ResponseEntity<CoreResponseHandler>  collectCrmFields(String token,ExtraInfo extraInfo);
	
	ResponseEntity<CoreResponseHandler> saveThumb(IdRequest3 idRequest);
	ResponseEntity<CoreResponseHandler> saveSelfie(IdRequest3 idRequest);
	
	ResponseEntity<CoreResponseHandler> agentBulkSave(List<Agent> lsAgents);
	
	ResponseEntity<CoreResponseHandler> globalsearch(String value);
	ResponseEntity<CoreResponseHandler> fetchThumb(String token);
	ResponseEntity<CoreResponseHandler> findByDateRangeEkycCounts(String from,String to);
	ResponseEntity<CoreResponseHandler> findByTotalEkycCounts();
	ResponseEntity<CoreResponseHandler> findAllDocuments(String token);
	
}
