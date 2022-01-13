package com.springboot.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Customer;
import com.springboot.model.UserInfo;
import com.springboot.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customer")
	public Customer postCustomer(@RequestBody Customer customer) {
		System.out.println(customer);
		return customerService.postCustomer(customer);
	}
	
	@GetMapping("/customer")
	public List<Customer> getAll() {
		return customerService.findAll();
	}
	 
	@GetMapping("/user")//step:3
	public UserInfo getUser(Principal principal ) {
		UserInfo user=customerService.getUserByName(principal.getName());
		return user;
		
	}
	
	/*
	 * for edit
	 * 2 apis
	 * 
	 * API1:
	 * when screen is shown
	 * Before this api call getCustomer by username
	 * populate edit form with existing customer details
	 * 
	 * EDIT 
	 * API2:
	 * after edit button is pressed
	 * 
	 * step1:
	 * fetch username from principle
	 * 
	 * step2:
	 * update customer table with new address and city
	 * refer to update query from UserRepository
	 * 
	 * return updated Customer object
	
	 */
	//API1:
	@GetMapping("/customer-details")
	public Customer getCustomerByName(Principal principal) {
		return customerService.getCustomerByName(principal.getName());
		
	
	
}
	//API2:
	@PutMapping("/edit-customer")
	public Customer updateCustomer(@RequestBody Customer customer,Principal principal) {
		
		return null;
	}
	
	
}
