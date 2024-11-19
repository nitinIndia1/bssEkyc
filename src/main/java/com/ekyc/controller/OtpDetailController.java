package com.ekyc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ekyc.model.CustomerDetail;
import com.ekyc.model.OtpDetail;
import com.ekyc.model.SimDetail;
import com.ekyc.repository.CustomerDetailRepository;
import com.ekyc.repository.OtpDetailRepository;
import com.ekyc.repository.SimDetailRepository;
import com.ekyc.service.CustomerDetailService;
import com.ekyc.utils.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

//import jakarta.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("/otp")

public class OtpDetailController {

	
	
	@Autowired
	private OtpDetailRepository repository;
	
	
	@Autowired
	private CustomerDetailService custService;
	
	@Autowired
	private CustomerDetailRepository custRepo;
	@Autowired
	private SimDetailRepository simRepo;
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	
	@GetMapping(value="setMpin0/{msisdn}")
	public ResponseEntity<CoreResponseHandler> setMPIN0(@PathVariable(value="msisdn",required=true)String msisdn){
		long l_time_start = System.currentTimeMillis();
		
		OtpDetail otpDetail = repository.findByMsisdn(msisdn);
		int z = 0;
		if(otpDetail!=null) {
			z = repository.updateStatus(msisdn);
		}
		if(z>0) {
			long l_time_end = System.currentTimeMillis();
			long l_diff = l_time_end-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "status change. ready for mpin change",l_diff+" ms"),HttpStatus.OK);
		}
		
		

			long l_time_end = System.currentTimeMillis();
			long l_diff = l_time_end-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed," > Bad request",l_diff+" ms"),HttpStatus.BAD_REQUEST);			
	}
	
	

	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	
	@GetMapping(value="setMpin/{msisdn}/{mpin}")
	public ResponseEntity<CoreResponseHandler> setMPIN(@PathVariable(value="msisdn",required=true)String msisdn,
			@PathVariable(value="mpin",required=true)String mpin){

		System.out.println("1");
		long l_time_start = System.currentTimeMillis();

		
		OtpDetail otpDetail = repository.findByMsisdn(msisdn);
		if(otpDetail==null) {
			long	l_time_end = System.currentTimeMillis();
			long	l_diff = l_time_end-l_time_start;
				
					return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "msisdn not found",l_diff+" ms"),HttpStatus.NOT_FOUND);

		}
		
//		int z = 0;
//		if(otpDetail!=null) {
//			z = repository.updateStatus(msisdn);
//		}
//		if(z>0) {
//
//		}

		
		
		
		
		
		
		List<OtpDetail> list = repository.findMsisdn(msisdn);
		System.out.println("2");
		Random random = new Random();
		int iii = 0;
		try {
		if(list!=null && list.size()>0 && list.get(0).getIsActive().equalsIgnoreCase("true")) {
			iii = repository.updateMpin(mpin,msisdn);
		}
		else {
			return sendOtpAgent(msisdn);
		
		}
	}catch(Exception ex) {
		ex.printStackTrace();
	}
		System.out.println("##### "+iii);
		if(iii>0) {
			long l_time_end = System.currentTimeMillis();
			long l_diff = l_time_end-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "MPIN Set",l_diff+" ms"),HttpStatus.OK);
		}
		else{
			long l_time_end = System.currentTimeMillis();
			long l_diff = l_time_end-l_time_start;
			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed," > Bad request",l_diff+" ms"),HttpStatus.BAD_REQUEST);			
		}		
	
	}
	
	
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	
	 
	@GetMapping(value="sendOtpAgent/{msisdn}")
	public ResponseEntity<CoreResponseHandler> sendOtpAgent(@PathVariable(value="msisdn",required=true)String msisdn){
		System.out.println("1");
		long l_time_start = System.currentTimeMillis();
		String otp = "";
		String strText = "";
		String strSendSms = "";

		List<OtpDetail> list = repository.findMsisdn(msisdn);
		Random random = new Random();
		if(msisdn.contains("6749999999")) {
			otp = "2345";
		}
		else {
		otp = String.format("%04d", random.nextInt(10000));
		}
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MINUTE, 5);
	    Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		int iii = 0;
		try {
		if(list!=null && list.size()>0) {
			iii = repository.updateOtp2(msisdn, otp, strDate, "false");
			strText = "Dear Neotel user enter this OTP "+otp+" for eKYC. Please do not share with anyone. Thank You.";
				strText = URLEncoder.encode(strText, "UTF-8");
			strSendSms = sendSms(msisdn,"2306668",strText); // 218920001472
		}
		else {
			System.out.println("here...");
		iii = repository.insertOtp2(msisdn, otp, strDate, "false");
		strText = "Dear Neotel user enter this OTP "+otp+" for eKYC. Please do not share with anyone. Thank You.";
		
			strText = URLEncoder.encode(strText, "UTF-8");
			System.out.println("here 2");
			strSendSms = sendSms(msisdn,"2306668",strText); // 218920001472
		}
	}catch(Exception ex) {
		System.out.println("here 3");
		ex.printStackTrace();
	}
		System.out.println("##### "+iii);
		if(iii>0) {
			long l_time_end = System.currentTimeMillis();
			long l_diff = l_time_end-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "OTP Sent : "+otp,l_diff+" ms"),HttpStatus.OK);
		}
		long l_time_end = System.currentTimeMillis();
		long l_diff = l_time_end-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed," > Bad request",l_diff+" ms"),HttpStatus.BAD_REQUEST);
	}	
	
	
	

	
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	
	 
	@GetMapping(value="sendOtp/{msisdn}/{lang}/{token}")
	public ResponseEntity<CoreResponseHandler> sendOtp(@PathVariable(value="msisdn",required=true)String msisdn,@PathVariable(value="token",required=true)String token){

		System.out.println("1");
		long l_time_start = System.currentTimeMillis();
		String otp = "";
		String strText = "";
		String strSendSms = "";
		CustomerDetail customerDetail = custService.findByToken(token);
		if(customerDetail==null) {
		long	l_time_end = System.currentTimeMillis();
		long	l_diff = l_time_end-l_time_start;
			
				return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no customer found with this token",l_diff+" ms"),HttpStatus.NOT_FOUND);
		}
		List<OtpDetail> list = repository.findMsisdn(msisdn);
		Random random = new Random();
		if(msisdn.contains("6749999999")) {
			otp = "2345";
		}
		else {
		otp = String.format("%04d", random.nextInt(10000));
		}
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MINUTE, 5);
	    Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		int iii = 0;
		try {
		if(list!=null && list.size()>0) {
			iii = repository.updateOtp(msisdn, otp, strDate, "false",token);
			strText = "Dear Neotel user enter this OTP "+otp+" for eKYC. Please do not share with anyone. Thank You.";
			
				strText = URLEncoder.encode(strText, "UTF-8");
			strSendSms = sendSms(msisdn,"2306668",strText); // 218920001472
		}
		else {
			System.out.println("here...");
		iii = repository.insertOtp(msisdn, otp, strDate, "false",token);
		strText = "Dear Neotel user enter this OTP "+otp+" for eKYC. Please do not share with anyone. Thank You.";
		strText = URLEncoder.encode(strText, "UTF-8");
			System.out.println("here 2");
			strSendSms = sendSms(msisdn,"2306668",strText); // 218920001472
		}
	}catch(Exception ex) {
		System.out.println("here 3");
		ex.printStackTrace();
	}
		System.out.println("##### "+iii);
		if(iii>0) {
			long l_time_end = System.currentTimeMillis();
			long l_diff = l_time_end-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "OTP Sent : "+otp,l_diff+" ms"),HttpStatus.OK);
		}
		long l_time_end = System.currentTimeMillis();
		long l_diff = l_time_end-l_time_start;
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed," > Bad request",l_diff+" ms"),HttpStatus.BAD_REQUEST);
	
	}	
	private String sendSms(String msisdn,String from,String text) {
//		System.out.println("9");
//	String responseStr = "";
//	try {
//		OkHttpClient client = new OkHttpClient();
//		Request request = new Request.Builder()
//				    //http://172.27.89.61:8080/crbt/smpp/api/send-sms?msisdn=23059610020&from=2306667&text=test%20message&configId=1&locale=en%27
//				.url("http://41.223.78.154:8080/crbt/smpp/api/send-sms?msisdn="+msisdn+"&from="+from+"&text="+text+"&configId=2&locale=en")
//				.header("Accept", "application/json")
//				.header("Content-Type", "application/json")
//				.build();
//		Response response = client.newCall(request).execute();
//		responseStr = response.body().string();
//		System.out.println(responseStr);
//		if(responseStr!=null &&  responseStr.contains("SMPP-MT-Response")) {
//			responseStr = "success";
//		}
//		else {
//			responseStr = "failure";
//		}
//}catch(Exception ex) {
//	ex.printStackTrace();
//}
//	return responseStr;
//	
	

		System.out.println("9");
	String responseStr = "";
	try {
//		OkHttpClient client = new OkHttpClient();
//		Request request = new Request.Builder()
//				.url("172.17.1.14:8080/smpp/api/send-sms?msisdn="+msisdn+"&from=121&text="+text+"&configId=1&isFlash=false&locale=en")
//				.header("Accept", "application/json")
//				.header("Content-Type", "application/json")
//				.build();
//		Response response = client.newCall(request).execute();
//		responseStr = response.body().string();
//		System.out.println(responseStr);
//		if(responseStr!=null &&  responseStr.contains("SMPP-MT-Response")) {
//			responseStr = "success";
//		}
//		else {
//			responseStr = "failure";
//		}

//		OkHttpClient client = new OkHttpClient().newBuilder()
//				  .build();
//				okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/plain");
//				RequestBody body = RequestBody.create(mediaType, "");
//				Request request = new Request.Builder()
//				  .url("http://172.17.1.14:8080/smpp/api/send-sms?msisdn="+msisdn+"&from=121&text="+text+"&configId=1&isFlash=false&locale=en")
//				  .method("GET", body)
//				  .build();
//				Response response = client.newCall(request).execute();	
//	
		
		
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		//headers.add("Authorization", "Bearer "+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(null, headers);
		try {
			String url = "http://172.17.1.14:8080/smpp/api/send-sms?msisdn="+msisdn+"&from=121&text="+text+"&configId=1&isFlash=false&locale=en";
			System.out.println(url);
			response = restTemplate.exchange(url , HttpMethod.GET,
					null, String.class);
			
			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(responseStr!=null &&  responseStr.contains("SMPP-MT-Response")) {
					responseStr = "success";
				}
				else {
					responseStr = "failure";
				}
//				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				responseStr = "failure";
			}
			else
			{
				System.out.println("2");
				responseStr = "failure";
				
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
//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),HttpStatus.INTERNAL_SERVER_ERROR);				
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
				System.out.println("inside exception "+obj);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return "failure";

			}
			return "failure";

		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}catch(Exception ex) {
	ex.printStackTrace();
}
	return responseStr;
	
	

}
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	
	@GetMapping(value="verifyOtp/{msisdn}/{otp}/{token}")
	public ResponseEntity<CoreResponseHandler> verifyOtp(@PathVariable(value="msisdn",required=true)String msisdn,
			@PathVariable(value="otp",required=true)String otp,@PathVariable(value="token",required=true)String token,HttpServletResponse response) throws Exception{
		long l_time_start = System.currentTimeMillis();
		long l_time_end = 0;
		long l_diff = 0;
//		if(msisdn.endsWith("282000592") && otp.equals("1234")) {
//			int ii = repository.updateIsActive(msisdn); // fixed : here isAcive is getting true
//			l_time_end = System.currentTimeMillis();
//			l_diff = l_time_end-l_time_start;
//			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "success",l_diff+" ms"),HttpStatus.OK);
//		
//		}
		
		
		CustomerDetail customerDetail = custService.findByToken(token);
		if(customerDetail==null) {
		l_time_end = System.currentTimeMillis();
		l_diff = l_time_end-l_time_start;
			
				return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "no customer found with this token",l_diff+" ms"),HttpStatus.NOT_FOUND);
		}
		
		
		List<OtpDetail> list = repository.verifyOtp(msisdn,otp,token);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		
		if(list!=null && list.size()>0) {
			for(OtpDetail obj:list) {
				String strDate = sdf.format(obj.getOtpValidity());
				Date dtDb = sdf.parse(strDate);
				Date dtNow = new Date();
				String strDtNow = sdf.format(dtNow);
				dtNow = sdf.parse(strDtNow);
				
				long ldtDb = dtDb.getTime();
				long ldtNow = dtNow.getTime();
				if(ldtDb>ldtNow) {
					System.out.println("success within time");
					int ii = repository.updateIsActive(msisdn,token);
					SimDetail simDetail =  customerDetail.getSimDetail();

					if(simDetail==null)
					simDetail = new SimDetail();
					
					simDetail.setMsisdn(msisdn);
					SimDetail simDetail_ =  simRepo.saveAndFlush(simDetail);
					customerDetail.setSimDetail(simDetail_);
					customerDetail.setUpdateDate(new Date());
					custRepo.saveAndFlush(customerDetail);
					l_time_end = System.currentTimeMillis();
					l_diff = l_time_end-l_time_start;
					return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "success",l_diff+" ms"),HttpStatus.OK);
				}
				else {
					System.out.println("failure time over!");
					l_time_end = System.currentTimeMillis();
					l_diff = l_time_end-l_time_start;
					return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "Time Over! try again...",l_diff+" ms"),HttpStatus.NOT_FOUND);
				}
			}
		}
		else {
			l_time_end = System.currentTimeMillis();
			l_diff = l_time_end-l_time_start;
			
				return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "verification failure. Mobile number - OTP mismatch or already active",l_diff+" ms"),HttpStatus.NOT_FOUND);
		}
		l_time_end = System.currentTimeMillis();
		l_diff = l_time_end-l_time_start;
		
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed," > Bad request",l_diff+" ms"),HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	
	@GetMapping(value="verifyOtpAgent/{msisdn}/{otp}")
	public ResponseEntity<CoreResponseHandler> verifyOtpAgent(@PathVariable(value="msisdn",required=true)String msisdn,
			@PathVariable(value="otp",required=true)String otp,HttpServletResponse response) throws Exception{
		long l_time_start = System.currentTimeMillis();
		long l_time_end = 0;
		long l_diff = 0;
//		if(msisdn.endsWith("282000592") && otp.equals("1234")) {
//			int ii = repository.updateIsActive(msisdn); // fixed : here isAcive is getting true
//			l_time_end = System.currentTimeMillis();
//			l_diff = l_time_end-l_time_start;
//			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "success",l_diff+" ms"),HttpStatus.OK);
//		
//		}
		
		

		
		
		List<OtpDetail> list = repository.verifyOtp2(msisdn,otp);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		
		if(list!=null && list.size()>0) {
			for(OtpDetail obj:list) {
				String strDate = sdf.format(obj.getOtpValidity());
				Date dtDb = sdf.parse(strDate);
				Date dtNow = new Date();
				String strDtNow = sdf.format(dtNow);
				dtNow = sdf.parse(strDtNow);
				
				long ldtDb = dtDb.getTime();
				long ldtNow = dtNow.getTime();
				if(ldtDb>ldtNow) {
					System.out.println("success within time");
					int ii = repository.updateIsActive2(msisdn);
					l_time_end = System.currentTimeMillis();
					l_diff = l_time_end-l_time_start;
					return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "success",l_diff+" ms"),HttpStatus.OK);
				}
				else {
					System.out.println("failure time over!");
					l_time_end = System.currentTimeMillis();
					l_diff = l_time_end-l_time_start;
					return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "Time Over! try again...",l_diff+" ms"),HttpStatus.NOT_FOUND);
				}
			}
		}
		else {
			l_time_end = System.currentTimeMillis();
			l_diff = l_time_end-l_time_start;
			
				return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "verification failure. Mobile number - OTP mismatch or already active",l_diff+" ms"),HttpStatus.NOT_FOUND);
		}
		l_time_end = System.currentTimeMillis();
		l_diff = l_time_end-l_time_start;
		
		return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.BAD_REQUEST, ResponseStatusEnum.FAILED, ApplicationResponse.Failed," > Bad request",l_diff+" ms"),HttpStatus.BAD_REQUEST);
		
	}
	
	/*@CrossOrigin(origins = "*", allowedHeaders = "*")*/
	 
	@GetMapping(value="verifyMpin/{msisdn}/{mpin}")
	public ResponseEntity<CoreResponseHandler> verifyMpin(@PathVariable(value="msisdn",required=true)String msisdn,
			@PathVariable(value="mpin",required=true)String mpin,HttpServletResponse response) throws Exception{
		long l_time_start = System.currentTimeMillis();
		long l_time_end = 0;
		long l_diff = 0;
		List<OtpDetail> list = repository.verifyMpin(msisdn,mpin);
		
		if(list!=null && list.size()>0) {
			l_time_end = System.currentTimeMillis();
			l_diff = l_time_end-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.OK, ResponseStatusEnum.SUCCESSFUL, ApplicationResponse.SUCCESSFUL, "success",l_diff+" ms"),HttpStatus.OK);
		}
		else {
			System.out.println("failure time over!");
			l_time_end = System.currentTimeMillis();
			l_diff = l_time_end-l_time_start;
			return new	ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(HttpStatus.NOT_FOUND, ResponseStatusEnum.FAILED, ApplicationResponse.Failed, "Incorrect mpin. Try again...",l_diff+" ms"),HttpStatus.NOT_FOUND);
		}

	}

	

}