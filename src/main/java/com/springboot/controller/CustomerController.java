package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Customer;
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

}
