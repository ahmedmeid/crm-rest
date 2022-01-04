package com.goodjob.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.goodjob.crm.dto.UserDto;
import com.goodjob.crm.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private UserService userService;

	@Autowired
	public UserRestController(UserService service){
		this.userService = service;
	}
	
	@GetMapping
	@ResponseBody
	public List<UserDto> getUsers(){
		return userService.getUsers();
	}
	
	@PostMapping
	@ResponseBody
	public UserDto createUser(@RequestBody UserDto dto) {
		return userService.createUser(dto).get();
	}

}
