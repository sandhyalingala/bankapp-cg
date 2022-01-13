package com.springboot.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Account;
import com.springboot.model.Customer;
import com.springboot.model.Help;
import com.springboot.model.Transaction;
import com.springboot.model.TransferInfo;
import com.springboot.service.TransactionService;

@RestController
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransferInfo  transfer;
	
	@PostMapping("/transfer")
	public Transaction performTransfer(Principal principal,@RequestBody TransferInfo transfer) {
		String username=principal.getName();
		/*
		 * STEP:1
		 * 1.1 Fetch AccountNumber(fromAccountNo.) using username when user logged IN
		 * 
		 * STEP:2
		 * 2.1 DEBIT amount from user Account(fromAccountNo.) /update the balance
		 * 2.2 CREDIT amount into Beneficiary Account(toAccountNo.)/ update the balance
		 * 
		 * STEP:3
		 * 3.1 INSERT the entry of transfer into transaction table
		 * 
		 */
		String fromAccountNumber=transactionService.fetchFromAccountNumber(username);
		System.out.println(fromAccountNumber);
		transactionService.updateBalance(fromAccountNumber,transfer.getAmount());
		transactionService.creditBalance(transfer.getToAccountNumber(),transfer.getAmount());
		Transaction transaction= new Transaction();
		transaction.setAccountFrom(fromAccountNumber);
		transaction.setAccountTo(transfer.getToAccountNumber());
		transaction.setAmount(transfer.getAmount());
		transaction.setOperationType("TRANSFER");
		transaction.setDateOfTransaction(new Date());
		return transactionService.saveTransaction(transaction);
		
	}
	
	@GetMapping("/statement/{startDate}/{endDate}")
	public List<Transaction> generateStatement(Principal principal, 
			@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, 
			@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		 
		String username=principal.getName(); 
		
		/* 
		 * Step 1: extract account number based on username
		 */
		
		/*
		 * Step 2:  
		 * Fetch transactions for above account number
		 * this number should be either in  account_from or account_to
		 * this will give me List<Tansaction>
		 */
		
		/*
		 * Step 3: 
		 * From List<Transaction> of step-2, I will filter this based on 
		 * startDate and endDate given. 
		 * return this List<Transaction>
		 */
		
		//Step 1
		String accountNumber = transactionService.fetchFromAccountNumber(username);
		
		//Step 2
		List<Transaction> list = transactionService.fetchTransactionsByAccountNumber(accountNumber);
		
		//Step 3
		try {
			//convert LocalDate to Date 
			Date startDateToDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate.toString());
			Date endDateToDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate.toString());
			//2022-01-10
			list = list.parallelStream()
					.filter(t-> t.getDateOfTransaction().compareTo(startDateToDate) >= 0)
					.filter(t-> t.getDateOfTransaction().compareTo(endDateToDate) <= 0)
					.collect(Collectors.toList());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return list; 
	}
	
	@PostMapping("/deposit")
	public Account processDeposit(Principal principal,@RequestBody TransferInfo transfer){
		/*
		 * Deposit
		 * 
		 * step 1:
		 * fetch the account numbar based on username
		 * 
		 * step2:
		 * update the balance of user and add the amount to the balance .
		 * 
		 * step 3:
		 * add an entry in transaction table
		 * operation _type =DEPOSIT
		 * accouunt_from = account_to =accountNumber(from step 1)
		 * 
		 * return account details
		 */
		
		String username=principal.getName();
		String accountNumber=transactionService.fetchFromAccountNumber(username);
		transactionService.depositAmount(transfer.getAmount());
		Transaction transaction= new Transaction();
		
		transaction.setAccountFrom(accountNumber);
		transaction.setAccountTo(accountNumber);
		transaction.setAmount(transfer.getAmount());
		transaction.setOperationType("DEPOSIT");
		transaction.setDateOfTransaction(new Date());
		 transactionService.saveTransaction(transaction);
		Account acc= transactionService.accountDetails(accountNumber);
		return acc;
		
	}
	
	@GetMapping("/balance")
	public double getBalanceByAccountNumber(Principal principal) {
		String username=principal.getName();
		String accountNumber=transactionService.fetchFromAccountNumber(username);
		double b= transactionService.getBalanceByAccountNumber(accountNumber);
		System.out.println(b);
		return b;
		
	}
	
	 @PostMapping("/help")
	 public Help postQuestion(@RequestBody Help help) {
		return transactionService.postQuestion(help);
		
	}
	
	@GetMapping("/help/{id}")
	public Help getQnA(@PathVariable("id") Long id) {
		return transactionService. getQnA(id);
		
	}
	
	 
	
}



