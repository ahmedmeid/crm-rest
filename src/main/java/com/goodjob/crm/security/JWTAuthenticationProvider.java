package com.goodjob.crm.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import lombok.Setter;

/**
 * 
 * @author ahmedeid
 *
 */
@Setter
public class JWTAuthenticationProvider implements AuthenticationProvider{
	
	@Value("${security.jwt.secret}")
	String secretKey;
	
	@Value("${security.jwt.token-issuer}")
	String issuer;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if(authentication.getCredentials() != null)
			authentication.setAuthenticated(Boolean.TRUE);
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		if(JWTAuthenticationToken.class.isAssignableFrom(authentication)) {
			return true;
		}
		return false;
	}

}
