package com.ekyc.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ekyc.model.CustomerDetailsIdType;

public class Test1234 {
public static void main(String[] args) {
	
	
	String idType = "DOC_PRooF";
	if(idType.equals(CustomerDetailsIdType.DOC_PROOF.name())) {
		System.out.println("id type is document");
	}
	else {
		System.out.println("id type is meter number");
	}

	
	
	String str = "prepaid~5g";
	str = str.substring(0, str.indexOf("~"));
	System.out.println(str);
	
	
	Test1234 t = new Test1234();
	JSONObject obj = t.convert("{account_id = 1852 | customer_id = 1 | msisdn = 9123456781 | imsi = 001010617001011 | data_parameter_type = mb | cs_voice_call_seconds = 0 | 4g_data_octets = 0 | 5g_data_octets = 0 | volte_call_seconds = 0 | total_data_octets_available = 20971520 | total_input_data_octets_available = 0 | total_output_data_octets_available = 0 | total_data_octets_consumed = 0 | total_call_seconds_available = 3000 | total_call_seconds_consumed = 0 | total_sms_available = 10 | total_sms_consumed = 0}");
	
	
	System.out.println(obj.toJSONString());
}

public JSONObject convert(String str) {
	
	str = str.replace("|", "\",\"");
	str = str.replace("{", "{\"");
	str =str.replace("}", "\"}");
	str = str.replace("=", "\":\"");
	
	
	JSONParser parser = new JSONParser();
	JSONObject obj =null;
	try {
	obj = (JSONObject) parser.parse(str);
	}catch(Exception ex) {
		ex.printStackTrace();
	}
	
	
	return obj;
}
}
