package com.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.model.Role;
import com.springboot.model.UserInfo;
import com.springboot.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {//security core

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		//got to repository check whether given username and password is correct
		 UserInfo myUser=userRepository.findByUsername(username);//findByUsername() - not present in repository of spring framework so creste that method in UserRepository
		 Role role = myUser.getRole();
		
		 List<GrantedAuthority> authorities= new ArrayList<>();
		 SimpleGrantedAuthority sga= new SimpleGrantedAuthority(role.getName());//attaching role to authorities of spring
		 authorities.add(sga);
		 
		 
		 
		return new User(myUser.getUsername(),myUser.getPassword(),authorities);//User -spring User
	}

}
