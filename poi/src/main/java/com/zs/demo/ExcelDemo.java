package com.zs.demo;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * @author: LonelyBattle
 * @create: 2025-01-19 22:57
 */
public class ExcelDemo {

    private static final String EXCEL_PATH = "D:\\Code\\tempdata\\test.xlsx";

    /**
     * SXSSFWorkbook 主要用于写入大型 Excel 文件，
     * 因为它通过流式处理来减少内存占用。然而，
     * SXSSFWorkbook 并不直接支持从输入流读取 Excel 文件。
     * 对于读取 Excel 文件，通常使用 XSSFWorkbook 或 SXSSFWorkbook
     * 的底层实现 XSSFSheet 等类。
     * @param path
     * @throws IOException
     */
    public static void write(String path) throws IOException {
        OutputStream out = new FileOutputStream(new File(path));
        SXSSFWorkbook sheets = new SXSSFWorkbook();
        CellStyle cellStyle = getCellStyle(sheets);

        SXSSFSheet sheet1 = sheets.createSheet("demoSheet1");
        SXSSFRow headerRow = sheet1.createRow(0);
        SXSSFCell cell = headerRow.createCell(0);
        cell.setCellValue("姓名");
        cell.setCellStyle(cellStyle);
        SXSSFCell cell1 = headerRow.createCell(1);
        cell1.setCellValue("年龄");
        cell1.setCellStyle(cellStyle);

        SXSSFRow row = sheet1.createRow(1);
        SXSSFCell cell2 = row.createCell(0);
        cell2.setCellValue("张三");
        cell2.setCellStyle(cellStyle);
        SXSSFCell cell3 = row.createCell(1);
        cell3.setCellValue("18");
        cell3.setCellStyle(cellStyle);

        sheets.write(out);
        /*先关闭sheet，再关闭输入流*/
        sheets.close();
        out.close();
    }

    public static CellStyle getCellStyle(SXSSFWorkbook sheets){
        CellStyle cellStyle = sheets.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return cellStyle;
    }

    public static void read(String path) throws IOException {
        InputStream in = new FileInputStream(new File(path));
        //SXSSFWorkbook sheets = new SXSSFWorkbook(in);
        XSSFWorkbook sheets = new XSSFWorkbook(in);
        XSSFSheet sheet = sheets.getSheetAt(0);
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
        }
        sheets.close();
        in.close();
    }
    public static void main(String[] args) throws IOException {
        write(EXCEL_PATH);
        read(EXCEL_PATH);
    }
}
