package com.goodjob.crm.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodjob.crm.dto.CustomerDto;
import com.goodjob.crm.model.Customer;
import com.goodjob.crm.repository.CustomerRepository;
import com.querydsl.core.types.Predicate;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
    ObjectMapper objectMapper;

	@Override
	public List<CustomerDto> getCustomers() {
		Iterable<Customer> customers = customerRepo.findAll();
		return StreamSupport.stream(customers.spliterator(), false)
		          .map(this::convertToDto)
		          .collect(Collectors.toList());
		
	}

	@Override
	public List<CustomerDto> getCustomers(Predicate predicate) {		
		List<Customer> customers = (List<Customer>) customerRepo.findAll(predicate);
		return StreamSupport.stream(customers.spliterator(), false)
		          .map(this::convertToDto)
		          .collect(Collectors.toList());
	}

	@Override
	public CustomerDto saveCustomer(CustomerDto customer) {
		
		Customer c = modelMapper.map(customer, Customer.class);
		Customer e = customerRepo.save(c);
		return modelMapper.map(e, CustomerDto.class);
		
	}

	@Override
	public CustomerDto getCustomer(Long id) {
		Optional<Customer> c = customerRepo.findById(id);
		if(!c.isPresent()) {
			return null;
		}
		CustomerDto dto = modelMapper.map(c.get(), CustomerDto.class);
		return dto;
	}
	
	@Override
	public CustomerDto updateCustomer(CustomerDto customer, Long id) throws JsonMappingException {
		Customer c = customerRepo.getById(id);
		if(c == null)
			return null;
		objectMapper.updateValue(c, customer);
		Customer e = customerRepo.save(c);
		CustomerDto dto = modelMapper.map(e, CustomerDto.class);
		return dto;
	}
	
	private CustomerDto convertToDto(Customer customer) {
		return modelMapper.map(customer, CustomerDto.class);
	}
	
	

}
