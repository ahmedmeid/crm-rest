package com.goodjob.crm.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.web.filter.GenericFilterBean;

public class JWTAuthenticationFilter extends GenericFilterBean {

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	AuthenticationConverter authConverter;
	
	@Autowired
	JwtProvider jwtProvider;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		Authentication token = authConverter.convert((HttpServletRequest) req);
		if (token != null && jwtProvider.isJWTValid((JWTAuthenticationToken) token)) {
			Authentication authentication = authManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(req, res);
	}
}
