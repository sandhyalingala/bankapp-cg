package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.springboot.model.Help;

@Component
public interface HelpRepository extends JpaRepository<Help,Long>{

}
