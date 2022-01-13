package com.cognixia.jumplus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognixia.jumplus.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	public Optional<Account> findByUsername(String username);

}