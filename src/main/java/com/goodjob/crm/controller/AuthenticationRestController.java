package com.goodjob.crm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goodjob.crm.dto.ChangePasswordDto;
import com.goodjob.crm.dto.LoginDto;
import com.goodjob.crm.security.AuthInfo;
import com.goodjob.crm.security.JwtProvider;
import com.goodjob.crm.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
@ResponseBody
public class AuthenticationRestController {

	private JwtProvider jwtProvider;
	private UserService userService;
	private AuthenticationManager authManager;

	@Autowired
	public AuthenticationRestController(JwtProvider provider, UserService service, AuthenticationManager manager){
		this.jwtProvider = provider;
		this.userService = service;
		this.authManager = manager;
	}
	
	@PostMapping("/login")
	ResponseEntity<AuthInfo> login(@RequestBody LoginDto login, HttpServletResponse response){
		Authentication token = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
		Authentication auth = authManager.authenticate(token);
		AuthInfo authInfo = jwtProvider.generateAuthInfo(auth);
		Cookie authCookie = new Cookie("token", authInfo.getToken());
		authCookie.setHttpOnly(Boolean.TRUE);
		authCookie.setMaxAge(Integer.MAX_VALUE);
		authCookie.setSecure(false);
		response.addCookie(authCookie);
		return new ResponseEntity<AuthInfo>(authInfo, HttpStatus.OK);
	}
	
	@PostMapping("/change-password")
	ResponseEntity<Map<String, String>> changePassword(@RequestBody ChangePasswordDto changePassword){
		Authentication token = new UsernamePasswordAuthenticationToken(changePassword.getUsername(), changePassword.getOldPassword());
		authManager.authenticate(token);
		userService.changePassword(changePassword);
		Map<String,String> result = new HashMap<>();
		result.put("message", "password has been changed successfully");
		return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
	}
	
	@PostMapping("/logout")
	ResponseEntity<Map<String, String>> logout(){
		
		return new ResponseEntity<Map<String,String>>(new HashMap<>(), HttpStatus.OK);		
	}

}
