package com.urbanojvr.mym0neymanager.service;

import com.urbanojvr.mym0neymanager.domain.Transaction;
import com.urbanojvr.mym0neymanager.repository.TransactionRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {

    private TransactionService sut;

    @Mock
    TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        sut = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    public void whenGetAllTransactions_shouldCallCorrectServiceOperation_andThenReturnsExpectedList(){
        ArrayList<Transaction> expectedList = new ArrayList<>();

        Transaction t = new Transaction("Ejemplo 1", 50.25, new Date());
        expectedList.add(t);

        Transaction t2 = new Transaction("Ejemplo 2", 100.89, new Date());
        expectedList.add(t2);

        when(transactionRepository.findAll()).thenReturn(expectedList);

        ArrayList<Transaction> receivedList = sut.getAll();

        verify(transactionRepository).findAll();
        assertEquals(expectedList, receivedList);
        assertTrue(receivedList.contains(t));
        assertTrue(receivedList.contains(t2));
    }

    @Test
    public void whenGetByYear_shouldReturnExpectedList(){
        ArrayList<Transaction> expectedList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        Date expectedDate = calendar.getTime();

        Transaction t = new Transaction("Ejemplo 1", 50.25, expectedDate);
        expectedList.add(t);

        Transaction t2 = new Transaction("Ejemplo 2", 100.89, expectedDate);
        expectedList.add(t2);

        when(transactionRepository.findAllByYear(2020)).thenReturn(expectedList);
        ArrayList<Transaction> receivedList = sut.getByYear(2020);

        verify(transactionRepository).findAllByYear(2020);
        assertEquals(2, receivedList.size());
        assertTrue(receivedList.contains(t));
        assertTrue(receivedList.contains(t2));
    }

    @Test
    public void whenSaveNewTransaction_thenCallRepositoryAndReturnStoredObject(){
        Transaction expected = new Transaction("Ejemplo 1", 50.25, new Date());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(expected);
        Transaction stored = sut.save(expected);

        verify(transactionRepository).save(expected);
        assertEquals(expected, stored);
        assertEquals(50.25, stored.getAmount(), 0.0);
    }

    @Test
    public void whenGetByYearAndMonth_shouldReturnExpectedTransactions_andThenCallRepository(){
        ArrayList<Transaction> expectedList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        Date expectedDate = calendar.getTime();

        Transaction t = new Transaction("Ejemplo 1", 50.25, expectedDate);
        expectedList.add(t);

        Transaction t2 = new Transaction("Ejemplo 2", 100.89, expectedDate);
        expectedList.add(t2);

        when(transactionRepository.findAllByYearAndMoth(2020, 10)).thenReturn(expectedList);
        ArrayList<Transaction> receivedList = sut.getByYearAndMonth(2020, 10);

        verify(transactionRepository).findAllByYearAndMoth(2020, 10);
        assertEquals(2, receivedList.size());
        assertTrue(receivedList.contains(t));
        assertTrue(receivedList.contains(t2));
    }
}
