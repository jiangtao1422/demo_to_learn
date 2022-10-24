package com.jt.service.impl;

import com.jt.service.ExcelService;
import com.jt.utils.StringUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author jiangtao
 * @create 2022/10/2 14:18
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public void createExcel() throws IOException {
        FileInputStream inputStream = new FileInputStream("C:\\Users\\eiji\\Desktop\\confinedSpace.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheetAt(0);
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "测试人员");
        map.put("age", "18");

        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); ) {
            Row next = rowIterator.next();
            for (Iterator<Cell> cellIterator = next.cellIterator(); cellIterator.hasNext(); ) {
                Cell cell = cellIterator.next();
                if (cell != null) {
                    String cellValue = cell.getStringCellValue();
                    String replaceEl = StringUtil.replaceEl(cellValue, map);
                    cell.setCellValue(replaceEl);
                }
            }

        }

        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\eiji\\Desktop\\test.xlsx");
        wb.write(fileOutputStream);
    }
}
