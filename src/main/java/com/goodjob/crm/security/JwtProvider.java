package com.goodjob.crm.security;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.Setter;

@Setter
@Component
public class JwtProvider {
	
	@Value("${security.jwt.secret}")
	String secretKey;
	
	@Value("${security.jwt.token-validity}")
	Long tokenValidity;
	
	@Value("${security.jwt.token-issuer}")
	String issuer;
	
	Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	public AuthInfo generateAuthInfo(Authentication auth) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		
		Date now = Calendar.getInstance().getTime();
		Date expiresAt = new Date(now.getTime()+tokenValidity);
		String username = auth.getName();
		String[] authorities = auth.getAuthorities()
       		   					 .stream()
       		   					 .map(a -> a.getAuthority())
       		   					 .toArray(String[]::new);
		
		String token = JWT.create()
						  .withIssuer(issuer)
						  .withSubject(username)
						  .withIssuedAt(now)
						  .withExpiresAt(expiresAt)
						  .withArrayClaim("roles", authorities)
						  .sign(algorithm);
		
		AuthInfo authInfo = AuthInfo.builder()
				                    .token(token)
				                    .expiresAt(expiresAt)
				                    .username(username)
				                    .authorities(authorities)
				                    .build();
		return authInfo;
				
	}
	
	public boolean isJWTValid(JWTAuthenticationToken jwt) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer(issuer)
		        .build();
		try {
			 DecodedJWT decodedJWT = verifier.verify(jwt.getJWT());
			 jwt.setDecodedJWT(decodedJWT);
		}catch(JWTVerificationException | IllegalArgumentException ex) {
			logger.info("The following token: " + jwt.getJWT());
			logger.info("is invalid because: " + ex.getMessage());
			return false;
		}
		return true;
	}
	
}
