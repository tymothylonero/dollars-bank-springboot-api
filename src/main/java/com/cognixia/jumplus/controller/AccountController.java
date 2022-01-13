package com.cognixia.jumplus.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jumplus.model.Account;
import com.cognixia.jumplus.repository.AccountRepository;

@RequestMapping("/api/account")
@RestController
public class AccountController {
	
	@Autowired
	AccountRepository repo;
	
	// Get an account by its id
	@GetMapping("/{id}")
	public Account getAccount(@PathVariable long id) throws Exception {
		
		Optional<Account> found = repo.findById(id);
		if(found.isPresent()) {
			return found.get();
		}
		throw new Exception("Account with id of '" + id + "' not found.");
		
	}
	
	// Create a new account
	@PostMapping("/add")
	public ResponseEntity<?> addAccount(@RequestBody Account acc) {
		
		acc.setId(0L);
		
		Account newAcc = repo.save(acc);
		
		return ResponseEntity.status(201).body(newAcc);
	}
	
	// Update the info of an existing account
	
	@PutMapping("/update")
	public Account updateAccount(@RequestBody Account acc) throws Exception {
		
		if(repo.existsById(acc.getId())) {
			
			acc.attachTransactions();
			return repo.save(acc);
		}
		throw new Exception("Account with username of '" + acc.getUsername() + "' not found.");
	}
	
	// Delete an account with a given id
	
	// TODO
	
	// Get an account by its username
	
	@GetMapping("/username/{username}")
	public Account getAccountByUsername(@PathVariable String username) throws Exception {
		
		Optional<Account> found = repo.findByUsername(username);
		if(found.isPresent()) {
			return found.get();
		}
		throw new Exception("Account with username of '" + username + "' not found.");
		
	}

}