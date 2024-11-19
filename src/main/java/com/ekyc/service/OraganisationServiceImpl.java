package com.ekyc.service;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ekyc.beans.AdminActivationData_;
import com.ekyc.beans.AdminLogin;
import com.ekyc.beans.AdminRegister;
import com.ekyc.beans.AdminSave;
import com.ekyc.beans.AgentActivationData_;
import com.ekyc.beans.AgentActivation_;
import com.ekyc.beans.DocumentDetail;
import com.ekyc.beans.DocumentUpdateRequest;
import com.ekyc.beans.Document_;
import com.ekyc.beans.IdRequest2;
import com.ekyc.beans.OrgMsisdn_;
import com.ekyc.beans.OrganisationSaveDetail;
import com.ekyc.beans.Test_;
import com.ekyc.model.AdminActivation;
import com.ekyc.model.Agent;
import com.ekyc.model.AgentActivation;
import com.ekyc.model.CustomerDetail;
import com.ekyc.model.Document;
import com.ekyc.model.Employee;
import com.ekyc.model.EmployeeOrganisationMsisdn;
import com.ekyc.model.OrgMsisdn;
import com.ekyc.model.Organisation;
import com.ekyc.model.OtpDetail;
import com.ekyc.model.SimDetail;
import com.ekyc.repository.AdminActivationRepository;
import com.ekyc.repository.AgentRepository;
import com.ekyc.repository.CustomerDetailRepository;
import com.ekyc.repository.EmployeeOrganisationMsisdnRepository;
import com.ekyc.repository.EmployeeRepository;
import com.ekyc.repository.OrgMsisdnRepository;
import com.ekyc.repository.OrganisationRepository;
import com.ekyc.repository.OtpDetailRepository;
import com.ekyc.utils.ApplicationResponse;
import com.ekyc.utils.CommonUtilityFunctions;
import com.ekyc.utils.CoreResponseHandler;
import com.ekyc.utils.ResponseStatusEnum;
import com.ekyc.utils.SuccessResponseBeanRefined;
import com.opencsv.CSVReader;

@Service
public class OraganisationServiceImpl implements OrganisationService {

	@Autowired
	private OrganisationRepository repo;
	@Autowired
	private CustomerDetailService custService;
	@Autowired
	private CommonUtilityFunctions utility;
	@Autowired
	private DocumentService docService;
	@Autowired
	private EmployeeService empService;
	@Autowired
	private OrgMsisdnRepository orgMsisdnRepo;
	@Autowired
	 CustomerDetailRepository custRepo;
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private AdminActivationRepository adminActivationRepo;
	@Autowired
	private OtpDetailRepository otpRepo;
	@Autowired
	private EmployeeOrganisationMsisdnRepository employeeOrganisationMsisdnRepository;
	@Override
	public ResponseEntity<CoreResponseHandler> setUpOrganisation(OrganisationSaveDetail organisationSaveDetail) {
		// TODO Auto-generated method stub
		long l_time_start = System.currentTimeMillis();

		CustomerDetail admin  = null;
		if(organisationSaveDetail.getAdminToken()!=null)
		
		admin = custService.findByToken(organisationSaveDetail.getAdminToken());
//		if(admin==null || (!admin.getKycStatus().equalsIgnoreCase("verified") && !admin.getKycStatus().contains("contingency"))) {
//			long l_end_time = System.currentTimeMillis();
//			long l_diff = l_end_time-l_time_start;
//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"admin not found, or kyc status invalid",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
//		}
		
//		List<Organisation> lsAdmin = repo.findByAdmin(admin);
//		if(lsAdmin!=null && lsAdmin.size()>0) {
//			long l_end_time = System.currentTimeMillis();
//			long l_diff = l_end_time-l_time_start;
//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"duplicate admin not allowed",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
//
//		}
		
//		List<String> list = Arrays.asList("contingency not verified","verified");
//		if(!utility.allowedValues(admin.getKycStatus(), list)) {
//			long l_end_time = System.currentTimeMillis();
//			long l_diff = l_end_time-l_time_start;
//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"invalid status found for this admin",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
//
//		}

		//Organisation organisation =  repo.findByContact(organisationSaveDetail.getOrgContact());
		Organisation organisation =  repo.findByAcctId(organisationSaveDetail.getAcctId());
		boolean check = false;
		if(organisation==null) {
			check=true;
//			long l_end_time = System.currentTimeMillis();
//			long l_diff = l_end_time-l_time_start;
//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"active details already found with this admin",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
			organisation = new Organisation();
		}

		
//		    Document document0 = null;
//		    try {
//			Document_ d_ = new Document_();
//			d_.setBase64(organisationSaveDetail.getIncorporationLetter64());
//			d_.setImgType("incorporationLetter");
//			d_.setToken(organisationSaveDetail.getAdminToken());
//
//			
//			
//			document0 = docService.saveDocument(organisation.getDocument(), d_);		
//			organisation.setDocument(document0);
//			
//			Document_ d_2 = new Document_();
//			d_2.setBase64(organisationSaveDetail.getVat64());
//			d_2.setImgType("vat");
//			d_2.setToken(organisationSaveDetail.getAdminToken());
//
//			
//			
//			document0 = docService.saveDocument(organisation.getDocument(), d_2);		
//			organisation.setDocument(document0);
//			
//			
//			Document_ d_3 = new Document_();
//			d_3.setBase64(organisationSaveDetail.getBrn64());
//			d_3.setImgType("brn");
//			d_3.setToken(organisationSaveDetail.getAdminToken());
//
//			
//			
//			document0 = docService.saveDocument(organisation.getDocument(), d_3);		
//			organisation.setDocument(document0);
//			
//			Document_ d_4 = new Document_();
//			d_4.setBase64(organisationSaveDetail.getUtilityBill64());
//			d_4.setImgType("utility");
//			d_4.setToken(organisationSaveDetail.getAdminToken());
//
//			
//			
//			document0 = docService.saveDocument(organisation.getDocument(), d_4);		
//			organisation.setDocument(document0);
//		    }catch(Exception ex) {
//		    	ex.printStackTrace();
//		    }

		
		organisation.setActive("yes");
		organisation.setOrgType(organisationSaveDetail.getOrgType());
		organisation.setAddress(organisationSaveDetail.getOrganisationAddress());
		organisation.setAdmin(admin);
		organisation.setOrganisationName(organisationSaveDetail.getOrganisationName());
		organisation.setContact(organisationSaveDetail.getOrgContact());
		organisation.setEmail(organisationSaveDetail.getOrgEmail());
		if(check) {
		String orgToken = utility.getRandomNumber(9);
		String orgPin = utility.getRandomNumber(4);
		organisation.setOrgToken(orgToken);
		organisation.setOrgPin(orgPin);
//		Organisation org1 =null;
//		Organisation org2 =null;
//		
//		if(organisationSaveDetail.getBrn()!=null) 
//		org1 = repo.findByBrn(organisationSaveDetail.getBrn());
//		if(org1!=null) {
//			long l_end_time = System.currentTimeMillis();
//			long l_diff = l_end_time-l_time_start;
//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"duplicate brn not allowed",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
//
//		}
//		org2 = repo.findByTouristCertificateNumber(organisationSaveDetail.getCertificateNumber());
//		if(org2!=null) {
//			long l_end_time = System.currentTimeMillis();
//			long l_diff = l_end_time-l_time_start;
//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"duplicate certificate number not allowed",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
//			
//		}
//		
//		String expiryDate = organisationSaveDetail.getExpiryDate();
//		if(expiryDate!=null) {
//		try {
//			 if (new SimpleDateFormat("yyyy-MM-dd").parse(expiryDate).before(new Date())) {
//				 long l_end_time = System.currentTimeMillis();
//					long l_diff = l_end_time-l_time_start;
//					return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"expiry date not allowed",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
//					
//				}
//			 else {
//				 System.out.println("ok");
//			 }
//		 }catch(Exception ex) {
//			 ex.printStackTrace();
//		 }
//		}
		}
		organisation.setAcctId(organisationSaveDetail.getAcctId());
		organisation.setBrn(organisationSaveDetail.getBrn());
		organisation.setVat(organisationSaveDetail.getVat());
		organisation.setCreateDate(new Date());
		organisation.setUpdateDate(new Date());
		organisation.setTouristCertificateNumber(organisationSaveDetail.getCertificateNumber());
		organisation.setExpiryDate(organisationSaveDetail.getExpiryDate());
		//organisation.setStatus("verified");
		Organisation organisation_ = repo.saveAndFlush(organisation);
		
		if(organisation_!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,organisation_,l_diff+" ms") ,HttpStatus.OK);												
		
		}
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

	}
	@Override
	public ResponseEntity<CoreResponseHandler> updateOrganisationDocument(DocumentUpdateRequest documentUpdateRequest,String type) {
		// TODO Auto-generated method stub
		long l_time_start = System.currentTimeMillis();

		Employee employee=null;
		Organisation organisation=null;
		if(type.equalsIgnoreCase("employee") || type.equalsIgnoreCase("tourist")) {
			employee = empRepo.findByToken(documentUpdateRequest.getToken());
		}
		else {
			organisation =  repo.findByOrgToken(documentUpdateRequest.getToken());
		}
		
		
		
		if(organisation==null && employee==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no organization/employee registered with this token",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		if(employee!=null) {
		List<String> list = Arrays.asList("incorporationLetter","vat","brn","utility","touristCert","authorizationLetter","authorizationLetter_part2","authorizationLetter_part3");
		if(!utility.allowedValues(documentUpdateRequest.getDocumentName(), list)) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"invalid documentName.",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												

		}

		
		Document document = employee.getDocument();
		if(document==null) {
			document=new Document();
		}
		
		
		
		
			Document_ d_ = new Document_();
			d_.setBase64(documentUpdateRequest.getImage());
			d_.setImgType(documentUpdateRequest.getDocumentName());
			d_.setToken(documentUpdateRequest.getToken());

			
			
			document = docService.saveDocument(employee.getDocument(), d_);
			
			
			employee.setDocument(document);
			employee.setUpdateDate(new Date());
			Employee employee_ =  empRepo.saveAndFlush(employee);
		
		if(employee_!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"updated",l_diff+" ms") ,HttpStatus.OK);												
		
		}
		}
		else if(organisation!=null) {
			List<String> list = Arrays.asList("incorporationLetter","vat","brn","utility","touristCert","authorizationLetter","authorizationLetter_part2","authorizationLetter_part3");
			if(!utility.allowedValues(documentUpdateRequest.getDocumentName(), list)) {
				long l_end_time = System.currentTimeMillis();
				long l_diff = l_end_time-l_time_start;
				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"invalid documentName.",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												

			}

			
			Document document = organisation.getDocument();
			if(document==null) {
				document=new Document();
			}
			
			
			
			
				Document_ d_ = new Document_();
				d_.setBase64(documentUpdateRequest.getImage());
				d_.setImgType(documentUpdateRequest.getDocumentName());
				d_.setToken(documentUpdateRequest.getToken());

				
				
				document = docService.saveDocument(organisation.getDocument(), d_);		
				if(organisation.getOrgType().equalsIgnoreCase("tourOperator") && document.getDocument15Url()!=null) {
					organisation.setStatus("verified");
				}
				if((organisation.getOrgType().equalsIgnoreCase("tourism") || organisation.getOrgType().equalsIgnoreCase("corporate")) && document.getDocument13Url()!=null) {
					organisation.setStatus("verified");
				}
				organisation.setDocument(document);
				organisation.setUpdateDate(new Date());
				Organisation organisation_ =  repo.saveAndFlush(organisation);
			
			if(organisation_!=null) {
				long l_end_time = System.currentTimeMillis();
				long l_diff = l_end_time-l_time_start;
				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"updated",l_diff+" ms") ,HttpStatus.OK);												
			
			}
		}
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

	}
	@Override
	public ResponseEntity<CoreResponseHandler> organisationGet(String orgToken, String orgPin) {
		// TODO Auto-generated method stub
		long l_time_start = System.currentTimeMillis();



		Organisation organisation =  repo.findByOrgTokenAndOrgPin(orgToken, orgPin);
		if(organisation==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "invalid credentials",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		
		
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,organisation,l_diff+" ms") ,HttpStatus.OK);												
		
		
	}
	@Override
	public Organisation organisationGet_(String orgToken, String orgPin) {
		// TODO Auto-generated method stub
		Organisation organisation =  repo.findByOrgTokenAndOrgPin(orgToken, orgPin);
		if(organisation==null) {
		return null;

		}
		
		
		return organisation;
		
	}
	@Override
	public ResponseEntity<CoreResponseHandler> getOrganisation(String msisdn) {
		// TODO Auto-generated method stub
		long l_time_start = System.currentTimeMillis();
		Organisation organisation =  repo.findByAcctId(msisdn);
		if(organisation==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no organisation found",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,organisation,l_diff+" ms") ,HttpStatus.OK);												
	
	}
	@Override
	public ResponseEntity<CoreResponseHandler> processOrganisationDocument(IdRequest2 idRequest2) {
		long l_time_start = System.currentTimeMillis();
		if(idRequest2.getDocname()==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"docname can not be blank",l_diff+" ms") ,HttpStatus.BAD_REQUEST);															
		}
		
		return null;
	}
	@Override
	public ResponseEntity<CoreResponseHandler> organisationTypeDate(String orgType, String date) {
		// TODO Auto-generated method stub
		long l_time_start = System.currentTimeMillis();
		List<Organisation> organisation =  repo.findByOrgTypeAndDate(orgType, date);
		if(organisation==null || organisation.size()==0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no organisation found",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,organisation,l_diff+" ms") ,HttpStatus.OK);												
	
	}
	@Override
	public ResponseEntity<CoreResponseHandler> adminSave(String orgToken,String orgPin, AdminSave adminSave) {
		// TODO Auto-generated method stub
		long l_time_start = System.currentTimeMillis();
		Organisation organisation = repo.findByOrgTokenAndOrgPin(orgToken, orgPin);
		if(organisation==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no organisation found",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}

		organisation.setAdminUserName(adminSave.getUserName());
		organisation.setAdminPassword(adminSave.getPassword());
		organisation.setUpdateDate(new Date());
		
		Organisation organisation2 = repo.saveAndFlush(organisation);
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,organisation2,l_diff+" ms") ,HttpStatus.OK);												
			
		
	}
	@Override
	public ResponseEntity<CoreResponseHandler> organisationType(String orgType) {
		long l_time_start = System.currentTimeMillis();
		List<Organisation> organisation =  repo.findByOrgType(orgType);
		if(organisation==null || organisation.size()==0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no organisation found",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,organisation,l_diff+" ms") ,HttpStatus.OK);												
	
	}
	@Override
	public ResponseEntity<CoreResponseHandler> getEmployeeByAdmin(String userName, String password) {
		long l_time_start = System.currentTimeMillis();
		Organisation organisation =  repo.findByAdminUserNameAndAdminPassword(userName, password);
		if(organisation==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no organisation found",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		return empService.findByOrganisationByAdmin(organisation);
	
	}
	
	
	@Override
	public ResponseEntity<CoreResponseHandler> saveAdminActivation(AgentActivation_ agentActivation_) {
		long l_time_start = System.currentTimeMillis();

		String token = agentActivation_.getToken();
		
		Employee customerDetail =  empRepo.findByToken(token);
		if(customerDetail==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no employee in records with this token",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		String agentNumber = agentActivation_.getAgentnumber();
		Agent agent = agentRepository.findByMsisdn(agentNumber);
		if(agent==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no admin found",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		String agentId = agent.getId()+"";
		String strCustomerId = customerDetail.getId() +"";
		AdminActivation agentActivation = new AdminActivation();
		agentActivation.setActivationDate(agentActivation_.getDate());
		agentActivation.setActivationStatus(agentActivation_.getActivation_status());
		agentActivation.setAdminId(agentId);
		agentActivation.setEmployeeId(strCustomerId);
		agentActivation.setKycStatus(agentActivation_.getKyc_status());
		agentActivation.setRemarks(null);

		AdminActivation agentActivation2 =  adminActivationRepo.saveAndFlush(agentActivation);

		if(agentActivation2!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;

			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"saved",l_diff+" ms") ,HttpStatus.OK);

		}


		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@Override
	public ResponseEntity<CoreResponseHandler> adminGet(String msisdn) {


		long l_time_start = System.currentTimeMillis();

		Agent agent =  agentRepository.findByMsisdn(msisdn);

		if(agent==null || agent.getId()<=0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "admin not found",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}

		List<AdminActivation> listActivation = adminActivationRepo.findByAdminId(agent.getId()+"");
		if(listActivation==null || listActivation.size()==0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "admin activation not found",l_diff+" ms"),HttpStatus.NOT_FOUND);			
		}
		List<AdminActivationData_> listAgentActivationData_ = new ArrayList<AdminActivationData_>();
		for(AdminActivation agentActivation:listActivation) {
			AdminActivationData_  agentActivationData_ = new AdminActivationData_();
			String activationStatus =  agentActivation.getActivationStatus();
			String dealerType = agent.getAgentType();
			String agentMsisdn = msisdn;
			String name = agent.getAgentName();
			agentActivationData_.setActivationStatus(activationStatus);
			agentActivationData_.setAdminType(dealerType);
			agentActivationData_.setAdminMsisdn(agentMsisdn);
			agentActivationData_.setAdminName(name);
			String strCustId =  agentActivation.getEmployeeId();
			if(strCustId!=null && !strCustId.equals("")) {
				int custId  = Integer.parseInt(strCustId);
				Employee customerDetail=  empRepo.getOne(custId);
				
				agentActivationData_.setPassportIssueDate(customerDetail.getPassportDateIssued());
				agentActivationData_.setPassportExpiryDate(customerDetail.getPassportDateExpired());
				agentActivationData_.setPassportToDate(customerDetail.getPassportDateTo());
				agentActivationData_.setPassportFromDate(customerDetail.getPassportDateFrom());
				
				Document document =customerDetail.getDocument();
				List<DocumentDetail> documentList = null;
				if(document!=null) {
					documentList = new ArrayList<DocumentDetail>();

				
					
					String bank =  null;
					String electricity = null;
					String permit = null;
					String visa = null;
					String uid = null;
					String water = null;
					String telecom = null;
					String consent = null;
					String incorporationLetter=null;
					String vat=null;
					String brn=null;
					String utility=null;
					String authorizationLetter=null;
					String emp_utility_org=null;
					String touristCert=null;
					
					
					String bank_url =  document.getDocumentUrl();
					String electricity_url = document.getDocument2Url();
					String permit_url = document.getDocument3Url();
					String visa_url = document.getDocument4Url();
					String uid_url = document.getDocument5Url();
					String water_url = document.getDocument6Url();
					String telecom_url = document.getDocument7Url();
					String consent_url = document.getDocument8Url();
					String incorporationLetter_url = document.getDocument9Url();
					String vat_url = document.getDocument10Url();
					String brn_url=document.getDocument11Url();
					String utility_url=document.getDocument12Url();
					String authorizationLetter_url = document.getDocument13Url();
					String emp_utility_org_url = document.getDocument14Url();
					String touristCert_url = document.getDocument15Url();
					
					
					
					

					
					if(bank_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("bank");
						documentDetail.setDocImage(bank);
						documentDetail.setDocImage_url(bank_url);
						documentList.add(documentDetail);
					}
					if(electricity_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("electricity");
						documentDetail.setDocImage(electricity);
						documentDetail.setDocImage_url(electricity_url);
						documentList.add(documentDetail);
					}
					if(permit_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("permit");
						documentDetail.setDocImage(permit);
						documentDetail.setDocImage_url(permit_url);
						documentList.add(documentDetail);
					}
					if(visa_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("visa");
						documentDetail.setDocImage(visa);
						documentDetail.setDocImage_url(visa_url);
						documentList.add(documentDetail);
					}
					if(uid_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("uid");
						documentDetail.setDocImage(uid);
						documentDetail.setDocImage_url(uid_url);
						documentList.add(documentDetail);
					}
					if(water_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("water");
						documentDetail.setDocImage(water);
						documentDetail.setDocImage_url(water_url);
						documentList.add(documentDetail);
					}
					if(telecom_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("telecom");
						documentDetail.setDocImage(telecom);
						documentDetail.setDocImage_url(telecom_url);
						documentList.add(documentDetail);
					}
					if(consent_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("consent");
						documentDetail.setDocImage(consent);
						documentDetail.setDocImage_url(consent_url);
						documentList.add(documentDetail);
					}
					if(incorporationLetter_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("IncorporationLetter");
						documentDetail.setDocImage(incorporationLetter);
						documentDetail.setDocImage_url(incorporationLetter_url);
						documentList.add(documentDetail);
					}
					if(vat_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("vat");
						documentDetail.setDocImage(vat);
						documentDetail.setDocImage_url(vat_url);
						documentList.add(documentDetail);
					}
					if(brn_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("brn");
						documentDetail.setDocImage(brn);
						documentDetail.setDocImage_url(brn_url);
						documentList.add(documentDetail);
					}
					if(utility_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("utility");
						documentDetail.setDocImage(utility);
						documentDetail.setDocImage_url(utility_url);
						documentList.add(documentDetail);
					}
					if(authorizationLetter_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("AuthorizationLetter");
						documentDetail.setDocImage(authorizationLetter);
						documentDetail.setDocImage_url(authorizationLetter_url);
						documentList.add(documentDetail);
					}
					if(emp_utility_org_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("emp_utility_org_");
						documentDetail.setDocImage(emp_utility_org);
						documentDetail.setDocImage_url(emp_utility_org_url);
						documentList.add(documentDetail);
					}
					if(touristCert_url!=null) {
						DocumentDetail documentDetail = new DocumentDetail();
						documentDetail.setDocName("touristCert");
						documentDetail.setDocImage(touristCert);
						documentDetail.setDocImage_url(touristCert_url);
						documentList.add(documentDetail);
					}
					
					
				}
				
				
				
				agentActivationData_.setDocumentList(documentList);
				String docId = null;
				if(document!=null) {
					docId = document.getDocumentId()+"";
				}
				agentActivationData_.setDocumentId(docId);
				agentActivationData_.setActivationDate(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(customerDetail.getCreateDate()));
				
				
				
				
				String customerMsisdn = null;
				String alternateNumber = customerDetail.getAlternateNumber()==null||customerDetail.getAlternateNumber().equals("")?"":customerDetail.getAlternateNumber();
				agentActivationData_.setEmployeeAlternate(alternateNumber);
				agentActivationData_.setEmployee(customerDetail);
				SimDetail simDetail = customerDetail.getSimDetail();
				if(simDetail!=null) {
					customerMsisdn = simDetail.getMsisdn();
					agentActivationData_.setEmployeeMsisdn(customerMsisdn);
				}
				
				
				
				if(customerMsisdn!=null && !customerMsisdn.contains(",")) {
					
				
				List<OtpDetail> otpDetailList = otpRepo.findMsisdn(customerMsisdn);
				OtpDetail otpDetail = null;
				if(otpDetailList!=null && otpDetailList.size()>0) {
					otpDetail = otpDetailList.get(0);					
				}


					if(otpDetail!=null && otpDetail.getIsActive().equals("true")) {
						agentActivationData_.setOtpStatus("Otp verified");
					}
					else if(otpDetail!=null && otpDetail.getIsActive().equals("false")) {
						agentActivationData_.setOtpStatus("Otp sent");
					}
					else if(otpDetail==null) {
						agentActivationData_.setOtpStatus("Otp not sent");
					}
				
				
				
				
				}
				
				if(customerDetail!=null) {
				String resType = customerDetail.getResidentType()==null || customerDetail.getResidentType().equals("")?"":customerDetail.getResidentType();
				String UserType = null;
				if(resType!=null && !resType.equals("")&& resType.equals("citizen")) {
					UserType = "CITIZEN";
				}
				else if(resType!=null && !resType.equals("")&& resType.equals("tourist")) {
					UserType = "TOURIST";
				}
				else if(resType!=null && !resType.equals("")&& resType.equals("resident")) {
					UserType = "RESIDENT";
				}
				else if(resType!=null && !resType.equals("") && resType.equals("employee")){
					UserType = "EMPLOYEE";
				}
//				String DocumentType = null;
//				if(UserType.equals("CITIZEN")) {
//					DocumentType = "National ID";
//				}
//				else if(UserType.equals("EMPLOYEE")) {
//					DocumentType = "National ID";					
//				}
//				else {
//					DocumentType = "Passport";
//				}
				
				agentActivationData_.setEmployeeType(UserType);
				agentActivationData_.setEmployeeIDType(customerDetail.getIdType());
				}
			}
			
			listAgentActivationData_.add(agentActivationData_);
		}


		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;

		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,listAgentActivationData_,l_diff+" ms") ,HttpStatus.OK);			

		
	
	
	}
	@Override
	public ResponseEntity<CoreResponseHandler> checkAcctid(String acctId) {
		long l_time_start = System.currentTimeMillis();
		Organisation organisation =  repo.findByAcctId(acctId);
		if(organisation!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"already there",l_diff+" ms") ,HttpStatus.BAD_REQUEST);															
		}
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"new",l_diff+" ms") ,HttpStatus.OK);												
	
	}
	@Override
	public ResponseEntity<CoreResponseHandler> checkBrn(String brn) {
		long l_time_start = System.currentTimeMillis();
		List<Organisation> org1 = repo.findByBrn(brn);
		if(org1!=null && org1.size()>0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"duplicate brn not allowed",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												

		}
		else {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"OK",l_diff+" ms") ,HttpStatus.OK);												
				
		}
		
//		long l_end_time = System.currentTimeMillis();
//		long l_diff = l_end_time-l_time_start;
//		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

	}
	@Override
	public ResponseEntity<CoreResponseHandler> checkCert(String cert) {
		// TODO Auto-generated method stub
		long l_time_start = System.currentTimeMillis();
		List<Organisation> org1 = repo.findByTouristCertificateNumber(cert);
		if(org1!=null && org1.size()>0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"duplicate cert number not allowed",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												

		}
		else {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"OK",l_diff+" ms") ,HttpStatus.OK);												
				
		}
		
	}
	@Override
	public ResponseEntity<CoreResponseHandler> checkExpiryDate(String expiryDate) {
		long l_time_start = System.currentTimeMillis();
		
		try {
			 if (new SimpleDateFormat("yyyy-MM-dd").parse(expiryDate).before(new Date())) {
				 long l_end_time = System.currentTimeMillis();
					long l_diff = l_end_time-l_time_start;
					return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"expiry date not allowed",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
					
				}
			 else {
					long l_end_time = System.currentTimeMillis();
					long l_diff = l_end_time-l_time_start;
					return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"OK",l_diff+" ms") ,HttpStatus.OK);												
				
			 }
		 }catch(Exception ex) {
			 ex.printStackTrace();
				long l_end_time = System.currentTimeMillis();
				long l_diff = l_end_time-l_time_start;
				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

		 }		
	}

	@Override
	public ResponseEntity<CoreResponseHandler> moveAdminToEmployee(String token) {
		long l_time_start = System.currentTimeMillis();
		CustomerDetail cust = custService.findByToken(token);
		
		if(cust==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "admin as customer not found in customer detail",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		
		
		List<Organisation> lsOrg = repo.findByAdmin(cust);
		if(lsOrg==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no admin/customerDetail mapping found in organisation",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		Organisation org = lsOrg.get(0);
		
		CustomerDetail custAdmin = org.getAdmin();
		
		Employee emp = new Employee();
		
		emp.setAddress(custAdmin.getAddress());
		emp.setAlternateNumber(custAdmin.getAlternateNumber());
		emp.setContingencyErrorCode(custAdmin.getContingencyErrorCode());
		emp.setCorrelationId(custAdmin.getCorrelationId());
		emp.setCreateDate(custAdmin.getCreateDate());
		emp.setDob(custAdmin.getDob());
		emp.setDocument(custAdmin.getDocument());
		emp.setEmail(custAdmin.getEmail());
		emp.setFirstName(custAdmin.getFirstName());
		emp.setGender(custAdmin.getGender());
		if(custAdmin.getResidentType().equalsIgnoreCase("citizen"))
		emp.setIdType("nid");
		else
		emp.setIdType("passport");
		emp.setImage(custAdmin.getImage());
		emp.setKycStatus(custAdmin.getKycStatus());
		emp.setLastName(custAdmin.getLastName());
		emp.setMaidenName(custAdmin.getMaidenName());
		emp.setNationality(custAdmin.getNationality());
		emp.setNewCustomer(custAdmin.getNewCustomer());
		emp.setOrganisation(org);
		emp.setOriginalDocId(custAdmin.getId());
		emp.setOtherDocCount(custAdmin.getOtherDocCount());
		emp.setPassportDateExpired(custAdmin.getPassportDateExpired());
		emp.setPassportDateFrom(custAdmin.getPassportDateFrom());
		emp.setPassportDateIssued(custAdmin.getPassportDateIssued());
		emp.setPassportDateTo(custAdmin.getPassportDateTo());
		emp.setPermitValue(custAdmin.getPermitValue());
		emp.setPmUid(custAdmin.getPmUid());
		emp.setRemarks(custAdmin.getRemarks());
		emp.setResidentType("employee");
		emp.setSimDetail(custAdmin.getSimDetail());
		emp.setToken(custAdmin.getToken());
		emp.setUpdateDate(new Date());
		
		Employee emp_ =  empRepo.saveAndFlush(emp);
		if(emp_!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"OK saved employee as admin",l_diff+" ms") ,HttpStatus.OK);												

		}
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

	}
	@Override
	public ResponseEntity<CoreResponseHandler> updateOrganisationAdmin(String find_token,String set_token) {
		long l_time_start = System.currentTimeMillis();
		CustomerDetail cust_find = custService.findByToken(find_token);
		CustomerDetail cust_to = custService.findByToken(set_token);
		Organisation org_2  = null;
		if(cust_find==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "find_token not in customer detail",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		
		if(cust_to==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "set_token not in customer detail",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}		
		
		List<Organisation> lsOrg = repo.findByAdmin(cust_find);
		if(lsOrg==null || lsOrg.size()==0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "find_token admin not in organisation",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		
		List<Organisation> lsOrg2 = repo.findByAdmin(cust_to);
		if(lsOrg2!=null && lsOrg2.size()>0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "set_token admin found duplicate in organisation",l_diff+" ms"),HttpStatus.NOT_FOUND);
		}
		else {
			Organisation org_ = lsOrg.get(0);
			org_.setAdmin(cust_to);
			org_2 = repo.saveAndFlush(org_);
		}
		
		if(org_2!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"admin updated",l_diff+" ms") ,HttpStatus.OK);												

		}
		
		
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

		
		

	}
	@Override
	public ResponseEntity<CoreResponseHandler> setOrganisationAdmin(AdminRegister adminRegister) {
		long l_time_start = System.currentTimeMillis();
		Organisation org = repo.findByAcctId(adminRegister.getAcctId());
		Organisation org_  =null;
		
//		if(org.getAdmin()!=null) {
//			long l_end_time = System.currentTimeMillis();
//			long l_diff = l_end_time-l_time_start;
//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"admin is already set. use update api to update admin",l_diff+" ms") ,HttpStatus.BAD_REQUEST);
//
//		}
		
		CustomerDetail cust = custService.findByToken(adminRegister.getToken());
		if(cust==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "customer to be set admin not found",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		
		OrgMsisdn obj =  orgMsisdnRepo.findByOrganisationAndMsisdn(org, adminRegister.getMsisdn());
		if(obj==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "this msisdn is not registered with organisation",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		obj.setIsAdmin("yes");
		obj.setUpdateDate(new Date());
		
		orgMsisdnRepo.saveAndFlush(obj);

		org.setAdmin(cust);
		org.setAdminUserName(adminRegister.getUsername());
		org.setAdminPassword(adminRegister.getPassword());
		org.setUpdateDate(new Date());
		org_ =  repo.saveAndFlush(org);

		if(org_!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"admin set",l_diff+" ms") ,HttpStatus.OK);												

		}
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

		
	}
	
	@Override
	public ResponseEntity<CoreResponseHandler> organisationadminemployeelogin(AdminLogin adminLogin) {
		// TODO Auto-generated method stub
		long l_time_start = System.currentTimeMillis();
		Organisation organisation =  repo.findByAdminUserNameAndAdminPassword(adminLogin.getUsername(), adminLogin.getPassword());
		if(organisation==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no admin. check credentials",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		
		List<Employee> list =  empRepo.findByOrganisation(organisation);
		
		if(list!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,list,l_diff+" ms") ,HttpStatus.OK);												

		}
		
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

	}
	@Override
	public ResponseEntity<CoreResponseHandler> allocateMsisdnsToOrganisation(OrgMsisdn_ orgMsisdn_) {
		// TODO Auto-generated method stub
		long l_time_start = System.currentTimeMillis();
		Organisation org = repo.findByAcctId(orgMsisdn_.getAcctId());
		if(org==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no such organisation",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		int errors = 0;
		for(String msisdn:orgMsisdn_.getMsisdns()) {
			try {
			OrgMsisdn obj = new OrgMsisdn();
			obj.setCreateDate(new Date());
			obj.setIsAdmin("no");
			obj.setOrganisation(org);
			obj.setMsisdn(msisdn);
			orgMsisdnRepo.saveAndFlush(obj);
			}catch(Exception ex) {
				errors++;
				ex.printStackTrace();
				continue;
			}
		}
		if(errors==0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"saved without any error",l_diff+" ms") ,HttpStatus.OK);												
			
		}
		else {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"saved with "+errors+" errors",l_diff+" ms") ,HttpStatus.OK);												
			
		}
		
	}
	@Override
	public ResponseEntity<CoreResponseHandler> organisationmsisdns(String acctId) {
		long l_time_start = System.currentTimeMillis();
		Organisation org =  repo.findByAcctId(acctId);
		if(org==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no such organisation",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		List<OrgMsisdn> list = orgMsisdnRepo.findByOrganisation(org);
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,list,l_diff+" ms") ,HttpStatus.OK);												
		
		
		
	}
	@Override
	public ResponseEntity<CoreResponseHandler> organisationemployees(String acctId) {
		long l_time_start = System.currentTimeMillis();
		Organisation org =  repo.findByAcctId(acctId);
		if(org==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no such organisation",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		return empService.findByOrganisationByAdmin(org);
		
	}
	@Override
	public ResponseEntity<CoreResponseHandler> checkemployeemsisdnstatus(String msisdn) {
		long l_time_start = System.currentTimeMillis();
		EmployeeOrganisationMsisdn obj =  employeeOrganisationMsisdnRepository.findByMsisdn(msisdn);
		String status = null;
		if(obj==null) {
			status = "free";
		}
		else {
			status=obj.getStatus();
		}
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,status,l_diff+" ms") ,HttpStatus.OK);												
		
	}
	
	
	
	
	
	@Override
	public ResponseEntity<CoreResponseHandler> readwritecsv() {
		// TODO Auto-generated method stub
		Set<String> setOrganisation = new HashSet<String>();
		Set<String> setMsisdn = new HashSet<String>();
	      
		long l_time_start = System.currentTimeMillis();
		
		try {
		      String strFile = "C:\\Users\\nitin\\OneDrive\\Documents\\postpaid_active_nbr.csv";
		      CSVReader reader = new CSVReader(new FileReader(strFile));
		      String [] nextLine;
		      int lineNumber = 0;
		      while ((nextLine = reader.readNext()) != null) {
		        lineNumber++;
		        String acctId = nextLine[0];
		        String msisdn = nextLine[1];
		        
		        
		        int errors = 0;
		        
		        acctId = acctId.replace(".00", "");
		        Organisation org = repo.findByAcctId(acctId);
				if(org==null) {
					setOrganisation.add(acctId);
				continue;
					
				}
				
					try {
					OrgMsisdn obj = new OrgMsisdn();
					obj.setCreateDate(new Date());
					obj.setIsAdmin("no");
					obj.setOrganisation(org);
					obj.setMsisdn(msisdn);
					obj =  orgMsisdnRepo.saveAndFlush(obj);
					if(obj!=null) {
						System.out.println(obj);
					}
					}catch(Exception ex) {
						setMsisdn.add(msisdn);
						errors++;
						ex.printStackTrace();
						continue;
					}
				
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		      }
		    }catch(Exception ex) {
		    	ex.printStackTrace();
		    }
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		Test_ test =  new Test_();
		test.setSetMsisdn(setMsisdn);
		test.setSetOrganisation(setOrganisation);
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,test,l_diff+" ms") ,HttpStatus.OK);												
		
	}
	
	
	

}
