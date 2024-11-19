package com.ekyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekyc.model.AgentActivation;
import java.util.*;
public interface AgentActivationRepository extends JpaRepository<AgentActivation, Integer> {

	List<AgentActivation> findByAgentId(String agentId);
	
	List<AgentActivation> findByCustomerId(String customerId);
	
	
	
	
	
}
