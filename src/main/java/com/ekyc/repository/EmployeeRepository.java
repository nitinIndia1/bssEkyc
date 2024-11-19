package com.ekyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ekyc.model.CustomerDetail;
import com.ekyc.model.Employee;
import com.ekyc.model.Organisation;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	
	
@Query(value = "select * from employee where document_id = ?1 order by id desc limit 1;",nativeQuery = true)
	
	Employee findByID(String id);
	Employee findByToken(String token);
	List<Employee> findByOrganisation(Organisation organisation);
}
