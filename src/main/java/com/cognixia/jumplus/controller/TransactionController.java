package com.cognixia.jumplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jumplus.repository.TransactionRepository;
import com.cognixia.jumplus.model.Transaction;

@RequestMapping("/api/transaction")
@RestController
public class TransactionController {
	
	@Autowired
	TransactionRepository repo;
	
	// Get five most recent transactions by an account id
	
	@GetMapping("/five_recent/{id}")
	public List<Transaction> getFiveTransactions(@PathVariable long id) throws Exception {
		
		return repo.getFiveMostRecentTransactions(id);
		
	}
	
	// Add a new transaction with a given account id

}