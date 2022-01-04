package com.goodjob.crm.service;

import java.util.List;
import java.util.Optional;

import com.goodjob.crm.dto.ChangePasswordDto;
import com.goodjob.crm.dto.UserDto;

public interface UserService {
	
	List<UserDto> getUsers();
	UserDto getUserbyUsername(String username);
	UserDto getUserbyEmailAddress(String emailAddress);
    UserDto getUserbyUserId(Long user_id);
    Optional<UserDto> createUser(UserDto user);
    void changePassword(ChangePasswordDto changePassword);
}
