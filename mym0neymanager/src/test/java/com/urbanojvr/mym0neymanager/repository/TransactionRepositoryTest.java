package com.urbanojvr.mym0neymanager.repository;

import com.urbanojvr.mym0neymanager.TestUtilities;
import com.urbanojvr.mym0neymanager.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@TestPropertySource( "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository sut;

    @Test
    void whenGetTransactionsByYear_shouldReturnOnlyTheTransactionsWithTheCorrectYear() {
        Date expectedDate = TestUtilities.createDate(2020);
        Date unexpectedDate = TestUtilities.createDate(1995);

        Transaction t1 = new Transaction("Concepto de ejemplo 1", 50.35, expectedDate);
        sut.save(t1);
        Transaction t2 = new Transaction("Concepto de ejemplo 2", 502.35, expectedDate);
        sut.save(t2);
        Transaction t3 = new Transaction("Concepto de ejemplo 3", 502.35, unexpectedDate);
        sut.save(t3);

        ArrayList<Transaction> receivedList = sut.findAllByYear(2020);

        assertEquals(2, receivedList.size());
        assertTrue(receivedList.contains(t1));
        assertTrue(receivedList.contains(t2));
        assertFalse(receivedList.contains(t3));
    }

    @Test
    void whenSaveANewTransactions_shouldReturnTheStoredOne_andShouldAppearsWHenFindAll() {
        Transaction expected = new Transaction("Concepto", 50.55, new Date());

        Transaction inserted = sut.save(expected);

        assertEquals(expected, inserted);

        ArrayList<Transaction> receivedList = sut.findAll();

        assertTrue(receivedList.contains(expected));
    }

    @Test
    void whenGetByYearAndMonth_shouldReturnCorrectTransactions_andNoReturnUnexpectedTransactions(){
        Date expectedDate = TestUtilities.createDate(2020, Calendar.JANUARY);
        Date unexpectedYearDate = TestUtilities.createDate(1995, Calendar.JANUARY);
        Date unexpectedMonthDate = TestUtilities.createDate(2020, Calendar.DECEMBER);

        Transaction t1 = new Transaction("Concepto de ejemplo 1", 50.35, expectedDate);
        sut.save(t1);
        Transaction t2 = new Transaction("Concepto de ejemplo 2", 502.35, expectedDate);
        sut.save(t2);
        Transaction t3 = new Transaction("Concepto de ejemplo 3", 502.35, unexpectedYearDate);
        sut.save(t3);
        Transaction t4 = new Transaction("Concepto de ejemplo 4", 502.35, unexpectedMonthDate);
        sut.save(t4);

        ArrayList<Transaction> receivedList = sut.findAllByYearAndMoth(2020, 1);

        assertEquals(2, receivedList.size());
        assertTrue(receivedList.contains(t1));
        assertTrue(receivedList.contains(t2));
        assertFalse(receivedList.contains(t3));
        assertFalse(receivedList.contains(t3));
    }
}
