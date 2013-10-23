package com.intellibps.bib.util;

/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/10/15
 * Time: 6:53 AM
 * To change this template use File | Settings | File Templates.
 */

import com.intellibps.bib.data.DataHeading;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFirstFooter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

public class ExcelHelper {
    private XSSFWorkbook wBook;

    public void openExcel(InputStream excelInputStream) throws IOException {
        wBook = new XSSFWorkbook(excelInputStream);
    }

    public void closeExcel() {
        wBook = null;
    }

    public int getWorkSheetCount() {
        return wBook.getNumberOfSheets();
    }

    public void setActiveSheet(int index) {
        wBook.setActiveSheet(index);
    }

    public ExcelHeading[] getSheetHeadings() {
        Vector<ExcelHeading> headers = new Vector<ExcelHeading>();
        ExcelHeading[] result;

        XSSFSheet sheet = wBook.getSheetAt(wBook.getActiveSheetIndex());
        Row row = sheet.getRow(0);
        Iterator<Cell> cellIterator = row.iterator();

        while (cellIterator.hasNext())
        {
            Cell cell = cellIterator.next();
            ExcelHeading excelHeading = new ExcelHeading();
            excelHeading.name(cell.getStringCellValue());
            excelHeading.index(cell.getColumnIndex());
            headers.add(excelHeading);
        }

        result = new ExcelHeading[headers.size()];
        return headers.toArray(result);
    }

    public String getSheetName()
    {
        XSSFSheet sheet = wBook.getSheetAt(wBook.getActiveSheetIndex());
        return sheet.getSheetName();
    }

    public int getDataType(ExcelHeading heading)
    {
        XSSFSheet sheet = wBook.getSheetAt(wBook.getActiveSheetIndex());
        Iterator<Row> rows = sheet.rowIterator();
        int result = 0;

        while (rows.hasNext())
        {
            Row row = rows.next()   ;
            if (row.getRowNum() != 0)
            {
                Cell cell = row.getCell(heading.index());

                if (DateUtil.isCellDateFormatted(cell))
                {
                    result = DataHeading.DATE;
                }
                else
                {
                switch (cell.getCellType())
                {
                    case  Cell.CELL_TYPE_NUMERIC: result = DataHeading.NUMBER;  break;
                    case Cell.CELL_TYPE_FORMULA: result = DataHeading.NUMBER;         break;
                    case Cell.CELL_TYPE_STRING: result = DataHeading.STRING;     break;

                }
                }

            }

        }

        return result;
    }

    public void loadDateValues(Vector<Date> dateValues, ExcelHeading heading)
    {
        XSSFSheet sheet = wBook.getSheetAt(wBook.getActiveSheetIndex());
        Iterator<Row> rows = sheet.rowIterator();

        while (rows.hasNext())
        {
            Row row = rows.next()   ;
            if (row.getRowNum() != 0)
            {
                Cell cell = row.getCell(heading.index());

               dateValues.add(cell.getDateCellValue());
            }

        }
    }

    public void loadNumberValues(Vector<Double> numberValues, ExcelHeading heading)
    {
        XSSFSheet sheet = wBook.getSheetAt(wBook.getActiveSheetIndex());
        Iterator<Row> rows = sheet.rowIterator();

        while (rows.hasNext())
        {
            Row row = rows.next()   ;
            if (row.getRowNum() != 0)
            {
                Cell cell = row.getCell(heading.index());

                numberValues.add(cell.getNumericCellValue());
            }

        }
    }

    public void loadStringValues(Vector<String> stringValues, ExcelHeading heading)
    {
        XSSFSheet sheet = wBook.getSheetAt(wBook.getActiveSheetIndex());
        Iterator<Row> rows = sheet.rowIterator();

        while (rows.hasNext())
        {
            Row row = rows.next()   ;
            if (row.getRowNum() != 0)
            {
                Cell cell = row.getCell(heading.index());

                stringValues.add(cell.getStringCellValue());
            }

        }
    }
}
