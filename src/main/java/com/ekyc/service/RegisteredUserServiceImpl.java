package com.ekyc.service;
import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ekyc.model.RegisteredUser;
import com.ekyc.repository.RegisteredUserRepository;

import java.util.function.*;
@Service
public class RegisteredUserServiceImpl {

	@Autowired
	private RegisteredUserRepository repo;
	
	public ResponseEntity<?> findUsers(String user){
		List<RegisteredUser> list = repo.findUsers(user);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
}
