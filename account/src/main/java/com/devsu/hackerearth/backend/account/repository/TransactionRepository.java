package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t JOIN Account a ON t.accountId = a.id WHERE :clientId = a.clientId AND t.date BETWEEN :start AND :end")
    List<Transaction> findAllByAccountClientIdAndDateBetween(
            @Param("clientId") Long clientId,
            @Param("start") Date start,
            @Param("end") Date end);

    @Query("SELECT t FROM Transaction t JOIN Account a ON t.accountId = a.id WHERE :clientId = a.clientId AND t.date BETWEEN :start AND :end")
    Page<Transaction> findAllByAccountClientIdAndDateBetween(
            @Param("clientId") Long clientId,
            @Param("start") Date start,
            @Param("end") Date end,
            @Param("page") Pageable pageable);
}
