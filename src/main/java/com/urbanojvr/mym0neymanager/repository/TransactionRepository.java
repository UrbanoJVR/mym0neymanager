package com.urbanojvr.mym0neymanager.repository;

import com.urbanojvr.mym0neymanager.domain.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;


public interface TransactionRepository extends Repository<Transaction, Long> {
    static final String GET_TRANSACTIONS_BY_YEAR_QUERY = "SELECT * FROM transactions WHERE YEAR(date) = :year ORDER BY transactions.date";
    static final String GET_TRANSACTIONS_BY_YEAR_AND_MONTH_QUERY = "SELECT * FROM transactions WHERE YEAR(date) = :year AND MONTH(date) = :month ORDER BY transactions.date";

    ArrayList<Transaction> findAllByConcept(String concept);

    ArrayList<Transaction> findAllByConceptContains(String concept);

    ArrayList<Transaction> findAll();

    Transaction save(Transaction transaction);

    @Query(value = GET_TRANSACTIONS_BY_YEAR_QUERY, nativeQuery = true)
    ArrayList<Transaction> findAllByYear(@Param("year") int year);

    @Query(value = GET_TRANSACTIONS_BY_YEAR_AND_MONTH_QUERY, nativeQuery = true)
    ArrayList<Transaction> findAllByYearAndMoth(@Param("year") int year, @Param("month") int month);
}
