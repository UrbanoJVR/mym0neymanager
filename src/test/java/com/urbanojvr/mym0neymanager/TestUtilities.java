package com.urbanojvr.mym0neymanager;

import com.urbanojvr.mym0neymanager.domain.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TestUtilities {

    public static Date createDate(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date date = calendar.getTime();

        return date;
    }

    public static Date createDate(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        Date date = calendar.getTime();

        return date;
    }

    public static ArrayList<Transaction> generateTransactionList(int size){
        ArrayList<Transaction> transactions = new ArrayList<>();

        for(int i = 0; i < size; i ++){
            Transaction t = createTransactionFromIndex(i);
            transactions.add(t);
        }

        return transactions;
    }

    public static Transaction createTransactionFromIndex(int index){
        String concept = "Concepto " + String.valueOf(index);
        String amountString = "10" + String.valueOf(index) + ".55";
        double amount = Double.parseDouble(amountString);
        int year = yearFromIndex(index);
        int month = mothFromIndex(index);

        return new Transaction(concept, amount, TestUtilities.createDate(year, month));
    }

    public static int yearFromIndex(int index){
        String yearString = "";
        if(index < 10){
            yearString = "200" + index;
        } else if (index < 100) {
            yearString = "20" + index;
        } else if(index < 1000){
            yearString = "2" + index;
        }

        return Integer.parseInt(yearString);
    }

    public static int mothFromIndex(int index){
        if(index <= 12 && index > 0){
            return index;
        }

        int month = index;

        while(month > 12){
            month = month / 12;
        }

        return month;
    }
}
