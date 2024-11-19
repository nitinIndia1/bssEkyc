package com.ekyc.controller;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

//import jakarta.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ekyc.beans.AdminLogin;
import com.ekyc.beans.AdminRegister;
import com.ekyc.beans.AdminSave;
import com.ekyc.beans.AgentActivation_;
import com.ekyc.beans.AgentLogin;
import com.ekyc.beans.Agent_;
import com.ekyc.beans.DocumentUpdateRequest;
import com.ekyc.beans.EmployeeOrganisationMsisdn_;
import com.ekyc.beans.EmployeeSaveDetail;
import com.ekyc.beans.ExtraInfo;
import com.ekyc.beans.FetchResponse;
import com.ekyc.beans.IdRequest;
import com.ekyc.beans.IdRequest2;
import com.ekyc.beans.IdRequest3;
import com.ekyc.beans.MsisdnUpdate;
import com.ekyc.beans.OcrAddress_;
import com.ekyc.beans.OcrId;
import com.ekyc.beans.OrgMsisdn_;
import com.ekyc.beans.OrganisationSaveDetail;
import com.ekyc.beans.SimCase;
import com.ekyc.beans.SpecialMsisdns_;
import com.ekyc.beans.StatusUpdate;
import com.ekyc.beans.VerifyMsisdnSpecial;
import com.ekyc.model.CustomerDetail;
import com.ekyc.model.OrgMsisdn;
import com.ekyc.model.Organisation;
import com.ekyc.model.OtpDetail;
import com.ekyc.repository.OtpDetailRepository;
import com.ekyc.service.CustomerDetailService;
import com.ekyc.service.DocumentService;
import com.ekyc.service.EkycService;
import com.ekyc.service.EmployeeService;
import com.ekyc.service.ImageService;
import com.ekyc.service.OrganisationService;
import com.ekyc.service.SimDetailService;
import com.ekyc.utils.ApplicationResponse;
import com.ekyc.utils.CommonUtilityFunctions;
import com.ekyc.utils.CoreResponseHandler;
import com.ekyc.utils.ResponseStatusEnum;
import com.ekyc.utils.SuccessResponseBeanRefined;
import com.opencsv.CSVReader;



@RestController
@RequestMapping("/api")
public class MainController {

	
	@Autowired
	private CustomerDetailService customerService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private SimDetailService simDetailService;
	@Autowired
	private EkycService ekycService;
	@Autowired
	private CommonUtilityFunctions utility;
	

	

	
	
	/*
	 *  * @GetMapping(value="updateVerified/{token}") public
	 * ResponseEntity<CoreResponseHandler>
	 * updateVerified(@PathVariable(value="token",required = true)String token){
	 * 
	 * return ekycService.updateVerified(token);
	 * 
	 * 
	 * }
	 */
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="processID")
	public ResponseEntity<CoreResponseHandler> processid(@Valid @ModelAttribute final IdRequest idRequest,@RequestParam(name = "self",required = false)String self){
		
		return ekycService.callFirstOCR(idRequest,self);
		

	}
	
	
	
	/*
	 * @PostMapping(value="nic/verify") public ResponseEntity<CoreResponseHandler>
	 * nicVerify(@RequestBody JSONObject obj){ return ekycService.nicVerify(obj);
	 * 
	 * }
	 * 
	 * @PostMapping(value="passport/verify") public
	 * ResponseEntity<CoreResponseHandler> nicPassport(@RequestBody JSONObject obj){
	 * 
	 * return ekycService.passportVerify(obj);
	 * 
	 * 
	 * }
	 */
	

	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 

	@PutMapping(value="processID/{type}")
	public ResponseEntity<CoreResponseHandler> process_put_id(@Valid @ModelAttribute final OcrId ocrId, @PathVariable(name = "type",required = true)String type){
		
		return ekycService.updateCustomerWithSelfie(ocrId,type);
		

	}
	
		/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
		@GetMapping(value="update/dob/{token}/{dob}")
		public ResponseEntity<CoreResponseHandler> updateDob(@PathVariable(value="token",required = true)String token,@PathVariable(value="dob",required = true)String dob){
			return ekycService.updateDob(token, dob);

		}	

	
	
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@GetMapping(value="truncateForTesting")
	public ResponseEntity<CoreResponseHandler> truncateForTesting(){
		
		return ekycService.removeall();
		

	}
	
	
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 

	@PostMapping(value="processPermit/{value}")
	public ResponseEntity<CoreResponseHandler> processpermit(@Valid @ModelAttribute final IdRequest3 idRequest,@PathVariable(name = "value",required = true)String value){
		
		return ekycService.savePermit(idRequest,value);
		

	}
	

	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 

	@PostMapping(value="processVisa")
	public ResponseEntity<CoreResponseHandler> processvisa(@Valid @ModelAttribute final IdRequest3 idRequest){
		
		return ekycService.saveVisa(idRequest);
		

	}

	
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	
	@GetMapping(value="process/base64/{docId}")
	public ResponseEntity<CoreResponseHandler> processbase64s(@PathVariable(value="docId",required = true)String docId){
		
		return ekycService.processbase64s(docId);
		

	}
	
	
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	
	@PostMapping(value="processWater")
	public ResponseEntity<CoreResponseHandler> processwater(@Valid @ModelAttribute final IdRequest3 idRequest){
		
		return ekycService.saveWater(idRequest);
		

	}

	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="processTelecom")
	public ResponseEntity<CoreResponseHandler> processtelecom(@Valid @ModelAttribute final IdRequest3 idRequest){
		
		return ekycService.saveTelecom(idRequest);
		

	}

	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="processConsent")
	public ResponseEntity<CoreResponseHandler> processconsent(@Valid @ModelAttribute final IdRequest3 idRequest){
		
		return ekycService.saveConsent(idRequest);
		

	}
	
	 
	 
	 
	

	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="processUID")
	public ResponseEntity<CoreResponseHandler> processUID(@Valid @ModelAttribute final IdRequest3 idRequest){
		
		return ekycService.saveUID(idRequest);
		

	}
	

	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="documentUpdate")
	public ResponseEntity<CoreResponseHandler> documentUpdate(@Valid @ModelAttribute final DocumentUpdateRequest documentUpdateRequest){
		
		return ekycService.documentUpdate(documentUpdateRequest);

	}
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="msisdnUpdate")
	public ResponseEntity<CoreResponseHandler> msisdnUpdate(@Valid @ModelAttribute final MsisdnUpdate msisdnUpdate){
		
		return ekycService.msisdnUpdate(msisdnUpdate);

	}
	
	
		
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="statusUpdate")
	public ResponseEntity<CoreResponseHandler> statusUpdate(@Valid @ModelAttribute final StatusUpdate statusUpdate){
		
		return ekycService.statusUpdate(statusUpdate);

	}

	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="agentActivation")
	public ResponseEntity<CoreResponseHandler> agentActivation(@Valid @RequestBody final AgentActivation_ agentActivation_){
		
		return ekycService.saveAgentActivation(agentActivation_);

	}
	

	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="agentLogin")
	public ResponseEntity<CoreResponseHandler> agentLogin(@Valid @RequestBody final AgentLogin agentLogin){
		
		return ekycService.agentLogin(agentLogin);

	}

	
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@GetMapping(value="agentCheck/{msisdn}")
	public ResponseEntity<CoreResponseHandler> agentCheck(@PathVariable(value="msisdn",required = true)String msisdn){
		
		return ekycService.checkAgent(msisdn);

	}

	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@GetMapping(value="agentGet/{msisdn}")
	public ResponseEntity<CoreResponseHandler> agentGet(@PathVariable(value="msisdn",required = true)String msisdn){
		
		return ekycService.agentGet(msisdn);

	}		


	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@GetMapping(value="permitValue/{token}/{permitValue}")
	public ResponseEntity<CoreResponseHandler> permitValueSave(@PathVariable(value="token",required = true)String token,@PathVariable(value="permitValue",required = true)String permitValue){
		
		return ekycService.savePermitValue(token, permitValue);
	}	
	
	
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@GetMapping(value="agentDealerGet/{type}")
	public ResponseEntity<CoreResponseHandler> agentDealerGet(@PathVariable(value="type",required = true)String type){
		
		return ekycService.agentDealerGet(type);

	}
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@GetMapping(value="agentCustom")
	public ResponseEntity<CoreResponseHandler> agentCustom(){
		
		return ekycService.agentCustom();

	}
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="saveAgent")
	public ResponseEntity<CoreResponseHandler> saveAgent(@Valid @RequestBody final Agent_ agent_){
		
		return ekycService.saveAgent(agent_);

	}
	
	
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="specialMsisdnPost")
	public ResponseEntity<CoreResponseHandler> specialMsisdn(@Valid @RequestBody final SpecialMsisdns_ specialMsisdns_){
		
		return ekycService.specialMsisdnSave(specialMsisdns_);

	}

	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="specialMsisdnVerify")
	public ResponseEntity<CoreResponseHandler> specialMsisdnVerify(@Valid @RequestBody final VerifyMsisdnSpecial verifyMsisdnSpecial){
		
		return ekycService.specialMsisdnVerify(verifyMsisdnSpecial);

	}
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PostMapping(value="processAddress")
	public ResponseEntity<CoreResponseHandler> processaddress(@Valid @ModelAttribute final IdRequest2 idRequest){
		
		return ekycService.callSecondOCR(idRequest);
		

	}

	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@PutMapping(value="processAddress")
	public ResponseEntity<CoreResponseHandler> process_put_address(@Valid @ModelAttribute final OcrAddress_ address){
		
		return ekycService.updateAddress(address);
		

	}

	
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@GetMapping(value="status/token/{token}")
	public ResponseEntity<CoreResponseHandler> getStatus(@PathVariable(value="token",required = true)String token){
		long l_time_start = System.currentTimeMillis();
		CustomerDetail customerDetail = customerService.findByToken(token);
		if(customerDetail==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no customer in records with this id",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,customerDetail.getKycStatus(),l_diff+" ms") ,HttpStatus.OK);															
		

	}

	
	@GetMapping(value="status/msisdn/{msisdn}")
	public ResponseEntity<CoreResponseHandler> getStatus2(@PathVariable(value="msisdn",required = true)String msisdn){
		long l_time_start = System.currentTimeMillis();
		CustomerDetail customerDetail = customerService.findByMsisdn(msisdn);
		if(customerDetail==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no customer in records with this msisdn",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,customerDetail,l_diff+" ms") ,HttpStatus.OK);
		

	}
	

	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@GetMapping(value="fetchByDate")
	public ResponseEntity<CoreResponseHandler> fetch(@RequestParam(name = "applicationDate", required = true) String applicationDate){
		long l_time_start = System.currentTimeMillis();
		if(!utility.checkDate(applicationDate, new SimpleDateFormat("dd-MM-yyyy"))) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"application date format is dd-MM-yyyy",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												

		}

		String newApplicationDate = utility.convertToDifferentSdf(applicationDate, new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("yyyy-MM-dd"));

		if(!utility.checkDate(newApplicationDate, new SimpleDateFormat("yyyy-MM-dd"))) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"some internal date error",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		List<FetchResponse> list = ekycService.fetchAll2(newApplicationDate);
		
		if(list==null || list.size()<=0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no such customer in records found",l_diff+" ms"),HttpStatus.NOT_FOUND);			
		}
		else if(list!=null && list.size()>0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,list,l_diff+" ms") ,HttpStatus.OK);															

		}
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	
	
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 

	@GetMapping(value="check/msisdn/newcustomer/{msisdn}/{newcustomer}/{type}")
	public ResponseEntity<CoreResponseHandler> checkbymsisdnnewcustomer(@PathVariable(value="msisdn",required = true)String msisdn,@PathVariable(value="newcustomer",required = true)String newcustomer,@PathVariable(value="type",required = true)String type){
		return ekycService.checkbymsisdnnewcustomer(msisdn, newcustomer, type);

	}


	
//	@GetMapping(value="check/kyc/msisdn/{msisdn}")
//	public ResponseEntity<CoreResponseHandler> kyccheck(@PathVariable(value="msisdn",required = true)String msisdn,@PathVariable(value="flag",required = true)String flag){
//		return ekycService.checkbymsisdnnewcustomer(msisdn, newcustomer, type);
//
//	}

	
	
	
	//==============================================================================================================
	
 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="check/brn/{brn}")
	public ResponseEntity<CoreResponseHandler> checkBrn(@PathVariable(value="brn",required = true)String brn){
		return orgService.checkBrn(brn);

	}

	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
	@GetMapping(value="check/certNumber/{cert}")
	public ResponseEntity<CoreResponseHandler> checkCertNumber(@PathVariable(value="cert",required = true)String cert){
		return orgService.checkCert(cert);

	}	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="check/expiry/{date}")
	public ResponseEntity<CoreResponseHandler> checkexpirydate(@PathVariable(value="date",required = true)String date){
		return orgService.checkExpiryDate(date);

	}	
		
	

	@Autowired
	private OrganisationService orgService;
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="processOrganisation")
	public ResponseEntity<CoreResponseHandler> processOrganisation(@Valid @ModelAttribute final OrganisationSaveDetail organisationSaveDetail){
		
		return orgService.setUpOrganisation(organisationSaveDetail);
		

	}
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="check/acctid/{acctid}")
	public ResponseEntity<CoreResponseHandler> checkacctid(@PathVariable(value="acctid",required = true)String acctid){
		
		return orgService.checkAcctid(acctid);
		

	}
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="processOrganisationDocument")
	public ResponseEntity<CoreResponseHandler> processOrganisationDocument(@Valid @ModelAttribute final IdRequest2 idRequest){
		
		//return orgService.callSecondOCR(idRequest);
		return null;

	}
	

	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="getOrganisation/{msisdn}")
	public ResponseEntity<CoreResponseHandler> getOrganisation(@PathVariable(value="msisdn",required = true)String msisdn){
		
		return orgService.getOrganisation(msisdn);
		

	}


	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="saveAdmin/{orgToken}/{orgPin}")
	public ResponseEntity<CoreResponseHandler> saveAdmin(@Valid @ModelAttribute final AdminSave adminSave,@PathVariable(value="orgToken",required = true)String orgToken,
			@PathVariable(value="orgPin",required = true)String orgPin){
		
		//return orgService.callSecondOCR(idRequest);
		return orgService.adminSave(orgToken, orgPin, adminSave);

	}
	
	
	
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="organisation/{organisationType}/{date}")
	public ResponseEntity<CoreResponseHandler> getOrganisation123(@PathVariable(value="organisationType",required = true)String organisationType,
			@PathVariable(value="date",required = true)String date){
		
		return orgService.organisationTypeDate(organisationType, date);
		

	}
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="organisation/list/{organisationType}")
	public ResponseEntity<CoreResponseHandler> getOrganisationtypelist(@PathVariable(value="organisationType",required = true)String organisationType){
		
		return orgService.organisationType(organisationType);
		

	}
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="organisation/verifyAdmin/{userName}/{password}")
	public ResponseEntity<CoreResponseHandler> getOrganisationtypeadminemployeelist(@PathVariable(value="userName",required = true)String userName,
			@PathVariable(value="password",required = true)String password){
		
		return orgService.getEmployeeByAdmin(userName, password);
		

	}
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="updateOrganisationDocument/{type}")
	public ResponseEntity<CoreResponseHandler> updateOrganisationDocument(@Valid @ModelAttribute final DocumentUpdateRequest documentUpdateRequest,
			@PathVariable(value="type",required = true)String type
			){
		
		return orgService.updateOrganisationDocument(documentUpdateRequest,type);
		

	}
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="organisationGet/{orgToken}/{orgPin}")
	public ResponseEntity<CoreResponseHandler> organisationGet(@PathVariable(value="orgToken",required = true)String orgToken,@PathVariable(value="orgPin",required = true)String orgPin){
		return orgService.organisationGet(orgToken, orgPin);

	}
		 
	@Autowired
	private EmployeeService empService;
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="processID_/{orgToken}/{orgPin}")//tourist
	public ResponseEntity<CoreResponseHandler> processidemployee(@Valid @ModelAttribute final IdRequest idRequest,@PathVariable(value="orgToken",required = true)String orgToken,@PathVariable(value="orgPin",required = true)String orgPin){
		
		return empService.callFirstOcr(orgToken, orgPin, idRequest);
		

	}
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PutMapping(value="processID__")
	public ResponseEntity<CoreResponseHandler> process_put_idEmployee(@Valid @ModelAttribute final OcrId ocrId){
		
		return empService.updateEmployeeWithSelfie(ocrId);
		

	}
	
	
	//address = token, type, address
	//type=  employee,admin
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="address/{type}/{token}")
	public ResponseEntity<CoreResponseHandler> address(@PathVariable(value="type",required = true)String type,
			@PathVariable(value="token",required = true)String token,
			@RequestParam(value = "address", required = true)String address,@RequestParam(value = "email", required = false)String email,
			@RequestParam(value = "alternateNumber", required = false)String alternateNumber){
		return empService.address(type, token, address,email,alternateNumber);

	}
	
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="nic/verify_")
	public ResponseEntity<CoreResponseHandler> nicVerifyEmployee(@RequestBody JSONObject obj){
		
		return empService.nicVerify(obj);
		

	}
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="passport/verify_")
	public ResponseEntity<CoreResponseHandler> nicPassportEmployee(@RequestBody JSONObject obj){
		
		return empService.passportVerify(obj);
		

	}	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="empUtility")
	public ResponseEntity<CoreResponseHandler> emputility(@Valid @ModelAttribute final IdRequest3 idRequest){
		
		return empService.emputility(idRequest);
		

	}
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="empAuthorizationLetter")
	public ResponseEntity<CoreResponseHandler> empauthorizationletter(@Valid @ModelAttribute final IdRequest3 idRequest){
		
		return empService.empauthorizationletter(idRequest);
		

	}	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="msisdnUpdate_")
	public ResponseEntity<CoreResponseHandler> msisdnUpdate_(@Valid @ModelAttribute final MsisdnUpdate msisdnUpdate){
		
		return empService.msisdnUpdate(msisdnUpdate);

	}
	


	
	
	//	@GetMapping(value="fetchAll")
//	public 	List<FetchResponse> fetch(){
//		List<FetchResponse> obj =  ekycService.fetchAll2();
//		return obj;
//
//	}
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="employee/organisation/msisdn/save")
	public ResponseEntity<CoreResponseHandler> employeeorganisationmsisdnsave(@Valid @ModelAttribute final EmployeeOrganisationMsisdn_ employeeOrganisationMsisdn_){
		
		return empService.employeeorganisationmsisdnsave(employeeOrganisationMsisdn_);
	}
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="employee/organisation/msisdn/status/update/{empToken}/{orgToken}/{msisdn}/{status}")
	public ResponseEntity<CoreResponseHandler> employeeorganisationmsisdnupdate(
			@PathVariable(value="empToken",required = true)String empToken,
			@PathVariable(value="orgToken",required = true)String orgToken,
			@PathVariable(value="msisdn",required = true)String msisdn,
			@PathVariable(value="status",required = true)String status){
		
		return empService.employeeorganisationmsisdnupdate(empToken,orgToken,msisdn,status);
	}
	
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="adminActivation")
	public ResponseEntity<CoreResponseHandler> adminActivation(@Valid @RequestBody final AgentActivation_ agentActivation_){
		
		return orgService.saveAdminActivation(agentActivation_);
		
		
		

	}

	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="adminGet/{msisdn}")
	public ResponseEntity<CoreResponseHandler> adminGet(@PathVariable(value="msisdn",required = true)String msisdn){
		
		return orgService.adminGet(msisdn);
		
		//return ekycService.agentGet(msisdn);

	}		

	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="check/sim/msisdn/{msisdn}")
	public ResponseEntity<CoreResponseHandler> checkSimMsisdn(@PathVariable(value="msisdn",required = true)String msisdn){
		
		
		return ekycService.checkSimMsisdn(msisdn);

	}		
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="set/organisation/admin")
	public ResponseEntity<CoreResponseHandler> setOrganisationAdmin(@Valid @ModelAttribute AdminRegister adminRegister){
		
		return orgService.setOrganisationAdmin(adminRegister);

	}
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="update/organisation/admin/{token_find}/{token_to}")
	public ResponseEntity<CoreResponseHandler> updateOrganisationAdmin(@PathVariable(value="token_find",required = true)String token_find,@PathVariable(value="token_to",required = true)String token_to){
		
		
		return orgService.updateOrganisationAdmin(token_find, token_to);

	}		
	
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="organisation/admin/employee/{token}")
	public ResponseEntity<CoreResponseHandler> moveAdminToEmployee(@PathVariable(value="token",required = true)String token){
		
		
		return orgService.moveAdminToEmployee(token);

	}		
	
		

	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="organisation/admin/employee/login")
	public ResponseEntity<CoreResponseHandler> organisationadminemployeelogin(@Valid @ModelAttribute AdminLogin adminLogin){
		
		
		return orgService.organisationadminemployeelogin(adminLogin);

	}		
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="organisation/list/msisdns")
	public ResponseEntity<CoreResponseHandler> organisationmsisdns(@Valid @RequestBody OrgMsisdn_ orgMsisdn_){
		
		
		return orgService.allocateMsisdnsToOrganisation(orgMsisdn_);

	}		
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="organisation/msisdns/{acctId}")
	public ResponseEntity<CoreResponseHandler> organisationmsisdns(@PathVariable(value="acctId",required = true)String acctId){
		return orgService.organisationmsisdns(acctId);

	}		
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="organisation/employees/{acctId}")
	public ResponseEntity<CoreResponseHandler> organisationemployees(@PathVariable(value="acctId",required = true)String acctId){
		return orgService.organisationemployees(acctId);

	}		
		

	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="check/employee/msisdn/status/{msisdn}")
	public ResponseEntity<CoreResponseHandler> checkemployeemsisdnstatus(@PathVariable(value="msisdn",required = true)String msisdn){
		return orgService.checkemployeemsisdnstatus(msisdn);

	}
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="sim/lost/damage/detail")
	public ResponseEntity<CoreResponseHandler> simLostDamageDetail(@Valid @ModelAttribute final SimCase simCase){
		
		return ekycService.simLostDamageCase(simCase);
		

	}
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="sim/lost/damage/msisdn/{msisdn}")
	public ResponseEntity<CoreResponseHandler> checksimlostmsisdn(@PathVariable(value="msisdn",required = true)String msisdn){
		return ekycService.findByMsisdn(msisdn);

	}
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="sim/lost/damage/token/{token}")
	public ResponseEntity<CoreResponseHandler> checksimlosttoken(@PathVariable(value="token",required = true)String token){
		return ekycService.findByToken(token);

	}
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="sim/lost/damage/case/{case_}")
	public ResponseEntity<CoreResponseHandler> checksimlostcase(@PathVariable(value="case_",required = true)String case_){
		return ekycService.findByCase_(case_);

	}
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="sim/lost/damage/dateRange")
	public ResponseEntity<CoreResponseHandler> checksimlostdateRange(@RequestParam(value="from",required = true)String from,@RequestParam(value="to",required = true)String to){
		return ekycService.findByDateRange(from, to);

	}
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="check/verified/msisdn/{msisdn}")
	public ResponseEntity<CoreResponseHandler> checkVerifiedMsisdn(@PathVariable(value="msisdn",required = true)String msisdn){
		return ekycService.checkVerifiedMsisdn(msisdn);

	}	

	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value="find/by/id/{id}")
	public ResponseEntity<CoreResponseHandler> findById(@PathVariable(value="id",required = true)String id){
		return ekycService.findByDocId(id);

	}	
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@GetMapping(value = "csv/writer")
	public ResponseEntity<CoreResponseHandler> csvWriter(){
		
		return orgService.readwritecsv();
		
	}
	
	

	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="ekyc/collect/crm/fields/{token}")
	public ResponseEntity<CoreResponseHandler> collectCrmFields(@PathVariable(value="token",required = true)String token,@RequestBody ExtraInfo extraInfo){
		return ekycService.collectCrmFields(token, extraInfo);

	}	
	
	

	
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	@PostMapping(value="ekyc/activate/{token}")
	public ResponseEntity<CoreResponseHandler> ekycActivate(@PathVariable(value="token",required = true)String token,@RequestBody ExtraInfo extraInfo){
		return ekycService.ekycActivate(token,extraInfo);

	}	
	
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
		@PostMapping(value="upload/thumbImage")
		public ResponseEntity<CoreResponseHandler> processthumbimage(@Valid @ModelAttribute final IdRequest3 idRequest){
			
			return ekycService.saveThumb(idRequest);
			

		}
	
	
	 /*@CrossOrigin(origins = "*", allowedHeaders = "*")*/ 
		@PostMapping(value="upload/selfie")
		public ResponseEntity<CoreResponseHandler> processselfieimage(@Valid @ModelAttribute final IdRequest3 idRequest){
			
			return ekycService.saveSelfie(idRequest);
			

		}		
		
		
//		@GetMapping(value = "check/sample1")
//		public ResponseEntity<CoreResponseHandler> checkSample1(){
//			HttpHeaders responseHeaders = new HttpHeaders();
//			responseHeaders.add("Connection", "keep-alive");
//			
//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"ok",null) ,responseHeaders, HttpStatus.OK);												
//			
//			
//			
//		}	
//		
//		
//
//		@GetMapping(value = "check/sample2")
//		public ResponseEntity<CoreResponseHandler> checkSample2(){
//			
//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"ok",null) , HttpStatus.OK);												
//			
//		}		
		
		@GetMapping(value="customerbymsisdn/{msisdn}")
		public ResponseEntity<?> api_customerbymsisdn(@PathVariable(value="msisdn")String msisdn){

			String accessToken = getCrmAccessToken();
			//System.out.println("ACCESS TOKEN : "+accessToken);

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", "Bearer "+accessToken);
			ResponseEntity<String> response = null;
			HttpEntity formEntity = new HttpEntity(null, headers);
			try {
				response = restTemplate.exchange("http://172.17.1.20:9090/api/customerbymsisdn/"+msisdn, HttpMethod.GET,
						formEntity, String.class);

				if(response!=null && response.getStatusCode().is2xxSuccessful()) {
					String actualResponse = response.getBody();
					JSONParser parser =new JSONParser();
					JSONObject obj=null;
					try {
						obj = (JSONObject) parser.parse(actualResponse);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return new ResponseEntity<>(obj, HttpStatus.OK);
				}
				else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
					System.out.println("1");
					return new ResponseEntity<>(response, response.getStatusCode());
				}
				else
				{
					System.out.println("2");
					return new ResponseEntity<>("ERROR", response.getStatusCode());

				}
			}
			//			catch(HttpClientErrorException ex) {
			//			ex.printStackTrace();
			//			String lMsg = ex.getLocalizedMessage();
			//			//String msg = ex.getMessage();
			////			JSONParser parser =new JSONParser();
			////			JSONObject obj = null;
			////			try {
			////			obj = (JSONObject)parser.parse(lMsg);
			////		}catch(Exception ex_) {
			////			ex_.printStackTrace();
			////		}
			//			System.out.println("exception check check check check check ");
			//			
			//			long l_end_time = System.currentTimeMillis();
			//			long l_diff = l_end_time-l_time_start;
			//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
			//
			//		}


			catch(HttpClientErrorException ex) {
				System.out.println("herehherehehrhehehrehrherhe");
				ex.printStackTrace();
				String msg = ex.getResponseBodyAsString();

				JSONParser parser =new JSONParser();
				JSONObject obj = null;
				try {
					obj = (JSONObject)parser.parse(msg);

				}catch(Exception ex_) {
					ex_.printStackTrace();
					return new ResponseEntity<>(obj , HttpStatus.INTERNAL_SERVER_ERROR);

				}
				return new ResponseEntity<>(obj , ex.getStatusCode());

			}
		}
		String getCrmAccessToken() {
			//String CRM_LoginUrl="http://172.5.10.2:9090/api/login";
			//http://172.17.1.20:9090/
			String CRM_LoginUrl="http://172.17.1.20:9090/api/login";
			String jwtToken = null;
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);

			String credential = "{\"email\":\"pawan@gmail.com\",\"password\":\"pawan123\"}";

			//System.out.println("@@@@@ "+ credential);

			HttpEntity<String> requestEntity = new HttpEntity<>(credential, header);

			//System.out.println("!!!!!requestEntity "+ requestEntity);
			ResponseEntity<String> responseEntity = restTemplate.exchange(CRM_LoginUrl, HttpMethod.POST, requestEntity,String.class);

			//System.out.println("$$$$$$$$ responseEntity" +responseEntity);
			//HttpStatusCode statusCode = responseEntity.getStatusCode();
			HttpStatus statusCode2 = responseEntity.getStatusCode();

			//System.out.println("CRM login Api Status Code1: " + statusCode);
			if (statusCode2 == HttpStatus.OK) {

				String response = responseEntity.getBody();
				// System.out.println(response);

				org.json.JSONObject jsonResponse = new org.json.JSONObject(response);


				jwtToken = jsonResponse.getString("jwtToken");

				//System.out.println("XXXXXX"+jwtToken);

			}
			return jwtToken;
		}
}
