package com.goodjob.crm.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationException extends AuthenticationException{

	public JWTAuthenticationException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 995977184481298887L;
	
}
