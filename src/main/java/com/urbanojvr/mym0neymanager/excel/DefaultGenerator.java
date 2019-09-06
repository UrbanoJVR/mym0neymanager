package com.urbanojvr.mym0neymanager.excel;

import com.urbanojvr.mym0neymanager.domain.Transaction;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;

public class DefaultGenerator extends ExcelGenerator{

    public DefaultGenerator(HashMap<Integer, ArrayList<Transaction>> transactions) {
        super(transactions);
    }

    @Override
    public void generateExcelReport() throws IOException {
        File file =  new File("/Users/urbano.villanueva/Desktop/report.xlsx");
        FileOutputStream outputStream = new FileOutputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook();

        this.createSummarySheet(transactions, workbook);
        this.createGeneralSheet(transactions, workbook);

        for(int month = 1; month < 13; month ++){
            ArrayList<Transaction> transactionsOfMonth = transactions.get(month);
            super.generateMonthSheet(month, transactionsOfMonth, workbook);
        }

        workbook.write(outputStream);
    }
}
