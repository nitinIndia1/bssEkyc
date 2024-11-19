package com.ekyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekyc.model.SimDetail;

public interface SimDetailRepository extends JpaRepository<SimDetail, Integer> {

	
	List<SimDetail> findByMsisdnContaining(String msisdn);
	
	
}
