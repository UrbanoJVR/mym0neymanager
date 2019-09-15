package com.urbanojvr.mym0neymanager.excel;

import com.urbanojvr.mym0neymanager.domain.Transaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

public abstract class ExcelGenerator {

    protected static final int START_ROW = 1;

    HashMap<Integer, ArrayList<Transaction>> transactions;

    public ExcelGenerator(HashMap<Integer, ArrayList<Transaction>> transactions){
        this.transactions = transactions;
    }

    public abstract void generateExcelReport() throws IOException;

    protected Sheet generateMonthSheet(int month, ArrayList<Transaction> transactionsOfMonth, XSSFWorkbook workbook){
        String actualMonth = Month.of(month).name();
        Sheet monthSheet = workbook.createSheet(actualMonth);

        int actualRow = START_ROW;

        Row monthNameRow = monthSheet.createRow(actualRow);
        monthSheet.addMergedRegion(new CellRangeAddress(actualRow, actualRow, 1, 3));

        Cell monthNameCell = monthNameRow.createCell(1);
        monthNameCell.setCellStyle(this.createCellStyle(workbook, IndexedColors.LIGHT_YELLOW.getIndex()));
        monthNameCell.setCellValue(Month.of(month).name());
        actualRow ++;

        double totalOfMonth = 0;
        for(Transaction transaction : transactionsOfMonth){
            Row transactionRow = monthSheet.createRow(actualRow);

            writeTransactionInRow(workbook, transactionRow, transaction, actualRow);

            totalOfMonth += transaction.getAmount();

            actualRow ++;
        }

        Row totalRow = monthSheet.createRow(actualRow);
        Cell totalTitleCell = totalRow.createCell(1);
        monthSheet.addMergedRegion(new CellRangeAddress(actualRow, actualRow, 1, 2));
        totalTitleCell.setCellStyle(createCellStyle(workbook, IndexedColors.GREY_25_PERCENT.getIndex(), IndexedColors.RED.getIndex()));
        totalTitleCell.setCellValue("Total:");

        Cell totalAmountCell = totalRow.createCell(3);
        totalAmountCell.setCellStyle(createCellStyle(workbook, IndexedColors.GREY_25_PERCENT.getIndex(), IndexedColors.RED.getIndex()));
        totalAmountCell.setCellValue(totalOfMonth);

        monthSheet.autoSizeColumn(1);
        monthSheet.autoSizeColumn(2);
        monthSheet.autoSizeColumn(3);

        return monthSheet;
    }

    protected Sheet createGeneralSheet(HashMap<Integer, ArrayList<Transaction>> transactions, XSSFWorkbook workbook){
        Sheet generalSheet = workbook.createSheet("General");

        int actualRow = START_ROW;
        for(int month = 1; month < 13; month ++){
            Row titleRow = generalSheet.createRow(actualRow);
            generalSheet.addMergedRegion(new CellRangeAddress(actualRow, actualRow, 1, 3));

            Cell titleCell = titleRow.createCell(1);
            titleCell.setCellStyle(this.createCellStyle(workbook, IndexedColors.LIGHT_YELLOW.getIndex()));
            titleCell.setCellValue(Month.of(month).name());
            actualRow ++;

            double totalOfMonth = 0;
            for(Transaction transaction : transactions.get(month)){
                Row transactionRow = generalSheet.createRow(actualRow);

                writeTransactionInRow(workbook, transactionRow, transaction, actualRow);

                totalOfMonth += transaction.getAmount();

                actualRow ++;
            }

            Row totalRow = generalSheet.createRow(actualRow);
            Cell totalTitleCell = totalRow.createCell(1);
            generalSheet.addMergedRegion(new CellRangeAddress(actualRow, actualRow, 1, 2));
            totalTitleCell.setCellStyle(createCellStyle(workbook, IndexedColors.GREY_25_PERCENT.getIndex(), IndexedColors.RED.getIndex()));
            totalTitleCell.setCellValue("Total:");

            Cell totalAmountCell = totalRow.createCell(3);
            totalAmountCell.setCellStyle(createCellStyle(workbook, IndexedColors.GREY_25_PERCENT.getIndex(), IndexedColors.RED.getIndex()));
            totalAmountCell.setCellValue(totalOfMonth);

            actualRow ++;

            generalSheet.autoSizeColumn(1);
            generalSheet.autoSizeColumn(2);
            generalSheet.autoSizeColumn(3);
        }

        return generalSheet;
    }

    protected Sheet createSummarySheet(HashMap<Integer, ArrayList<Transaction>> transactions, XSSFWorkbook workbook){
        Sheet summarySheet = workbook.createSheet("Summary");

        int actualRow = START_ROW;
        double totalOfYear = 0;
        for(int month = 1; month < 13; month ++){
            Row monthRow = summarySheet.createRow(actualRow);

            Cell titleCell = monthRow.createCell(1);
            titleCell.setCellStyle(createCellStyle(workbook, IndexedColors.GREY_25_PERCENT.getIndex(), IndexedColors.RED.getIndex()));
            titleCell.setCellValue(Month.of(month).name());

            Cell totalOfMonthCell = monthRow.createCell(2);
            totalOfMonthCell.setCellStyle(createCellStyle(workbook, IndexedColors.GREY_25_PERCENT.getIndex(), IndexedColors.RED.getIndex()));

            double totalOfMonth = 0.0;
            for(Transaction transaction : transactions.get(month)){
                totalOfMonth += transaction.getAmount();
            }

            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            totalOfMonth = Double.parseDouble(decimalFormat.format(totalOfMonth).replace(",", "."));
            totalOfMonthCell.setCellValue(totalOfMonth);

            totalOfYear += totalOfMonth;

            actualRow ++;
        }

        Row totalOfYearRow = summarySheet.createRow(actualRow);

        Cell totalOfYearTitleCell = totalOfYearRow.createCell(1);
        totalOfYearTitleCell.setCellStyle(this.createCellStyle(workbook, IndexedColors.LIGHT_YELLOW.getIndex()));
        totalOfYearTitleCell.setCellValue("Total of year:");

        Cell totalOfYearAmountCell = totalOfYearRow.createCell(2);
        totalOfYearAmountCell.setCellStyle(this.createCellStyle(workbook, IndexedColors.LIGHT_YELLOW.getIndex()));
        totalOfYearAmountCell.setCellValue(totalOfYear);


        summarySheet.autoSizeColumn(1);
        summarySheet.autoSizeColumn(2);
        summarySheet.autoSizeColumn(3);

        return summarySheet;
    }

    CellStyle createCellStyle(Workbook workbook, short colorIndex){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        byte[] rgbColor = new byte[]{(byte) 102, (byte) 178, (byte) 255};
        java.awt.Color awtColor = new java.awt.Color(102, 178, 255);
        int alpha = awtColor.getAlpha();
        BigInteger bigInteger = BigInteger.valueOf(alpha);
        byte[] alphaArray = bigInteger.toByteArray();
        XSSFColor xssfColor = new XSSFColor(rgbColor, new DefaultIndexedColorMap());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(colorIndex);

        return  cellStyle;
    }

    CellStyle createCellStyle(Workbook workbook, short backgroundColorIndex, short fontColorIndex){
        CellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        cellStyle.setFillForegroundColor(backgroundColorIndex);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setColor(fontColorIndex);
        cellStyle.setFont(font);

        return  cellStyle;
    }

    private short toggleColorsByRowIndex(int row, short colorIndex1, short colorIndex2){
        if(row % 2 != 0){
            return colorIndex1;
        } else {
            return colorIndex2;
        }
    }

    void writeTransactionInRow(Workbook workbook, Row row, Transaction transaction, int actualRow){
        for(int i = 1; i < 4; i ++){
            Cell cell = row.createCell(i);
            switch (i){
                case 1:
                    cell.setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDate()));
                    break;
                case 2:
                    cell.setCellValue(transaction.getConcept());
                    break;
                case 3:
                    cell.setCellValue(transaction.getAmount());
                    break;
            }
            short colorIndex = toggleColorsByRowIndex(actualRow, IndexedColors.SKY_BLUE.getIndex(), IndexedColors.PALE_BLUE.getIndex());
            cell.setCellStyle(createCellStyle(workbook, colorIndex));
        }
    }
}
