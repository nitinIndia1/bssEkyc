package com.ekyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekyc.model.Employee;
import com.ekyc.model.EmployeeOrganisationMsisdn;
import com.ekyc.model.Organisation;

public interface EmployeeOrganisationMsisdnRepository extends JpaRepository<EmployeeOrganisationMsisdn, Integer> {

	
	
	EmployeeOrganisationMsisdn findByMsisdn(String msisdn);
	
	EmployeeOrganisationMsisdn findByEmployeeAndOrganisationAndMsisdn(Employee employee,Organisation organisation,String msisdn);
	
	EmployeeOrganisationMsisdn findByEmployeeAndOrganisation(Employee employee,Organisation organisation);
	
}
