package com.goodjob.crm.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_sequence")
	@SequenceGenerator(name="app_sequence", allocationSize = 1)
	Long id;
	
	@Column(name="first_name")
	String firstName;
	
	@Column(name="last_name")
	String lastName;
	
	@Column(name="home_address")
	String homeAddress;
	
	@Column(name="address_area")
	String area;
	
	@Column(name="address_city")
	String city;
	
	@Column(name="address_district")
	String district;
	
	@Column(name="address_postal_code")
	String postalCode;
	
	@Column(name="email_address")
	String emailAddress;
	
	@Column(name="mobile_number")
	String mobileNo;

}
