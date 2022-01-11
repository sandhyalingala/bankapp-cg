package com.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig {
 
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		//PasswordEncoder passwordEncoder ;[ref can only be created for interface]
		PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();//BCryptPasswordEncoder is inbuilt class[author told to use its object if we want to use passwordEncoder]
		return passwordEncoder;//passwordEncoder is used to convert plain text into encoded format so spring accepts it
	}
}
