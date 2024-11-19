package com.ekyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.ekyc.model.OrgMsisdn;
import com.ekyc.model.Organisation;

public interface OrgMsisdnRepository extends JpaRepository<OrgMsisdn, Integer> {

	
	List<OrgMsisdn> findByOrganisation(Organisation organisation);
	
	OrgMsisdn findByOrganisationAndMsisdn(Organisation organisation,String msisdn);
}
