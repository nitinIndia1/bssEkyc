package com.ekyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekyc.model.AdminActivation;
import com.ekyc.model.AgentActivation;
import java.util.*;
public interface AdminActivationRepository extends JpaRepository<AdminActivation, Integer> {

	List<AdminActivation> findByAdminId(String agentId);
	
	List<AdminActivation> findByEmployeeId(String customerId);
	
	
	
	
	
}
