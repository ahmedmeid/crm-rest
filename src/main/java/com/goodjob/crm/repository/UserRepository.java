package com.goodjob.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goodjob.crm.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long>{
	
	AppUser findByUsername(String username);
	AppUser findByEmailAddress(String emailAddress);
}
