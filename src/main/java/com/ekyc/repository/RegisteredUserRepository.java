package com.ekyc.repository;
import java.util.*;
import java.util.stream.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ekyc.model.Organisation;
import com.ekyc.model.RegisteredUser;

import java.util.function.*;
public interface RegisteredUserRepository
		extends
			JpaRepository<RegisteredUser, Integer> {

	
	@Query(value = "SELECT * FROM nauruekycservice.registered_user where Registered_Name like ?1% ;",nativeQuery = true)
	List<RegisteredUser> findUsers(String user);
	
	
}
