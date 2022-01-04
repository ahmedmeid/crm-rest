package com.goodjob.crm.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.goodjob.crm.dto.ChangePasswordDto;
import com.goodjob.crm.dto.UserDto;
import com.goodjob.crm.model.AppUser;
import com.goodjob.crm.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepo;
	private ModelMapper modelMapper;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository repo, ModelMapper mapper, BCryptPasswordEncoder encoder){
		this.userRepo = repo;
		this.modelMapper = mapper;
		this.passwordEncoder = encoder;
	}
	@Override
	public List<UserDto> getUsers() {
		List<AppUser> users = userRepo.findAll();
		return StreamSupport.stream(users.spliterator(), false)
				            .map(this::convertEntityToDto)
				            .collect(Collectors.toList());
	}

	@Override
	public UserDto getUserbyUsername(String username) {
		return convertEntityToDto(userRepo.findByUsername(username));
	}

	@Override
	public UserDto getUserbyEmailAddress(String emailAddress) {
		return convertEntityToDto(userRepo.findByEmailAddress(emailAddress));
	}

	@Override
	public UserDto getUserbyUserId(Long user_id) {
		return convertEntityToDto(userRepo.getOne(user_id));
	}
	
	private UserDto convertEntityToDto(AppUser source) {
		return modelMapper.map(source, UserDto.class);
	}

	@Override
	public Optional<UserDto> createUser(UserDto userDto) {
		AppUser user = convertDtoToEntity(userDto);
		AppUser x =  userRepo.save(user);
		return Optional.of(convertEntityToDto(x));
	}
	
	private AppUser convertDtoToEntity(UserDto source) {
		AppUser user = modelMapper.map(source, AppUser.class);
		//ToDo to generate random password and send it to user by email
		user.setPassword(passwordEncoder.encode("defaultPassword"));
		return user;
	}

	@Override
	public void changePassword(ChangePasswordDto changePassword) {
		AppUser user = userRepo.findByUsername(changePassword.getUsername());
		user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
		userRepo.save(user);
	}
	
	

}
