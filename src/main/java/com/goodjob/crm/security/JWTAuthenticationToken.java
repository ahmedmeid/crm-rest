package com.goodjob.crm.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.auth0.jwt.interfaces.DecodedJWT;


/**
 * 
 * @author ahmedeid
 *
 */
public class JWTAuthenticationToken implements Authentication{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7988744725111588175L;
	private String jwt;
	private DecodedJWT decodedJWT;
	private Boolean isAuthenticated;
	
	public JWTAuthenticationToken(String rawJWT) {
		this.jwt = rawJWT;
		this.isAuthenticated = false;
	}
	
	public void setDecodedJWT(DecodedJWT decJWT) {
		 this.decodedJWT = decJWT;
	}
	
	public String getJWT() {
		return this.jwt;
	}
	
	@Override
	public String getName() {
		return decodedJWT.getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return decodedJWT.getClaim("roles").asList(String.class)
				                            .stream().map(r -> new SimpleGrantedAuthority(r))
				                            .collect(Collectors.toList());
	}

	@Override
	public Object getCredentials() {
		return decodedJWT;
	}

	@Override
	public Object getDetails() {
		return decodedJWT;
	}

	@Override
	public Object getPrincipal() {
		return decodedJWT.getSubject();
	}

	@Override
	public boolean isAuthenticated() {
		return this.isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;	
	}
	

}
