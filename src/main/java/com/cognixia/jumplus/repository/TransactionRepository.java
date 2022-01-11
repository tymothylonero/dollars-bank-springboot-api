package com.cognixia.jumplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognixia.jumplus.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}