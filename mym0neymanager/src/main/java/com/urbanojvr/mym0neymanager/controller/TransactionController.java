package com.urbanojvr.mym0neymanager.controller;

import com.urbanojvr.mym0neymanager.domain.Transaction;
import com.urbanojvr.mym0neymanager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping("/transactions")
public class TransactionController {

    TransactionService service;

    @Autowired
    public TransactionController(TransactionService service){
        this.service = service;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ArrayList<Transaction> getAllTransactions(){
        return service.getAll();
    }

    @PostMapping
    public Transaction save(@RequestBody @Valid Transaction transaction){
        return service.save(transaction);
    }

    @GetMapping(value = "/excel/{year}")
    public void generateExcelReport(@PathVariable @Valid int year) throws IOException {
        service.generateExcel(year);
    }

    @GetMapping(value = "/{year}", produces = "application/json")
    public ArrayList<Transaction> getTransactionsByYear(@PathVariable @Valid int year){
        return service.getByYear(year);
    }

}
