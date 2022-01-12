package com.cognixia.jumplus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cognixia.jumplus.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	// Get five most recent transactions
	// SELECT * FROM transaction WHERE user_id=? ORDER BY id DESC LIMIT 5
	
	@Query(value = "select * from transaction where user_id = :userid order by id desc limit 5", nativeQuery = true)
	public List<Transaction> getFiveMostRecentTransactions(@Param(value = "userid") long userid);

}