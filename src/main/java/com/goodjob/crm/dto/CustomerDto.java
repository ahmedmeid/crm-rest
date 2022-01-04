package com.goodjob.crm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {
	
	Long id;
	
	String firstName;
	
	String lastName;
	
	String homeAddress;
	
	String area;
	
	String city;
	
	String district;
	
	String postalCode;
	
	String emailAddress;
	
	String mobileNo;

}
