package com.goodjob.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.goodjob.crm.dto.CustomerDto;
import com.goodjob.crm.model.Customer;
import com.goodjob.crm.service.CustomerService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

	private CustomerService customerService;

	@Autowired
	public CustomerRestController(CustomerService service){
		this.customerService = service;
	}
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Iterable<CustomerDto>> getCustomers(@QuerydslPredicate(root = Customer.class) Predicate predicate){
		
		List<CustomerDto> customers = customerService.getCustomers(predicate);
		if(customers.size() == 0) {
			return new ResponseEntity<Iterable<CustomerDto>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Iterable<CustomerDto>>(customers, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CustomerDto> newCustomer(@RequestBody CustomerDto customer) {		
		CustomerDto c = customerService.saveCustomer(customer);	
		return new ResponseEntity<CustomerDto>(c, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {
		CustomerDto customer =  customerService.getCustomer(id);
		if(customer == null) {
			return new ResponseEntity<CustomerDto>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CustomerDto>(customer, HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customer, @PathVariable Long id) throws JsonMappingException {
		CustomerDto e = customerService.updateCustomer(customer, id);
		if(e == null) {
			return new ResponseEntity<CustomerDto>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CustomerDto>(e, HttpStatus.OK);
		
	}

}
