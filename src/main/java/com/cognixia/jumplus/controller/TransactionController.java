package com.cognixia.jumplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jumplus.repository.TransactionRepository;

@RequestMapping("/api/transaction")
@RestController
public class TransactionController {
	
	@Autowired
	TransactionRepository repo;
	
	// Get five most recent transactions by an account id
	
	// Add a new transaction with a given account id

}