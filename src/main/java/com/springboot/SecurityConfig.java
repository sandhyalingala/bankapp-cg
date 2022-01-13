package com.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.service.MyUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {//spingframework class//SecurityConfig [our class]
	
	@Autowired
	 private MyUserDetailsService myUserDetailsService;
	
 
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		//PasswordEncoder passwordEncoder ;[ref can only be created for interface]
		PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();//BCryptPasswordEncoder is inbuilt class[author told to use its object if we want to use passwordEncoder]
		return passwordEncoder;//passwordEncoder is used to convert plain text into encoded format so spring accepts it
	}
	
	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getDBAuthenticator());
		}
	
	@Override
		protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/customer").permitAll()//API path
			.antMatchers("/user").authenticated()
			.antMatchers("/transfer").authenticated()
			.antMatchers("/statement/{startDate}/{endDate}").authenticated()
			.antMatchers("/deposit").authenticated()
			.antMatchers("/balance").authenticated()
			.antMatchers("/customer-details").authenticated()
			.antMatchers("/help").authenticated()
			.antMatchers("/help/**").authenticated()
			.anyRequest()  
			.permitAll()
			.and()
			.httpBasic()
			.and()
			.csrf().disable();
		}
	
	public DaoAuthenticationProvider getDBAuthenticator() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(myUserDetailsService);
		auth.setPasswordEncoder(getPasswordEncoder());
		return auth;
	}
}
