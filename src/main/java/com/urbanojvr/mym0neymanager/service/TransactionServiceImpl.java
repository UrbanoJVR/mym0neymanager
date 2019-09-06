package com.urbanojvr.mym0neymanager.service;

import com.urbanojvr.mym0neymanager.domain.Transaction;
import com.urbanojvr.mym0neymanager.excel.DefaultGenerator;
import com.urbanojvr.mym0neymanager.excel.ExcelGenerator;
import com.urbanojvr.mym0neymanager.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository repository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArrayList<Transaction> getAll() {
        return (ArrayList<Transaction>) repository.findAll();
    }

    @Override
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public ArrayList<Transaction> getByYear(int year) {
        return repository.findAllByYear(year);
    }

    @Override
    public ArrayList<Transaction> getByYearAndMonth(int year, int month) {
        return repository.findAllByYearAndMoth(year, month);
    }

    @Override
    public void generateExcel(int year) throws IOException {
        HashMap<Integer, ArrayList<Transaction>> transactionsMap = new HashMap<>();
        for(int month = 1; month < 13; month++){
            transactionsMap.put(month, this.getByYearAndMonth(year, month));
        }
        ExcelGenerator generator = new DefaultGenerator(transactionsMap);
        generator.generateExcelReport();
    }
}
