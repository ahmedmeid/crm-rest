package com.goodjob.crm.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
	
	String username;	
	String oldPassword;
	String newPassword;

}
