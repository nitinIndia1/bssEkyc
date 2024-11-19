package com.ekyc.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ekyc.model.CustomerDetail;
import com.ekyc.model.Document;
import com.ekyc.model.Organisation;
import com.fasterxml.jackson.annotation.JsonFormat;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer> {

	Organisation findByAdminAndActive(CustomerDetail admin,String active);
	Organisation findByOrgToken(String token);
	Organisation findByOrgTokenAndOrgPin(String orgToken,String orgPin);
	Organisation findByContact(String msisdn);
	Organisation findByAcctId(String msisdn);
	
	List<Organisation> findByAdmin(CustomerDetail admin);
	
	@Query(value = "SELECT * FROM nauruekycservice.organisation where org_type=?1 and create_date like %?2% ;",nativeQuery = true)
	List<Organisation> findByOrgTypeAndDate(String orgType,String date);
	
	List<Organisation> findByOrgType(String orgType);
	
	Organisation findByAdminUserNameAndAdminPassword(String userName,String password);
	
	List<Organisation> findByBrn(String brn);
	
	List<Organisation> findByTouristCertificateNumber(String touristCertificateNumber);
	
	
		
}
