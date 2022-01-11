package com.springboot.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.model.Account;
import com.springboot.model.Customer;
import com.springboot.model.Role;
import com.springboot.model.UserInfo;
import com.springboot.repository.CustomerRepository;


@Service  
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	public Customer postCustomer(Customer customer) {
		//generate 10 digit account number(random)
		Random random=new Random();//util package
		int temp=random.nextInt(99999);//5 digit random number
		String accountNo = "MSB91"+ temp;
		Account account = new Account();
		//prepare account with accountNo,initial balance,dateOfOpening
		account.setAccountNumber(accountNo);
		account.setBalance(100);
		account.setDateOfOpening(new Date());
		customer.setAccount(account);
		
		//encode the given password and attach to customer
		String encodedPass=passwordEncoder.encode(customer.getUserInfo().getPassword());
		UserInfo user = new UserInfo(); 
		user.setUsername(customer.getUserInfo().getUsername());
		user.setPassword(encodedPass);
		
		//create a role and insert in DB
		Role role = new Role();
		role.setName("USER");
		
		user.setRole(role);
		
		customer.setUserInfo(user);
		return customerRepository.save(customer);
	}
	public List<Customer> findAll() {
		
		return customerRepository.findAll();
	}

}
