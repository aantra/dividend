package com.example.dividendstocks;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Getter
public class Util {
    private  Map<String, String> nseMap;
    private  Map<String, String> bseMap;

    public Util() {
        this.nseMap = new HashMap<>();
        this.bseMap = new HashMap<>();
       readFromExcelFile("nse.xlsx", nseMap);
       readFromExcelFileHssf("bse.xls", bseMap);
    }

    private void readFromExcelFile(String filePath, Map<String, String> map) {
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file
            while (itr.hasNext()) {
                Row row = itr.next();
                map.put(row.getCell(2).getStringCellValue(), row.getCell(1).getStringCellValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFromExcelFileHssf(String filePath, Map<String, String> map) {
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            // iterated 2 times, to head over immediately to table data
            itr.next();
            itr.next();
            while (itr.hasNext()) {
                Row row = itr.next();
                map.put(row.getCell(2).getStringCellValue(), String.valueOf(row.getCell(1).getNumericCellValue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
