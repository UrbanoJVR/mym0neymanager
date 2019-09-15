package com.urbanojvr.mym0neymanager.service;

import com.urbanojvr.mym0neymanager.domain.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface TransactionService {
    ArrayList<Transaction> getAll();

    Transaction save(Transaction transaction);

    ArrayList<Transaction> getByYear(int year);

    ArrayList<Transaction> getByYearAndMonth(int year, int month);

    void generateExcel(int year) throws IOException;
}
