package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.springboot.model.Role;

@Component
public interface RoleRepository extends JpaRepository<Role,Long>{

}
