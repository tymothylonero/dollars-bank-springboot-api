package com.cognixia.jumplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognixia.jumplus.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}