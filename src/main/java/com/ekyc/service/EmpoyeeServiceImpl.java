package com.ekyc.service;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ekyc.beans.DocumentUpdateRequest;
import com.ekyc.beans.Document_;
import com.ekyc.beans.EmployeeOrganisationMsisdn_;
import com.ekyc.beans.EmployeeSaveDetail;
import com.ekyc.beans.IdRequest;
import com.ekyc.beans.IdRequest3;
import com.ekyc.beans.Image_;
import com.ekyc.beans.MsisdnUpdate;
import com.ekyc.beans.OcrId;
import com.ekyc.beans.SimAccessToken;
import com.ekyc.beans.SimNicResponse;
import com.ekyc.beans.SimPassportResponse;
import com.ekyc.beans.VerifiedMsisdn_;
import com.ekyc.beans.VerifyMLRequest;
import com.ekyc.beans.VerifyMLResponse;
import com.ekyc.model.CustomerDetail;
import com.ekyc.model.Document;
import com.ekyc.model.Employee;
import com.ekyc.model.EmployeeOrganisationMsisdn;
import com.ekyc.model.Image;
import com.ekyc.model.Organisation;
import com.ekyc.model.OtherDocRequiredCount;
import com.ekyc.model.SimDetail;
import com.ekyc.model.VerifiedMsisdn;
import com.ekyc.repository.EmployeeOrganisationMsisdnRepository;
import com.ekyc.repository.EmployeeRepository;
import com.ekyc.repository.OrganisationRepository;
import com.ekyc.repository.OtherDocRequiredCountRepository;
import com.ekyc.repository.SimDetailRepository;
import com.ekyc.repository.VerifiedMsisdnRepository;
import com.ekyc.utils.ApplicationResponse;
import com.ekyc.utils.CommonUtilityFunctions;
import com.ekyc.utils.CoreResponseHandler;
import com.ekyc.utils.ResponseStatusEnum;
import com.ekyc.utils.SuccessResponseBeanRefined;
//public class EmpoyeeServiceImpl{}

@Service
public class EmpoyeeServiceImpl implements EmployeeService {

@Autowired
private EmployeeRepository repo;
@Autowired
private CommonUtilityFunctions utility;
@Autowired
private CustomerDetailService custService;
@Autowired
private ImageService imgService;

@Autowired
private OrganisationService orgService;

@Autowired
private RestTemplate restTemplate0;

@Autowired
private OtherDocRequiredCountRepository otherDocRepo;

@Autowired
private DocumentService docService;

@Autowired
private SimDetailRepository simRepo;



	@Override
	public ResponseEntity<CoreResponseHandler> employeeGet(String orgToken, String orgPin) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ResponseEntity<CoreResponseHandler> callFirstOcr(String orgToken, String orgPin, IdRequest idRequest) {
		long l_time_start = System.currentTimeMillis();

		
		Organisation organisation = orgService.organisationGet_(orgToken, orgPin);
		if(organisation==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "organisation not found",l_diff+" ms"),HttpStatus.NOT_FOUND);			
		
		}
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		String requestId = utility.getRandomNumber(9);
		requestBody.add("doctype", "idproof");
		requestBody.add("category", "identity");
		requestBody.add("docname", idRequest.getIdType());
		requestBody.add("image", idRequest.getId64());
		requestBody.add("requestId", requestId);

		//		ResponseEntity<OcrId> response = null;
		//		HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
		//		try {
		//			response = restTemplate.exchange("http://182.74.113.62:9001/ekyc/ocr/id", HttpMethod.POST,
		//					formEntity, OcrId.class);
		//		} catch (Exception ex) {
		//			System.out.println("Exception...");
		//			ex.printStackTrace();
		//			long l_time_end = System.currentTimeMillis();
		//			long l_diff = l_time_end - l_time_start;
		//			ex.printStackTrace();
		//			long l_end_time = System.currentTimeMillis();
		//			l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
		//		}
		//		OcrId obj = response.getBody();
		OcrId obj = new OcrId();
		long l_time_end = System.currentTimeMillis();
		long l_diff = l_time_end - l_time_start;

		if(obj!=null) {
			OcrId ocrId =  saveEmployee(obj,idRequest,organisation);
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,ocrId,l_diff+" ms") ,HttpStatus.OK);												


		}

		long l_end_time = System.currentTimeMillis();
		l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);








	}

	@Override
	public ResponseEntity<CoreResponseHandler> updateEmployeeDocument(DocumentUpdateRequest documentUpdateRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	private OcrId saveEmployee(OcrId ocrId,IdRequest idRequest,Organisation organisation) {
		// TODO Auto-generated method stub
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		String token =null;
		try {
		Employee customerDetail = new Employee();
		//dd-MM-yyyy
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		customerDetail.setOriginalDocId(ocrId.getId()==null||ocrId.getId().equals("")?"":ocrId.getId());
		customerDetail.setFirstName(ocrId.getFirstName()==null||ocrId.getFirstName().equals("")?"":ocrId.getFirstName());
		customerDetail.setLastName(ocrId.getLastName()==null||ocrId.getLastName().equals("")?"":ocrId.getLastName());
		customerDetail.setResidentType(idRequest.getResidentType()==null?"":idRequest.getResidentType());
		customerDetail.setIdType(idRequest.getIdType()==null?"":idRequest.getIdType());
		if(ocrId.getDob()==null) {
			customerDetail.setDob(null);
		}
		else {
			Date dob = null;
			try {
		dob = sdf.parse(ocrId.getDob());
		customerDetail.setDob(dob);
			}catch(Exception ex) {
				customerDetail.setDob(null);
			}
		}
		token = utility.getRandomNumber(9);
		
		Image_ image_ = new Image_();
		image_.setBase64(idRequest.getId64());
		image_.setImgType("kyc");
		image_.setToken(token);
		Image image  = imgService.saveImage(image_);
		customerDetail.setNationality(ocrId.getCountryCode()==null||ocrId.getCountryCode().equals("")?"":ocrId.getCountryCode());
		customerDetail.setGender(ocrId.getGender()==null||ocrId.getGender().equals("")?"":ocrId.getGender());
		customerDetail.setImage(image);
		//customerDetail.setAddress(adsf);
		customerDetail.setToken(token);
		customerDetail.setOrganisation(organisation);
		Date dt = new Date();
		
		customerDetail.setCreateDate(dt);
		customerDetail.setUpdateDate(dt);
		customerDetail.setKycStatus("id proof submitted");
		SimpleDateFormat sdf2 = new  SimpleDateFormat("yyyy-MM-dd");
		
		Employee customerDetail_ = updateCustomer(customerDetail,sdf2.format(dt));
		if(customerDetail_!=null) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			ocrId.setToken(token);
			return ocrId;
			//return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,ocrId,l_diff+" ms") ,HttpStatus.OK);												

		}
		}catch(Exception ex) {
			ex.printStackTrace();
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return null;
			//return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		long l_end_time = System.currentTimeMillis();
		l_diff = l_end_time-l_time_start;
		return null;
		//return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	private Employee updateCustomer(Employee customerDetail,String strDate) {
		// TODO Auto-generated method stub
		Employee customerDetail2 =  repo.saveAndFlush(customerDetail);
		return customerDetail2;
	}



	@Override
	public ResponseEntity<CoreResponseHandler> updateEmployeeWithSelfie(OcrId ocrId) {
		// TODO Auto-generated method stub
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		try {
			Employee customerDetail0 = findByID(ocrId.getId());
			//System.out.println("0"+customerDetail0.getCustomerId());



//			if(customerDetail0!=null && customerDetail0.getToken()!=null && !customerDetail0.getKycStatus().equals("verified") && !customerDetail0.getKycStatus().equals("verification failed") && !customerDetail0.getKycStatus().equals("verified. Other doc proof also needed.") ) {
//				Employee customerDetail  =  findByToken(ocrId.getToken());
//				if(customerDetail==null) {
//					long l_end_time = System.currentTimeMillis();
//					l_diff = l_end_time-l_time_start;
//					System.out.println("1111");
//					return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"detail already there with token :"+customerDetail0.getToken()+" kyc status : "+customerDetail0.getKycStatus(),l_diff+" ms") ,HttpStatus.OK);												
//
//				}

				//				if(customerDetail.getKycStatus().equals("id proof submitted")) {
				//					Image image = customerDetail.getImage();
				//					custService.deleteDuplicateCustomer(customerDetail);
				//					imgService.deleteDuplicateCustomerImage(image);
				//					long l_end_time = System.currentTimeMillis();
				//					l_diff = l_end_time-l_time_start;
				//					System.out.println("1");
				//					return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"detail already there with token :"+customerDetail0.getToken()+" kyc status : "+customerDetail0.getKycStatus(),l_diff+" ms") ,HttpStatus.OK);												
				//						
				//				}
				//				else {
				//					long l_end_time = System.currentTimeMillis();
				//					l_diff = l_end_time-l_time_start;
				//					System.out.println("###");
				//					return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"detail already there with token :"+customerDetail0.getToken()+" kyc status : "+customerDetail0.getKycStatus(),l_diff+" ms") ,HttpStatus.OK);
				//				}


			//}
			System.out.println("2");
			Employee customerDetail  =  findByToken(ocrId.getToken());
			System.out.println("3");
			if(customerDetail==null) {

				return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no customer in records with this token",null),HttpStatus.NOT_FOUND);

			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$ "+ ocrId.getToken());
			customerDetail.setOriginalDocId(ocrId.getId()==null||ocrId.getId().equals("")?"":ocrId.getId());
			customerDetail.setFirstName(ocrId.getFirstName()==null||ocrId.getFirstName().equals("")?"":ocrId.getFirstName());
			customerDetail.setLastName(ocrId.getLastName()==null||ocrId.getLastName().equals("")?"":ocrId.getLastName());
			if(ocrId.getDob()==null) {
				customerDetail.setDob(null);
			}
			else {
				Date dob = null;
				try {
					dob = sdf.parse(ocrId.getDob());
					customerDetail.setDob(dob);
				}catch(Exception ex) {
					customerDetail.setDob(null);
				}
			}

			customerDetail.setNationality(ocrId.getCountryCode()==null||ocrId.getCountryCode().equals("")?"":ocrId.getCountryCode());
			customerDetail.setGender(ocrId.getGender()==null||ocrId.getGender().equals("")?"":ocrId.getGender());


			Image image = customerDetail.getImage();


//			Image_ image_ = new Image_();
//			image_.setBase64(ocrId.getSelfie64());
//			image_.setImgType("self");
//			image_.setToken(ocrId.getToken());
//			Image image2  = imgService.updateImageWithSelfie(image, image_);
//			if(image2!=null) {
//				customerDetail.setUpdateDate(new Date());
//				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//				Employee customerDetail2 = updateCustomer(customerDetail,sdf2.format(customerDetail.getCreateDate()));
//
//				RestTemplate restTemplate = new RestTemplate();
//
//				HttpHeaders headers = new HttpHeaders();
//				headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//
//				JSONObject obj =new JSONObject();
//				obj.put("password", "1s84jBiV6ZpHO6VR5B");
//				obj.put("username", "mtml");
//
//				ResponseEntity<SimAccessToken> response = null;
//				HttpEntity formEntity = new HttpEntity(obj, headers);
//				try {
//					response = restTemplate0.exchange("https://simapi.icta.mu/icta/auth/login", HttpMethod.POST,
//							formEntity, SimAccessToken.class);
//					if(response==null) {
//						long l_end_time = System.currentTimeMillis();
//						l_diff = l_end_time-l_time_start;
//						return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"sim api access token null",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
//					}
//					if(response!=null) {
//
//						SimAccessToken simAccessToken = response.getBody();
//						if(simAccessToken==null) {
//							long l_end_time = System.currentTimeMillis();
//							l_diff = l_end_time-l_time_start;
//							return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"sim api access token null",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
//						}
//						return callSimApi(simAccessToken.getAccessToken(),customerDetail2);
//					}
//				} catch (HttpStatusCodeException ex) {
//					System.out.println("Exception...");
//					ex.printStackTrace();
//					int statusCode = ex.getStatusCode().value();
//					String abcObj =ex.getResponseBodyAsString();
//					JSONParser parser = new JSONParser();
//					JSONObject obj2 = (JSONObject)parser.parse(abcObj);
//					System.out.println(obj2.toJSONString());
//					long l_end_time = System.currentTimeMillis();
//					l_diff = l_end_time-l_time_start;
//					return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,obj2,l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
//				}
//				catch(Exception ex22) {
//					System.out.println("###########################");
//					ex22.printStackTrace();
//				}
//
//
//			}
//			else {
//				long l_end_time = System.currentTimeMillis();
//				l_diff = l_end_time-l_time_start;
//				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"unable to upload selfie image",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
//			}










		}catch(Exception ex) {
			ex.printStackTrace();
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"error",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
		}

		long l_end_time = System.currentTimeMillis();
		l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"error",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public ResponseEntity<CoreResponseHandler> nicVerify(JSONObject obj) {
		// TODO Auto-generated method stub
		 String accessToken =  getAccessToken();

			long l_diff = 0;
			long l_time_start = System.currentTimeMillis();
			//CustomerDetail customerDetail_ =null;
			SimNicResponse simNicResponse = null;
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", "Bearer "+accessToken);
			ResponseEntity<SimNicResponse> response = null;
			HttpEntity formEntity = new HttpEntity(obj, headers);
			
			String token =(String) obj.get("token");
			String newCustomer = (String)obj.get("newCustomer");
			Employee customerDetail =  findByToken(token);
			
			
			try {
				response = restTemplate0.exchange("https://simapi.icta.mu/icta/verifyNIC", HttpMethod.POST,
						formEntity, SimNicResponse.class);
				if(response==null) {
					long l_end_time = System.currentTimeMillis();
					l_diff = l_end_time-l_time_start;
					return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"sim verify nic response null",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
				}
				if(response!=null) {
					simNicResponse = response.getBody();
					if(simNicResponse==null) {
						long l_end_time = System.currentTimeMillis();
						l_diff = l_end_time-l_time_start;
						return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"sim verify nic response null",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
					}

					System.out.println(simNicResponse);
					if(!customerDetail.getKycStatus().equals("contingency not verified")) {
					customerDetail.setKycStatus("verified. Other doc proof also needed.");
					customerDetail.setUpdateDate(new Date());
					}
					customerDetail.setCorrelationId(simNicResponse.getCorrelationId());
					Employee customerDetail2 =  updateCustomer(customerDetail, "");
					
					return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,simNicResponse,null) ,HttpStatus.OK);
//					customerDetail_ = custService.findByID(simNicResponse.getNicNum());
//					if(customerDetail_==null) {
//						long l_end_time = System.currentTimeMillis();
//						l_diff = l_end_time-l_time_start;
//						return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no such customer in records",l_diff+" ms"),HttpStatus.NOT_FOUND);
//
//					}
//					System.out.println(simNicResponse);

				}
			} catch (HttpStatusCodeException ex) {
				System.out.println("Exception...");
				ex.printStackTrace();
				int statusCode = ex.getStatusCode().value();
				String abcObj =ex.getResponseBodyAsString();
				JSONParser parser = new JSONParser();
				JSONObject obj2 =null;
				try {
					obj2 = (JSONObject)parser.parse(abcObj);
				}catch(Exception ex2){
					ex2.printStackTrace();
				}
				if(obj2!=null) {
					System.out.println(obj2.toJSONString());
					
					
					
					System.out.println("#### "+obj2.toJSONString());
					String errorCode = (String) obj2.get("errorCode");

					customerDetail.setContingencyErrorCode(errorCode);
					
					if(errorCode.equals("0100")) {
						if(newCustomer.equals("yes")) {
						customerDetail.setKycStatus("contingency not verified");
						customerDetail.setToken("T"+token);
						customerDetail.setUpdateDate(new Date());
						}
						
					}
					
					updateCustomer(customerDetail, "");
					
					
					
					
					
					
					long l_end_time = System.currentTimeMillis();
					l_diff = l_end_time-l_time_start;
					return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,obj2,l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
				}
				else
				{
					long l_end_time = System.currentTimeMillis();
					l_diff = l_end_time-l_time_start;
					return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);				
				}
				
			}


			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);				
		
		
	}

	@Override
	public ResponseEntity<CoreResponseHandler> passportVerify(JSONObject obj) {
		// TODO Auto-generated method stub
		String accessToken =  getAccessToken();

		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		SimPassportResponse simPassportResponse = null;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", "Bearer "+accessToken);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		JSONObject obj =new JSONObject();
//		obj.put("passportNumber", customerDetail.getId());
//		if(customerDetail.getDob()==null ) {
//			long l_end_time = System.currentTimeMillis();
//			l_diff = l_end_time-l_time_start;
//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"dob of customer is NULL during passport verification",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		obj.put("dateOfBirth",  sdf.format(customerDetail.getDob()));
//		obj.put("gender", customerDetail.getGender().substring(0, 1).toUpperCase());
//		if(customerDetail.getResidentType().toUpperCase().startsWith("TOU")) {
//			obj.put("userType", "TOURIST");
//		}
//		else {
//			obj.put("userType", "RESIDENT");
//		}
//		obj.put("nationalityCode", customerDetail.getNationality());

		String token =(String) obj.get("token");
		String newCustomer = (String) obj.get("newCustomer");
		Employee customerDetail =  findByToken(token);
		
		
		
		ResponseEntity<SimPassportResponse> response = null;
		HttpEntity formEntity = new HttpEntity(obj, headers);
		try {
			response = restTemplate0.exchange("https://simapi.icta.mu/icta/verifyPassportNumber", HttpMethod.POST,
					formEntity, SimPassportResponse.class);
			if(response==null) {
				long l_end_time = System.currentTimeMillis();
				l_diff = l_end_time-l_time_start;
				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"sim verify nic response null",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(response!=null) {
				simPassportResponse = response.getBody();
				if(simPassportResponse==null) {
					long l_end_time = System.currentTimeMillis();
					l_diff = l_end_time-l_time_start;
					return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"sim verify nic response null",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
				}
				System.out.println(simPassportResponse);
				if(!customerDetail.getKycStatus().equals("contingency not verified")) {
				customerDetail.setKycStatus("verified. Other doc proof also needed.");
				customerDetail.setUpdateDate(new Date());
				}
				customerDetail.setCorrelationId(simPassportResponse.getCorrelationId());
				customerDetail.setPmUid(simPassportResponse.getPmUid());
				Employee customerDetail2 =  updateCustomer(customerDetail, "");
			
				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,simPassportResponse,null) ,HttpStatus.OK);

//				customerDetail_ = custService.findByID(simPassportResponse.getPassportNum());
//				if(customerDetail_==null) {
//					long l_end_time = System.currentTimeMillis();
//					l_diff = l_end_time-l_time_start;
//					return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no such customer in records",l_diff+" ms"),HttpStatus.NOT_FOUND);
//
//				}
//				System.out.println(simPassportResponse);

			}
		} catch (HttpStatusCodeException ex) {
			System.out.println("Exception...");
			//ex.printStackTrace();
			int statusCode = ex.getStatusCode().value();
			String abcObj =ex.getResponseBodyAsString();
			JSONParser parser = new JSONParser();
			JSONObject obj2 =null;
			try {
				obj2 = (JSONObject)parser.parse(abcObj);
			}catch(Exception ex2){
				ex2.printStackTrace();
			}
			if(obj2!=null) {

				System.out.println(obj2.toJSONString());
				
				
				
				System.out.println("@@@@ "+obj2.toJSONString());
				String errorCode = (String) obj2.get("errorCode");
				
				customerDetail.setContingencyErrorCode(errorCode);
				if(errorCode.equals("0100")) {
					if(newCustomer.equals("yes")) {
					customerDetail.setKycStatus("contingency not verified");
					customerDetail.setToken("T"+token);
					customerDetail.setUpdateDate(new Date());
					}
				}
				updateCustomer(customerDetail, "");
				
				
				
				
				
				
				long l_end_time = System.currentTimeMillis();
				l_diff = l_end_time-l_time_start;
				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,obj2,l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
							
			}
			else
			{
				long l_end_time = System.currentTimeMillis();
				l_diff = l_end_time-l_time_start;
				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);				
			}
			
		}

		long l_end_time = System.currentTimeMillis();
		l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

	}

	
	
	
	private Employee findByID(String id) {
		// TODO Auto-generated method stub
		return repo.findByID(id);
	}
	private Employee findByToken(String token) {
		// TODO Auto-generated method stub
		Employee customerDetail  = repo.findByToken(token);
		if(customerDetail==null)
		return null;
		else
			return customerDetail;
	}
	private ResponseEntity<CoreResponseHandler> callSimApi(String accessToken,Employee customerDetail) {
		String idType = customerDetail.getIdType();
		if(idType.equalsIgnoreCase("passport")) {
			return callVerifyPassportNumber(accessToken,customerDetail);
			
		}
		else {
			return  callVerifyNic(accessToken,customerDetail);
		}

	}
	private ResponseEntity<CoreResponseHandler> callVerifyPassportNumber(String accessToken,Employee customerDetail) {
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		boolean test = false;
		Employee customerDetail_ =null;
		SimPassportResponse simPassportResponse = null;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", "Bearer "+accessToken);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		JSONObject obj =new JSONObject();
		obj.put("passportNumber", customerDetail.getOriginalDocId());
		if(customerDetail.getDob()==null ) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"dob of customer is NULL during passport verification",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		obj.put("dateOfBirth",  sdf.format(customerDetail.getDob()));
		obj.put("gender", customerDetail.getGender().substring(0, 1).toUpperCase());
		if(customerDetail.getResidentType().toUpperCase().startsWith("TOU")) {
			obj.put("userType", "TOURIST");
		}
		else {
			obj.put("userType", "RESIDENT");
		}
		obj.put("nationalityCode", customerDetail.getNationality());

		ResponseEntity<SimPassportResponse> response = null;
		HttpEntity formEntity = new HttpEntity(obj, headers);
		try {
			response = restTemplate0.exchange("https://simapi.icta.mu/icta/verifyPassportNumber", HttpMethod.POST,
					formEntity, SimPassportResponse.class);
			if(response==null) {
				long l_end_time = System.currentTimeMillis();
				l_diff = l_end_time-l_time_start;
				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"sim verify nic response null",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(response!=null) {
				simPassportResponse = response.getBody();
				if(simPassportResponse==null) {
					long l_end_time = System.currentTimeMillis();
					l_diff = l_end_time-l_time_start;
					return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"sim verify nic response null",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
				}

//				customerDetail_ = custService.findByID(simPassportResponse.getPassportNum());
//				if(customerDetail_==null) {
//					long l_end_time = System.currentTimeMillis();
//					l_diff = l_end_time-l_time_start;
//					return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no such customer in records",l_diff+" ms"),HttpStatus.NOT_FOUND);
//
//				}
				System.out.println(simPassportResponse);

			}
		} catch (HttpStatusCodeException ex) {
			test = true;
			System.out.println("Exception...");
			//ex.printStackTrace();
			int statusCode = ex.getStatusCode().value();
			String abcObj =ex.getResponseBodyAsString();
			JSONParser parser = new JSONParser();
			JSONObject obj2 =null;
			try {
				obj2 = (JSONObject)parser.parse(abcObj);
			}catch(Exception ex2){
				ex2.printStackTrace();
			}
			if(obj2!=null) {
				
				System.out.println("@@@@ "+obj2.toJSONString());
//				String errorCode = (String) obj2.get("errorCode");

//				customerDetail.setContingencyErrorCode(errorCode);
//				if(errorCode.equals("0100")) {
//					customerDetail.setKycStatus("contingency not verified");
//					customerDetail.setUpdateDate(new Date());
//					
//				}
//				custService.updateCustomer(customerDetail, "");
			}
			else
			{
				long l_end_time = System.currentTimeMillis();
				l_diff = l_end_time-l_time_start;
				//return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);				
			}
			
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			//return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,obj2,l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		String firstName=null;
		String maidenName =null;
		String lastName = null;
		if(test==false) {
			customerDetail.setCorrelationId(simPassportResponse.getCorrelationId());
		customerDetail.setPmUid(simPassportResponse.getPmUid());
		firstName = simPassportResponse.getFirstName()==null?"":simPassportResponse.getFirstName();
		maidenName = simPassportResponse.getMaidenName()==null?"":simPassportResponse.getMaidenName();
		lastName = simPassportResponse.getLastName()==null?"":simPassportResponse.getLastName();

		//customerDetail = custService.updateCustomer(customerDetail, "");
		}
		Image image = customerDetail.getImage();
		String img64Orignal= image.getOriginalPhotoBase64();
		String passportImage = image.getKycPhotoBase64();

		String name = firstName+" "+maidenName+" "+lastName;
		name=name.trim();
		name=name.replace("  ", " ");
		VerifyMLRequest verifyMLRequest = new VerifyMLRequest();
		verifyMLRequest.setImage(img64Orignal);
		verifyMLRequest.setNimage(passportImage);
		verifyMLRequest.setName(name);
		verifyMLRequest.setId(customerDetail.getOriginalDocId());
		verifyMLRequest.setUtype(customerDetail.getResidentType());

		try {
			VerifyMLResponse verifyMLResponse =  callVerifyML(verifyMLRequest);
			if(verifyMLResponse==null) {
				long l_end_time = System.currentTimeMillis();
				l_diff = l_end_time-l_time_start;
				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"no response from verify ML ",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);				
			}
			String verifiedImage = verifyMLResponse.getImage();
			verifiedImage = verifiedImage.replace("b'", "");
			image.setVerifiedPhotoBase64(verifiedImage);
			Image_ image_ = new Image_();
			image_.setBase64(verifiedImage);
			image_.setImgType("verified");
			image_.setToken(customerDetail.getToken());
			Image img = imgService.updateImageWithSelfie(image,image_);
			if(img==null) {
				long l_end_time = System.currentTimeMillis();
				l_diff = l_end_time-l_time_start;
				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"verified image not saved",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);											
			}
			customerDetail.setUpdateDate(new Date());
			if(verifyMLResponse.isMatch_status()==false) {
				customerDetail.setKycStatus("verification failed");
			}
			else if(!customerDetail.getKycStatus().equals("contingency not verified")){
				customerDetail.setKycStatus("verified. Other doc proof also needed.");
			}
			repo.saveAndFlush(customerDetail);
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,verifyMLResponse,l_diff+" ms") ,HttpStatus.OK);												

		}catch(Exception ex) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"no response from verify ML ",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);							
		}



		//long l_end_time = System.currentTimeMillis();
		//l_diff = l_end_time-l_time_start;
		//return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);				
	}



	private ResponseEntity<CoreResponseHandler> callVerifyNic(String accessToken,Employee customerDetail) {
		long l_diff = 0;
		long l_time_start = System.currentTimeMillis();
		//CustomerDetail customerDetail_ =null;
		SimNicResponse simNicResponse = null;
		RestTemplate restTemplate = new RestTemplate();
		boolean test = false;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", "Bearer "+accessToken);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		JSONObject obj =new JSONObject();
		obj.put("nicNum", customerDetail.getOriginalDocId());
		obj.put("dateOfBirth",  sdf.format(customerDetail.getDob()));
		obj.put("gender", customerDetail.getGender().substring(0, 1).toUpperCase());
		obj.put("userType", "SELF");
		ResponseEntity<SimNicResponse> response = null;
		HttpEntity formEntity = new HttpEntity(obj, headers);
		try {
			response = restTemplate0.exchange("https://simapi.icta.mu/icta/verifyNIC", HttpMethod.POST,
					formEntity, SimNicResponse.class);
			if(response==null) {
				long l_end_time = System.currentTimeMillis();
				l_diff = l_end_time-l_time_start;
				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"sim verify nic response null",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(response!=null) {
				simNicResponse = response.getBody();
				if(simNicResponse==null) {
					long l_end_time = System.currentTimeMillis();
					l_diff = l_end_time-l_time_start;
					return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"sim verify nic response null",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
				}

//				customerDetail_ = custService.findByID(simNicResponse.getNicNum());
//				if(customerDetail_==null) {
//					long l_end_time = System.currentTimeMillis();
//					l_diff = l_end_time-l_time_start;
//					return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no such customer in records",l_diff+" ms"),HttpStatus.NOT_FOUND);
//
//				}
				System.out.println(simNicResponse);

			}
		} catch (HttpStatusCodeException ex) {
			test=true;
			System.out.println("Exception...");
			ex.printStackTrace();
			int statusCode = ex.getStatusCode().value();
			String abcObj =ex.getResponseBodyAsString();
			JSONParser parser = new JSONParser();
			JSONObject obj2 =null;
			try {
				obj2 = (JSONObject)parser.parse(abcObj);
			}catch(Exception ex2){

			}
			if(obj2!=null)
				System.out.println(obj2.toJSONString());
			else
			{
				long l_end_time = System.currentTimeMillis();
				l_diff = l_end_time-l_time_start;
				//return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);				
			}
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			//return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,obj2,l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);
		}


		if(test==false) {
		customerDetail.setCorrelationId(simNicResponse.getCorrelationId());
		//customerDetail = custService.updateCustomer(customerDetail, "");
		}
		
		Image image = customerDetail.getImage();
		String img64Orignal= image.getOriginalPhotoBase64();
		VerifyMLRequest verifyMLRequest = null;
		if(test==false) {
		String simImage = simNicResponse.getPhotograph();
		String firstName = simNicResponse.getFirstName()==null?"":simNicResponse.getFirstName();
		String maidenName = simNicResponse.getMaidenName()==null?"":simNicResponse.getMaidenName();
		String lastName = simNicResponse.getLastName()==null?"":simNicResponse.getLastName();
		
		String name = firstName+" "+maidenName+" "+lastName;
		name=name.trim();
		name=name.replace("  ", " ");
		verifyMLRequest = new VerifyMLRequest();
		verifyMLRequest.setImage(img64Orignal);
		verifyMLRequest.setNimage(simImage);
		verifyMLRequest.setName(name);
		verifyMLRequest.setId(customerDetail.getOriginalDocId());
		verifyMLRequest.setUtype(customerDetail.getResidentType());
		}
		
		if(verifyMLRequest!=null) {
		try {
			VerifyMLResponse verifyMLResponse =  callVerifyML(verifyMLRequest);
			if(verifyMLResponse==null) {
				long l_end_time = System.currentTimeMillis();
				l_diff = l_end_time-l_time_start;
				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"no response from verify ML ",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);				
			}
			String verifiedImage = verifyMLResponse.getImage();
			verifiedImage = verifiedImage.replace("b'", "");
			image.setVerifiedPhotoBase64(verifiedImage);
			Image_ image_ = new Image_();
			image_.setBase64(verifiedImage);
			image_.setImgType("verified");
			image_.setToken(customerDetail.getToken());
			Image img = imgService.updateImageWithSelfie(image,image_);
			if(img==null) {
				long l_end_time = System.currentTimeMillis();
				l_diff = l_end_time-l_time_start;
				return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"verified image not saved",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);											
			}
			customerDetail.setUpdateDate(new Date());
			if(verifyMLResponse.isMatch_status()==false) {
				customerDetail.setKycStatus("verification failed");
			}
			else if(!customerDetail.getKycStatus().equals("contingency not verified")){
				customerDetail.setKycStatus("verified. Other doc proof also needed.");
			}
			repo.saveAndFlush(customerDetail);
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,verifyMLResponse,l_diff+" ms") ,HttpStatus.OK);												

		}catch(Exception ex) {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"no response from verify ML ",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);							
		}
		}
		else {
			long l_end_time = System.currentTimeMillis();
			l_diff = l_end_time-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ML api not called because sim api failed for nic ",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);										
		}



		//long l_end_time = System.currentTimeMillis();
		//l_diff = l_end_time-l_time_start;
		//return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);				
	}






	private VerifyMLResponse callVerifyML(VerifyMLRequest verifyMLRequest) {

		VerifyMLResponse verifyMLResponse = null;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();

		requestBody.add("image", verifyMLRequest.getImage());
		requestBody.add("nimage", verifyMLRequest.getNimage());
		requestBody.add("name", verifyMLRequest.getName());
		requestBody.add("id", verifyMLRequest.getId());
		requestBody.add("utype", verifyMLRequest.getUtype());

		ResponseEntity<VerifyMLResponse> response = null;
		HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
		try {
			
			//http://192.168.0.14:8000/ekyc/verify
//			response = restTemplate.exchange("http://192.168.0.14:8000/ekyc/verify", HttpMethod.POST,
//					formEntity, VerifyMLResponse.class);
			response = restTemplate.exchange("http://192.168.3.2:8000/ekyc/verify", HttpMethod.POST,
					formEntity, VerifyMLResponse.class);
			verifyMLResponse = response.getBody();

		} catch (Exception ex) {
			ex.printStackTrace();
			verifyMLResponse=null;
		}
		return verifyMLResponse;
	}

	
	
	
	
	private String getAccessToken() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		JSONObject obj =new JSONObject();
		obj.put("password", "1s84jBiV6ZpHO6VR5B");
		obj.put("username", "mtml");

		ResponseEntity<SimAccessToken> response = null;
		HttpEntity formEntity = new HttpEntity(obj, headers);
		try {
			response = restTemplate0.exchange("https://simapi.icta.mu/icta/auth/login", HttpMethod.POST,
					formEntity, SimAccessToken.class);
			if(response==null) {
				
				return	null;
			}
			if(response!=null) {

				SimAccessToken simAccessToken = response.getBody();
				if(simAccessToken==null) {
					return null;
				}
				return simAccessToken.getAccessToken();
				//return callSimApi(simAccessToken.getAccessToken(),customerDetail2);
			}
		} catch (HttpStatusCodeException ex) {
			System.out.println("Exception...");
			ex.printStackTrace();
			}
		catch(Exception ex22) {
			System.out.println("###########################");
			ex22.printStackTrace();
		}
		return null;
	}



	@Override
	public ResponseEntity<CoreResponseHandler> emputility(IdRequest3 idRequest) {


		long l_time_start = System.currentTimeMillis();

		String token = idRequest.getToken();
		Employee customerDetail =  findByToken(token);
		if(customerDetail==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no customer in records with this token",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		if(customerDetail.getKycStatus().equals("verified")) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"already verified",l_diff+" ms") ,HttpStatus.OK);												
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"already verified",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
		}
		if(customerDetail.getKycStatus().equals("verification failed")) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			//return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"verification failed last time, first verify yourself. Then, try again later with this document.",l_diff+" ms") ,HttpStatus.OK);												
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"verification failed last time, first verify yourself. Then, try again later with this document.",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												

		}
		if(!customerDetail.getKycStatus().equals("verified. Other doc proof also needed.") && !customerDetail.getKycStatus().equals("contingency not verified") && customerDetail.getResidentType().equals("citizen")) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"already verified",l_diff+" ms") ,HttpStatus.OK);												
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"	. Status found : "+customerDetail.getKycStatus(),l_diff+" ms") ,HttpStatus.BAD_REQUEST);
		}
		
		
		
		String userType = customerDetail.getResidentType();
		OtherDocRequiredCount otherDocRequiredCount = otherDocRepo.findByUserType(userType);


		if(customerDetail.getOtherDocCount()!=null && otherDocRequiredCount!=null && customerDetail.getOtherDocCount().equals(otherDocRequiredCount.getCounts())) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"documents already uploaded.",l_diff+" ms") ,HttpStatus.OK);												
		}


		Document document = customerDetail.getDocument();
		if(document==null) {
			document=new Document();
		}
		Document_ d_ = new Document_();
		d_.setBase64(idRequest.getImage());
		d_.setImgType("emp_utility_org");
		d_.setToken(idRequest.getToken());

		Document document0 = null;
		
		document0 = docService.saveDocument(document, d_);

		if(document0!=null) {

		if(customerDetail.getOtherDocCount()==null || Integer.parseInt(customerDetail.getOtherDocCount())==0) {
			customerDetail.setOtherDocCount("1");
		}
		else {
			int count = Integer.parseInt(customerDetail.getOtherDocCount());
			customerDetail.setOtherDocCount(count+1+"");
		}


		//		String userType = customerDetail.getResidentType();
		//		OtherDocRequiredCount otherDocRequiredCount = otherDocRepo.findByUserType(userType);
		if(otherDocRequiredCount.getCounts().equals(customerDetail.getOtherDocCount())) {
//			VerifiedMsisdn_ vm = new VerifiedMsisdn_();
//			vm.setToken(customerDetail.getToken());
//			SimDetail simDetail =  customerDetail.getSimDetail();
//			if(simDetail!=null)
//			vm.setMsisdn(simDetail.getMsisdn());
//			vm.setSimDetail(simDetail);
//			boolean b = saveToVerifiedMsisdn(vm);
			customerDetail.setKycStatus("verified");			
		}

		}

		//customerDetail.setKycStatus("verified");
		customerDetail.setDocument(document0);
		customerDetail.setUpdateDate(new Date());

		Employee customerDetail0= repo.saveAndFlush(customerDetail);

		if(customerDetail0!=null && customerDetail0.getId()>0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			if(customerDetail0.getKycStatus().equals("verified")) {
				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"document collected. verified.",l_diff+" ms") ,HttpStatus.OK);																
			}
			else
			{
				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"document collected",l_diff+" ms") ,HttpStatus.OK);												
			}
		}


		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);




	}



	@Override
	public ResponseEntity<CoreResponseHandler> empauthorizationletter(IdRequest3 idRequest) {


		long l_time_start = System.currentTimeMillis();

		String token = idRequest.getToken();
		Employee customerDetail =  findByToken(token);
		if(customerDetail==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no customer in records with this token",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		if(customerDetail.getKycStatus().equals("verified")) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"already verified",l_diff+" ms") ,HttpStatus.OK);												
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"already verified",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
		}
		if(customerDetail.getKycStatus().equals("verification failed")) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			//return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"verification failed last time, first verify yourself. Then, try again later with this document.",l_diff+" ms") ,HttpStatus.OK);												
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"verification failed last time, first verify yourself. Then, try again later with this document.",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												

		}
		if(!customerDetail.getKycStatus().equals("verified. Other doc proof also needed.") && !customerDetail.getKycStatus().equals("contingency not verified") && customerDetail.getResidentType().equals("citizen")) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"already verified",l_diff+" ms") ,HttpStatus.OK);												
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"	. Status found : "+customerDetail.getKycStatus(),l_diff+" ms") ,HttpStatus.BAD_REQUEST);
		}
		
		
		
		String userType = customerDetail.getResidentType();
		OtherDocRequiredCount otherDocRequiredCount = otherDocRepo.findByUserType(userType);


		if(customerDetail.getOtherDocCount()!=null && otherDocRequiredCount!=null && customerDetail.getOtherDocCount().equals(otherDocRequiredCount.getCounts())) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"documents already uploaded.",l_diff+" ms") ,HttpStatus.OK);												
		}


		Document document = customerDetail.getDocument();
		if(document==null) {
			document=new Document();
		}
		Document_ d_ = new Document_();
		d_.setBase64(idRequest.getImage());
		d_.setImgType("authorizationLetter");
		d_.setToken(idRequest.getToken());

Document document0 = null;
		
		document0 = docService.saveDocument(document, d_);

		if(document0!=null) {

		if(customerDetail.getOtherDocCount()==null || Integer.parseInt(customerDetail.getOtherDocCount())==0) {
			customerDetail.setOtherDocCount("1");
		}
		else {
			int count = Integer.parseInt(customerDetail.getOtherDocCount());
			customerDetail.setOtherDocCount(count+1+"");
		}


		//		String userType = customerDetail.getResidentType();
		//		OtherDocRequiredCount otherDocRequiredCount = otherDocRepo.findByUserType(userType);
		if(otherDocRequiredCount.getCounts().equals(customerDetail.getOtherDocCount())) {
//			VerifiedMsisdn_ vm = new VerifiedMsisdn_();
//			vm.setToken(customerDetail.getToken());
//			SimDetail simDetail =  customerDetail.getSimDetail();
//			if(simDetail!=null)
//			vm.setMsisdn(simDetail.getMsisdn());
//			vm.setSimDetail(simDetail);
//			boolean b = saveToVerifiedMsisdn(vm);
			customerDetail.setKycStatus("verified");			
		}

		}

		//customerDetail.setKycStatus("verified");
		customerDetail.setDocument(document0);
		customerDetail.setUpdateDate(new Date());

		Employee customerDetail0= repo.saveAndFlush(customerDetail);

		if(customerDetail0!=null && customerDetail0.getId()>0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			if(customerDetail0.getKycStatus().equals("verified")) {
				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"document collected. verified.",l_diff+" ms") ,HttpStatus.OK);																
			}
			else
			{
				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"document collected",l_diff+" ms") ,HttpStatus.OK);												
			}
		}


		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);




	}



	@Override
	public ResponseEntity<CoreResponseHandler> findByOrganisationByAdmin(Organisation organisation) {
		long l_time_start = System.currentTimeMillis();
		List<Employee> list = repo.findByOrganisation(organisation);
		if(list==null || list.size()==0) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no employee found",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,list,l_diff+" ms") ,HttpStatus.OK);
		
	}


	@Override
	public ResponseEntity<CoreResponseHandler> msisdnUpdate(MsisdnUpdate msisdnUpdate) {
		long l_time_start = System.currentTimeMillis();

		if(!msisdnUpdate.getNewCustomer().equals("yes")&& !msisdnUpdate.getNewCustomer().equals("no")) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			//			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"already verified",l_diff+" ms") ,HttpStatus.OK);												
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"newCustomer value only 'yes' or 'no' allowed",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												

		}

		String token = msisdnUpdate.getToken();
		Employee customerDetail =  findByToken(token);
		if(customerDetail==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no employee in records with this token",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}

		SimDetail simDetail =  customerDetail.getSimDetail();
		if(simDetail==null) {
			customerDetail.setNewCustomer(msisdnUpdate.getNewCustomer());
			if(msisdnUpdate.getNewCustomer().equals("yes")) {
				simDetail = new SimDetail();
				simDetail.setMsisdn(msisdnUpdate.getMsisdn());
				simDetail.setImsi(msisdnUpdate.getIccid());
				SimDetail simDetail2 = simRepo.saveAndFlush(simDetail);
				customerDetail.setSimDetail(simDetail2);
				customerDetail.setUpdateDate(new Date());
				customerDetail.setNewCustomer("yes");
				updateCustomer(customerDetail, "");
				long l_end_time = System.currentTimeMillis();
				long l_diff = l_end_time-l_time_start;

				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"updated",l_diff+" ms") ,HttpStatus.OK);

			}
			else {
				simDetail = new SimDetail();
				simDetail.setMsisdn(msisdnUpdate.getMsisdn());
				simDetail.setImsi(msisdnUpdate.getIccid());
				SimDetail simDetail2 = simRepo.saveAndFlush(simDetail);
				customerDetail.setSimDetail(simDetail2);
				customerDetail.setUpdateDate(new Date());
				customerDetail.setNewCustomer("no");
				updateCustomer(customerDetail, "");
				long l_end_time = System.currentTimeMillis();
				long l_diff = l_end_time-l_time_start;

				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"updated",l_diff+" ms") ,HttpStatus.OK);

			}


		}
		else if(simDetail!=null){
			String msisdn = msisdnUpdate.getMsisdn();
			if(simDetail.getMsisdn().contains(msisdn)) {
				long l_end_time = System.currentTimeMillis();
				long l_diff = l_end_time-l_time_start;

				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"updated",l_diff+" ms") ,HttpStatus.OK);

			}
			else {
				customerDetail.setNewCustomer(msisdnUpdate.getNewCustomer());
				customerDetail.setUpdateDate(new Date());
				updateCustomer(customerDetail, "");
				String msisdnSim =  simDetail.getMsisdn();
				msisdnSim = msisdnSim+","+msisdn;
				simDetail.setMsisdn(msisdnSim);

				simRepo.saveAndFlush(simDetail);
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
	public ResponseEntity<CoreResponseHandler> address(String type, String token, String address,String email,String alternateNumber) {
		// TODO Auto-generated method stub
		long l_time_start = System.currentTimeMillis();
		Employee employee = null;
		CustomerDetail customerDetail =null;
		if(type.equalsIgnoreCase("employee") || type.equalsIgnoreCase("tourist")) {
			employee = repo.findByToken(token);
		}
		else {
			customerDetail=custService.findByToken(token);
		}
		
		if(employee==null && customerDetail==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no records with this token",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}

		if(employee!=null) {
			employee.setAddress(address);
			if(email!=null && !email.equals(""))
				employee.setEmail(email);
			if(alternateNumber!=null && alternateNumber.equalsIgnoreCase(""))
				employee.setAlternateNumber(alternateNumber);
			
			employee.setUpdateDate(new Date());
			Employee employee2 = repo.saveAndFlush(employee);
			if(employee2!=null) {
				long l_end_time = System.currentTimeMillis();
				long l_diff = l_end_time-l_time_start;

				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"updated",l_diff+" ms") ,HttpStatus.OK);
				
			}
		}
		
		if(customerDetail!=null) {
			customerDetail.setAddress(address);

			if(email!=null && email.equalsIgnoreCase(""))
				customerDetail.setEmail(email);

			if(alternateNumber!=null && alternateNumber.equalsIgnoreCase(""))
				customerDetail.setAlternateNumber(alternateNumber);
			customerDetail.setUpdateDate(new Date());
			CustomerDetail customerDetail2 = custService.updateCustomer(customerDetail, "");
			if(customerDetail2!=null) {
				long l_end_time = System.currentTimeMillis();
				long l_diff = l_end_time-l_time_start;

				return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"updated",l_diff+" ms") ,HttpStatus.OK);
				
			}
		}
		
		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

	}



	@Autowired
	private EmployeeOrganisationMsisdnRepository employeeOrganisationMsisdnRepository;
	@Autowired
	private OrganisationRepository organisationRepository;
	@Override
	public ResponseEntity<CoreResponseHandler> employeeorganisationmsisdnsave(
			EmployeeOrganisationMsisdn_ employeeOrganisationMsisdn_) {
		long l_time_start = System.currentTimeMillis();

		EmployeeOrganisationMsisdn employeeOrganisationMsisdn = employeeOrganisationMsisdnRepository.findByMsisdn(employeeOrganisationMsisdn_.getMsisdn());
		if(employeeOrganisationMsisdn!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"msisdn already found",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
		}
		Organisation organisation = organisationRepository.findByOrgToken(employeeOrganisationMsisdn_.getOrgToken());
		if(organisation==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no organisation",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		Employee employee = repo.findByToken(employeeOrganisationMsisdn_.getEmpToken());
		if(employee==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no employee",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		EmployeeOrganisationMsisdn employeeOrganisationMsisdn0 = employeeOrganisationMsisdnRepository.findByEmployeeAndOrganisation(employee, organisation);
		if(employeeOrganisationMsisdn0!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"employee is already registered with organisation",l_diff+" ms") ,HttpStatus.BAD_REQUEST);												
		}
		employeeOrganisationMsisdn = new EmployeeOrganisationMsisdn();
		employeeOrganisationMsisdn.setEmployee(employee);
		employeeOrganisationMsisdn.setOrganisation(organisation);
		employeeOrganisationMsisdn.setMsisdn(employeeOrganisationMsisdn_.getMsisdn());
		employeeOrganisationMsisdn.setStatus("taken");
		employeeOrganisationMsisdn.setCreateDate(new Date());
		employeeOrganisationMsisdn.setUpdateDate(new Date());
		EmployeeOrganisationMsisdn employeeOrganisationMsisdn2 = employeeOrganisationMsisdnRepository.saveAndFlush(employeeOrganisationMsisdn);
		if(employeeOrganisationMsisdn2!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;

			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"saved",l_diff+" ms") ,HttpStatus.OK);
			
		}

		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

	}



	@Override
	public ResponseEntity<CoreResponseHandler> employeeorganisationmsisdnupdate(String empToken, String orgToken,
			String msisdn, String status) {
		// TODO Auto-generated method stub
		long l_time_start = System.currentTimeMillis();
		if(!status.equals("taken") && !status.equals("free")) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"allowed status values are : taken, free",l_diff+" ms") ,HttpStatus.BAD_REQUEST);
		}
		Employee employee = repo.findByToken(empToken);
		Organisation organisation = organisationRepository.findByOrgToken(orgToken);
		if(employee==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no employee",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		if(organisation==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no organisation",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		EmployeeOrganisationMsisdn employeeOrganisationMsisdn =  employeeOrganisationMsisdnRepository.findByEmployeeAndOrganisationAndMsisdn(employee, organisation, msisdn);

		if(employeeOrganisationMsisdn==null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no employeeOrganisationMsisdn",l_diff+" ms"),HttpStatus.NOT_FOUND);
			
		}
		
		employeeOrganisationMsisdn.setStatus(status);
		
		employeeOrganisationMsisdn.setEmployee(employee);
		employeeOrganisationMsisdn.setMsisdn(msisdn);
		//employeeOrganisationMsisdn.setOrganisation(organisation);
		
		employeeOrganisationMsisdn.setUpdateDate(new Date());
		EmployeeOrganisationMsisdn employeeOrganisationMsisdn2= employeeOrganisationMsisdnRepository.saveAndFlush(employeeOrganisationMsisdn);
		if(employeeOrganisationMsisdn2!=null) {
			long l_end_time = System.currentTimeMillis();
			long l_diff = l_end_time-l_time_start;

			return new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL,"updated",l_diff+" ms") ,HttpStatus.OK);
			
		}

		long l_end_time = System.currentTimeMillis();
		long l_diff = l_end_time-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,"ERROR",l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);

	}



	
	
	
	
	
	
	@Autowired
	private VerifiedMsisdnRepository vRepo;
	
	
	
	public boolean saveToVerifiedMsisdn(VerifiedMsisdn_ verifiedMsisdn_) {
		VerifiedMsisdn vm = new VerifiedMsisdn();
		vm.setCreateDate(new Date());
		vm.setMsisdn(verifiedMsisdn_.getMsisdn());
		vm.setToken(verifiedMsisdn_.getToken());
		vm.setSimDetail(verifiedMsisdn_.getSimDetail());
		VerifiedMsisdn vm2 =  vRepo.saveAndFlush(vm);
		if(vm2!=null) {
			return true;
		}
		return false;
	}	
	
	
	
	
}
