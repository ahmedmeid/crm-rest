package com.goodjob.crm.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class JWTAuthenticationConverter implements AuthenticationConverter{
	
	Logger logger = LoggerFactory.getLogger(JWTAuthenticationConverter.class);

	@Override
	public Authentication convert(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies!= null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					return new JWTAuthenticationToken(cookie.getValue());
				}
			}
		}
		return null;
	}

}
