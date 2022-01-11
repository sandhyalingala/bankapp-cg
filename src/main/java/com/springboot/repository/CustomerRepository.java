package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.springboot.model.Customer;

@Component
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
