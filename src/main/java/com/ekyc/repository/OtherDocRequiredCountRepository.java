package com.ekyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekyc.model.OtherDocRequiredCount;

public interface OtherDocRequiredCountRepository extends JpaRepository<OtherDocRequiredCount, Integer> {

	OtherDocRequiredCount findByUserType(String userType);
	
	
	
	
}
