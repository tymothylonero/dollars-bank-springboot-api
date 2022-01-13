package com.cognixia.jumplus.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/add")
	public ResponseEntity<?> addTransaction(@RequestBody Transaction trans) {
		
		trans.setId(0L);
		
		Transaction newTrans = repo.save(trans);
		
		return ResponseEntity.status(201).body(newTrans);
	}
	
	// Get a transaction with an id
	
	@GetMapping("/{id}")
	public Transaction getTransactionById(@PathVariable long id) throws Exception {
		
		Optional<Transaction> found = repo.findById(id);
		if(found.isPresent()) {
			return found.get();
		}
		throw new Exception("Transaction with id of '" + id + "' not found.");
		
	}
	
	// Delete a transaction with an id
	
	// TODO

}