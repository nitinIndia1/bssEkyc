package com.ekyc.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ekyc.beans.CustomCustomerDTO;
import com.ekyc.beans.CustomerObject;
import com.ekyc.model.CustomerDetail;
import com.ekyc.model.Document;

public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, Integer>{

	
	
	@Query(value = "SELECT * FROM nauruekycservice.customer_details where create_date like %?1% ;",nativeQuery = true)
	List<CustomerDetail> findByCreateDatess(String date);
	
	
	
	@Query(value = "SELECT * FROM nauruekycservice.customer_details where create_date BETWEEN ?1 AND ?2 ;", nativeQuery = true)
	List<CustomerDetail> findByCreateDatess(String date, String date2);

	

	@Query(value = "select * from nauruekycservice.customer_details where crm_info like %?1% ;",nativeQuery = true)
	CustomerDetail findByMsisdn(String msisdn);
	
	
	CustomerDetail findByToken(String token);
	
	@Query(value = "select * from customer_details where id = ?1 order by customerId desc limit 1;",nativeQuery = true)
	
	CustomerDetail findByID(String id);
	
	@Query(value = "select * from customer_details where kyc_status like '%contingency%' limit 5;", nativeQuery = true)
	  List<CustomerDetail> findTest();
	
	CustomerDetail findByDocument(Document document);

	
	@Query(value = "SELECT first_name as firstName,maiden_name as maidenName,last_name as lastName,token, update_date as updateDate, id,kyc_status as kycStatus,gender,dob FROM nauruekycservice.customer_details where id=?1 order by customerId desc limit 1;", nativeQuery = true)
	Optional<CustomerObject> findById2(String id);


	
//	@Query(value = "SELECT first_name as firstName, last_name as lastName FROM customer_details where id=?1 order by customerId desc limit 1", nativeQuery = true)
//	CustomerObject findById2(String id);

	
	@Query(value = "SELECT * FROM nauruekycservice.customer_details where first_name like ?1% or token like ?1% ;",nativeQuery = true)
	List<CustomerDetail> findByTokenOrName(String value);
	
	
	@Query(value = "select count(*) from customer_details where create_date between ?1 and ?2  and token not like 'N%' and kyc_status='verified' ;",nativeQuery = true)
	long findTotalEkcyCountsDatewise(String from,String to);
	
	@Query(value = "select count(*) from customer_details where create_date between ?1 and ?2  and token like 'N%' ;",nativeQuery = true)
	long findTotalNOEkcyCountsDatewise(String from,String to);
	
	@Query(value = "select count(*) from customer_details  where kyc_status='verified' and create_date > '2025-01-25 00:00:00' ;",nativeQuery = true)
	long findTotalEkcyCounts();
	
	
	@Query(value = "select count(*) from customer_details  where token like 'N%' and create_date > '2025-01-25 00:00:00' ;",nativeQuery = true)
	long findTotalNOEkcyCounts();
	
}
