package com.goodjob.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goodjob.crm.model.SecurityRole;

public interface RoleRepository extends JpaRepository<SecurityRole, Long>{

}
