package com.apabi.utils;

import com.apabi.booklist.entity.BookInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.*;

/**
 * Created by liuyutong on 2017/12/7.
 */
public class ExcelUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    /**
     * 导出 excel
     *
     * @param filePath   文件全路径
     * @param sheetName  sheet页名称
     * @param sheetIndex 当前sheet下表  从0开始
     * @param fileHeader 头部
     * @param datas      内容
     */
    public static void writeExcel(String filePath, String sheetName,
                                  int sheetIndex,
                                  String[] fileHeader,
                                  List<BookInfo> datas) {
        // 创建工作簿
        Workbook wb = new XSSFWorkbook();

        // 创建工作表 sheet
        Sheet s = wb.createSheet();

        wb.setSheetName(sheetIndex, sheetName);

        Row row = s.createRow(0);
        Cell cell;
        Font font;
        CellStyle styleHeader;

        //粗体
        font = wb.createFont();
        font.setBold(true);
        // 设置头样式
        styleHeader = wb.createCellStyle();
        styleHeader.setFont(font);

        //设置头
        for (int i = 0; i < fileHeader.length; ) {
            cell = row.createCell(i);
            cell.setCellStyle(styleHeader);
            cell.setCellValue(fileHeader[i]);
            i++;
        }

        //设置内容
        for (int rownum = 0; rownum < datas.size();rownum++) { // 行 row   datas.size()
            row = s.createRow(rownum + 1); //创建行
            BookInfo bookInfo = datas.get(rownum);
            cell = row.createCell(0);
            cell.setCellValue(bookInfo.getIsExist() == 1 ? "是" : "否");

            cell = row.createCell(1);
            cell.setCellValue(bookInfo.getMetaId());

            cell = row.createCell(2);
            cell.setCellValue(bookInfo.getName());

            cell = row.createCell(3);
            cell.setCellValue(bookInfo.getAuthor());

            cell = row.createCell(4);
            cell.setCellValue(bookInfo.getPublisher());

            cell = row.createCell(5);
            cell.setCellValue(bookInfo.getCategoryNum());

            cell = row.createCell(6);
            cell.setCellValue(bookInfo.getCategory());

            cell = row.createCell(7);
            cell.setCellValue(DateUtil.formatDate(bookInfo.getPublishDate(), "yyyy-MM-dd"));

            cell = row.createCell(8);
            cell.setCellValue(bookInfo.getIsbn());

            cell = row.createCell(9);
            cell.setCellValue(DateUtil.formatDate(bookInfo.getPromulgateDate(), "yyyy-MM-dd"));

            cell = row.createCell(10);
            cell.setCellValue(bookInfo.getSummary());
        }

        FileOutputStream out = null;
        try {
            // 创建文件或者文件夹,将内容写进去
            File uploadFile = new File(filePath);
            uploadFile.getParentFile().mkdirs();
            out = new FileOutputStream(filePath);
            wb.write(out);
            /*if (new File(filePath).createNewFile()) {
                out = new FileOutputStream(filePath);
                wb.write(out);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeExcel(OutputStream os, String sheetName,
                                      int sheetIndex,
                                      String[] fileHeader,
                                      List<BookInfo> sources) throws IOException {
        // 创建工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建工作表 sheet
        Sheet s = wb.createSheet();

        wb.setSheetName(sheetIndex, sheetName);

        Row row = s.createRow(0);
        Cell cell;
        Font font;
        CellStyle styleHeader;

        //粗体
        font = wb.createFont();
        font.setBold(true);
        // 设置头样式
        styleHeader = wb.createCellStyle();
        styleHeader.setFont(font);

        //设置头
        for (int i = 0; i < fileHeader.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(styleHeader);
            cell.setCellValue(fileHeader[i]);
        }

        //设置内容
        for (int rownum = 1; rownum < sources.size(); rownum++) { // 行 row   datas.size()
            row = s.createRow(rownum); //创建行
            BookInfo bookInfo = sources.get(rownum);
            cell = row.createCell(0);
            cell.setCellValue(bookInfo.getMetaId());

            cell = row.createCell(1);
            cell.setCellValue(bookInfo.getName());

            cell = row.createCell(2);
            cell.setCellValue(bookInfo.getAuthor());

            cell = row.createCell(3);
            cell.setCellValue(bookInfo.getPublisher());

            cell = row.createCell(4);
            cell.setCellValue(bookInfo.getCategoryNum());

            cell = row.createCell(5);
            cell.setCellValue(bookInfo.getCategory());

            cell = row.createCell(6);
            cell.setCellValue(DateUtil.formatDate(bookInfo.getPublishDate()));

            cell = row.createCell(7);
            cell.setCellValue(bookInfo.getIsbn());

            cell = row.createCell(8);
            cell.setCellValue(DateUtil.formatDate(bookInfo.getPromulgateDate()));

            cell = row.createCell(9);
            cell.setCellValue(bookInfo.getSummary());
        }
        wb.write(os);
    }



    /**
     * 读取 excel 文件内容
     */
    public static List<Map<String, String>> readExcel(String filePath, int sheetIndex) {
        List<Map<String, String>> mapList = new ArrayList<>();
        // 头
        List<String> list = new ArrayList<>();

        int cnt = 0;
        int idx = 0;

        try {
            InputStream input = new FileInputStream(filePath);  //建立输入流
            Workbook wb = new HSSFWorkbook(input);

            // 获取sheet页
            Sheet sheet = wb.getSheetAt(sheetIndex);

            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                Iterator<Cell> cells = row.cellIterator();

                Map<String, String> map = new HashMap<>();

                if (cnt == 0) { // 将头放进list中
                    while (cells.hasNext()) {
                        Cell cell = cells.next();
                        if (isContainMergeCell(sheet)) {
                            cancelMergeCell(sheet);
                        }
                        list.add(getStringCellValue(cell));
                    }
                    cnt++;
                    continue;

                } else {
                    while (cells.hasNext()) {
                        Cell cell = cells.next();
                        if (isContainMergeCell(sheet)) {
                            cancelMergeCell(sheet);
                        }
                        // 区别相同的头
                        list = changeSameVal(list);
                        map.put(list.get(idx++), getStringCellValue(cell));
                    }
                }
                idx = 0;
                mapList.add(map);

            }
            return mapList;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    /**
     * 读取 excel 文件内容
     */
    public static List<BookInfo> readExcel(InputStream is) throws IOException {
        List<BookInfo> mapList = new ArrayList<>();

        Workbook wb = new XSSFWorkbook(is);

        // 获取sheet页
        Sheet sheet = wb.getSheetAt(0);
        if (isContainMergeCell(sheet)) {
            cancelMergeCell(sheet);
        }

        // 头
        List<String> header = new ArrayList<>();
        sheet.forEach(row -> {
            // 将头放进list中
            if (row.getRowNum() == 0) {

                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    header.add(getStringCellValue(cell));
                }
//                row.forEach(cell -> header.add(getStringCellValue(cell)));
                // 区别相同的头 TODO
            } else {
                BookInfo bookInfo = new BookInfo();
                row.forEach(cell -> {
                    String column = header.get(cell.getColumnIndex());
                    bookInfo.setProperty(column, getStringCellValue(cell));
                });
                mapList.add(bookInfo);
            }
        });
        return mapList;

    }

    /**
     * 合并单元格
     *
     * @param sheet    当前sheet页
     * @param firstRow 开始行
     * @param lastRow  结束行
     * @param firstCol 开始列
     * @param lastCol  结束列
     */
    public static int mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        if (sheet == null) {
            return -1;
        }
        return sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }


    /**
     * 取消合并单元格
     */
    public static void cancelMergeCell(Sheet sheet) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int idx = 0; idx < sheetMergeCount; ) {
            CellRangeAddress range = sheet.getMergedRegion(idx);

            String val = getMergeCellValue(sheet, range.getFirstRow(), range.getLastRow());
            // 取消合并单元格
            sheet.removeMergedRegion(idx);

            for (int rownum = range.getFirstRow(); rownum < range.getLastRow() + 1; ) {
                for (int cellnum = range.getFirstColumn(); cellnum < range.getLastColumn() + 1; ) {

                    sheet.getRow(rownum).getCell(cellnum).setCellValue(val);

                    cellnum++;
                }

                rownum++;
            }

            idx++;
        }
    }

    /**
     * 判断指定单元格是否是合并单元格
     */
    public static boolean isMergeCell(Sheet sheet,
                                      int row, int column) {

        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; ) {
            CellRangeAddress range = sheet.getMergedRegion(i);

            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }

            i++;
        }
        return false;
    }

    /**
     * 判断sheet页中是否含有合并单元格
     */
    public static boolean isContainMergeCell(Sheet sheet) {
        if (sheet == null) {
            return false;
        }
        return sheet.getNumMergedRegions() > 0;
    }

    /**
     * 获取指定合并单元的值
     */
    public static String getMergeCellValue(Sheet sheet,
                                           int row, int column) {

        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; ) {
            CellRangeAddress range = sheet.getMergedRegion(i);

            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);

                    return getStringCellValue(fCell);
                }
            }

            i++;
        }

        return null;
    }

    /**
     * 获取单元格的值
     */
    public static String getStringCellValue(Cell cell) {
        String strCell = "";
        if (cell == null) return strCell;
        switch (cell.getCellTypeEnum()) {
            case STRING:
                strCell = cell.getRichStringCellValue().getString().trim();
                break;
            case NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                evaluator.evaluateFormulaCellEnum(cell);
                CellValue cellValue = evaluator.evaluate(cell);
                strCell = String.valueOf(cellValue.getNumberValue());
                break;
            default:
                strCell = "";
        }
        return strCell;
    }

    public static List<String> changeSameVal(List<String> list) {

        return list;
    }
}
