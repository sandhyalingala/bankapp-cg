package com.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.model.Account;
import com.springboot.model.Help;
import com.springboot.model.Transaction;
import com.springboot.repository.AccountRepository;
import com.springboot.repository.HelpRepository;
import com.springboot.repository.TransactionRepository;
import com.springboot.repository.UserRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private HelpRepository helpRepository;
	public String fetchFromAccountNumber(String username) {
		  return userRepository.fetchFromAccountNumber( username);
		
		
	}

	public void updateBalance(String fromAccountNumber, double amount) {
		 userRepository.updateBalance(fromAccountNumber,amount);
		
	}

	public void creditBalance(String toAccountNumber, double amount) {
		 userRepository.creditBalance(toAccountNumber,amount);
		
	}

	public Transaction saveTransaction(Transaction transaction) {
		
		return transactionRepository.save(transaction);
	}

	public List<Transaction> fetchTransactionsByAccountNumber(String accountNumber) {
		
		return transactionRepository.fetchTransactionsByAccountNumber(accountNumber);
	}

	
	public void depositAmount(double amount) {
		 transactionRepository.depositAmount( amount);
		
	}
	public Account accountDetails(String accountNumber) {
		return transactionRepository.accountDetails(accountNumber);
		
	}

	public double getBalanceByAccountNumber(String accountNumber) {
		
		return accountRepository.getBalanceByAccountNumber(accountNumber) ;
	}

	public Help postQuestion(Help help) {
		// TODO Auto-generated method stub
		help.setQuestion(help.getQuestion());
		help.setAnswer(help.getAnswer());
		return helpRepository.save(help);
	}

	public Help getQnA(Long id) {
		
		return  transactionRepository.getQnA(id);
	}

	

}
