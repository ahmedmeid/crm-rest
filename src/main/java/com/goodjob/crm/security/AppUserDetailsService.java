package com.goodjob.crm.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.goodjob.crm.model.AppUser;
import com.goodjob.crm.repository.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepository.findByUsername(username);
		UserDetails details = org.springframework.security.core.userdetails.User.builder()
				                                                                .username(user.getUsername())
				                                                                .password(user.getPassword())
				                                                                .authorities(user.getRoles().stream()
				                                                                		         .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
				                                                                		         .collect(Collectors.toList()))
				                                                                .accountLocked(false)
				                                                                .accountExpired(false)
				                                                                .build();				                                                                				                                                                		     				                                                                		    																			
		return details;
	}

}
