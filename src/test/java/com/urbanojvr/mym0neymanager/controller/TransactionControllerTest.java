package com.urbanojvr.mym0neymanager.controller;

import com.urbanojvr.mym0neymanager.TestUtilities;
import com.urbanojvr.mym0neymanager.domain.Transaction;
import com.urbanojvr.mym0neymanager.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionControllerTest {

    TransactionController sut;

    @Mock
    TransactionService service;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        sut = new TransactionController(service);
    }

    @Test
    void getAllTransactions() {
        int listSize = 100;
        ArrayList<Transaction> expected = TestUtilities.generateTransactionList(listSize);

        when(service.getAll()).thenReturn(expected);

        ArrayList<Transaction> received = sut.getAllTransactions();
        assertEquals(listSize, received.size());
        for(int i = 0; i < listSize; i++){
            assertEquals(received.get(i).toString(), TestUtilities.createTransactionFromIndex(i).toString());
        }
    }

    @Test
    void save() {
        Transaction t = new Transaction("Concepto", 15.95, new Date());

        Transaction received = sut.save(t);

        assertEquals(received, t);
        verify(service).save(t);
    }

    @Test
    void generateExcelReport() {
    }

    @Test
    void getTransactionsByYear() {
        ArrayList<Transaction> expectedList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        Date expectedDate = calendar.getTime();

        Transaction t = new Transaction("Ejemplo 1", 50.25, expectedDate);
        expectedList.add(t);

        Transaction t2 = new Transaction("Ejemplo 2", 100.89, expectedDate);
        expectedList.add(t2);

        when(service.getByYear(2020)).thenReturn(expectedList);
        ArrayList<Transaction> receivedList = sut.getTransactionsByYear(2020);

        verify(service).getByYear(2020);
        assertEquals(2, receivedList.size());
        assertTrue(receivedList.contains(t));
        assertTrue(receivedList.contains(t2));
    }
}
