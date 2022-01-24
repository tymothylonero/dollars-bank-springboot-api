package com.cognixia.jumplus.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jumplus.model.Account;
import com.cognixia.jumplus.model.Transaction;
import com.cognixia.jumplus.repository.AccountRepository;
import com.cognixia.jumplus.repository.TransactionRepository;

@RequestMapping("/api/account")
@RestController
public class AccountController {
	
	@Autowired
	AccountRepository repo;
	
	@Autowired
	TransactionRepository transRepo;
	
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
		
		List<Transaction> transactions = acc.getTransactions();
		acc.setTransactions(null);
		
		Account newAcc = repo.save(acc);
		
		List<Transaction> newTransactions = new ArrayList<Transaction>();
		for(Transaction t : transactions) {
			t.setId(0L);
			t.setAccount(newAcc);
			newTransactions.add(transRepo.save(t));
		}
		
		newAcc.setTransactions(newTransactions);
		
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
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable long id) throws Exception {
		
		if(repo.existsById(id)) {
			
			Account deleted = repo.getById(id);
			repo.deleteById(id);
			return new ResponseEntity<Account>(deleted, HttpStatus.OK);
		}
		
		throw new Exception("Account with id of '" + id + "' not found.");
		
	}
	
	// Get an account by its username
	
	@GetMapping("/username/{username}")
	public Account getAccountByUsername(@PathVariable String username) throws Exception {
		
		Optional<Account> found = repo.findByUsername(username);
		if(found.isPresent()) {
			return found.get();
		}
		throw new Exception("Account with username of '" + username + "' not found.");
		
	}
	
	@PutMapping("/username/deposit/{username}/{amount}")
    public Account depositAmount(@PathVariable String username , @PathVariable Double amount) throws Exception {

		Optional<Account> findAccount = repo.findByUsername(username);
		
		if(findAccount.isEmpty()) {
			throw new Exception("Could not find account '" + username + "'.");
		}
		
		Account user = findAccount.get(); 

		Transaction deposit = new Transaction(0L, "Deposit", "Deposit of $" + amount, amount, new Date(), user);
		
		transRepo.save(deposit);
		
		Double newBalance = user.getBalance() + amount;
		user.setBalance(newBalance);
		user.attachTransactions();
		return updateAccount(user);


    }

    @PutMapping("/username/withdraw/{username}/{amount}")
    public Account withdrawAmount(@PathVariable String username , @PathVariable Double amount) throws Exception {


		Optional<Account> findAccount = repo.findByUsername(username);

		if (findAccount.isEmpty()) {
			throw new Exception("Could not find account '" + username + "'.");
		}

		Account user = findAccount.get();
		if (amount > user.getBalance()) {
			throw new Exception("Insufficient Balance");
		} else {
			
			Transaction withdraw = new Transaction(0L, "Withdrawal", "Withdrawal of $" + amount, amount * -1.0, new Date(), user);
			
			transRepo.save(withdraw);
			
			Double newBalance = user.getBalance() - amount;
			user.setBalance(newBalance);
			user.attachTransactions();
			return updateAccount(user);
		}

    }

}