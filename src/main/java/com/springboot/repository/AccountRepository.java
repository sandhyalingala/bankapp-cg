package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.springboot.model.Account;

@Component
public interface AccountRepository extends JpaRepository<Account, Long>{

	@Query("select a.balance from Account a where a.accountNumber=?1")
	 double getBalanceByAccountNumber(String accountNumber) ;

}
