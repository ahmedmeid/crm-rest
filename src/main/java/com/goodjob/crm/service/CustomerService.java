package com.goodjob.crm.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.goodjob.crm.dto.CustomerDto;
import com.querydsl.core.types.Predicate;

public interface CustomerService {

	public List<CustomerDto> getCustomers();
	public List<CustomerDto> getCustomers(Predicate predicate);
	public CustomerDto saveCustomer(CustomerDto newCustomer);
	public CustomerDto getCustomer(Long id);
	public CustomerDto updateCustomer(CustomerDto customer, Long id) throws JsonMappingException;
}
