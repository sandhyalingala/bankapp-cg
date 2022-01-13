package com.springboot.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.model.Account;
import com.springboot.model.Help;
import com.springboot.model.Transaction;

@Component
public interface TransactionRepository extends JpaRepository<Transaction,Long>{

	@Query("select t from Transaction t where t.accountFrom=?1 OR t.accountTo=?1 ORDER BY t.dateOfTransaction DESC")
	List<Transaction> fetchTransactionsByAccountNumber(String accountNumber);
	
	@Transactional
	@Modifying
	@Query ("update Account a SET a.balance=a.balance+?1")
	void depositAmount(double amount);

	


	@Query("select a from Account a where a.accountNumber=?1")
	Account accountDetails(String accountNumber);

	@Query("select h from Help h where h.id=?1")
	Help getQnA(Long id);

	

	

	
	
	
	
}
