package com.goodjob.crm.security;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthInfo {

	String username;

	@JsonIgnore
	String token;
	
	Date expiresAt;

	String[] authorities;

}
