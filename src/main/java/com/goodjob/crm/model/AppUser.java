package com.goodjob.crm.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="app_user")
public class AppUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name="email_address")
	String emailAddress;
	
	@Column(name="first_name")
	String firstName;
	
	@Column(name="last_name")
	String lastName;
	
	@Column(name="username")
	String username;
	
	@Column(name="password")
	String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="user_role", 
			   joinColumns = {@JoinColumn(name="user_id")}, 
			   inverseJoinColumns = {@JoinColumn(name="role_id")})
	List<SecurityRole> roles;

}
