package com.nicommons.file;

import com.nicommons.utils.StrUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Excel通用工具类
 *
 * @author feng
 * @date 2018-08-30 21:47
 **/
public class ExcelUtil {
    private static Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 不同版本后缀
     */
    public static final String EXCEL_2003_SUFFIX = "xls";
    public static final String EXCEL_2010_SUFFIX = "xlsx";

    /**
     * 读取excel文件内容，将文件内容封装入Map
     * @param path 文件路径
     * @return 文件内容
     */
    public static Map<String, List<List<String>>> readExcel(String path){
        return readExcel(new File(path));
    }

    /**
     * 读取excel文件内容，将文件内容封装入Map
     * @param file excel文件实例
     * @return 文件内容
     */
    public static Map<String, List<List<String>>> readExcel(File file){
        log.debug("开始读取文件" + file.getPath());
        try {
            InputStream in = new FileInputStream(file);
            Workbook wb = null;

            if (file.getPath().endsWith(EXCEL_2003_SUFFIX)){
                wb = new HSSFWorkbook(in);
            }else if (file.getPath().endsWith(EXCEL_2010_SUFFIX)){
                wb = new XSSFWorkbook(in);
            }else {
                log.debug("文件非excel，无法读取！");
                return null;
            }

            return read(wb);

        }catch (Exception e){
            log.error("文件读取失败！" , e);
        }

        return null;
    }

    /**
     * 读取excel文件内容，将文件内容封装入Map
     * @param wb Workbook实例
     * @return 文件内容
     */
    private static Map<String, List<List<String>>> read(Workbook wb){
        Map<String, List<List<String>>> maps = new HashMap<String, List<List<String>>>();
        // 循环每个工作页
        for (int i = 0, size = wb.getNumberOfSheets(); i < size; i++){
            List<List<String>> sheetList = new ArrayList<List<String>>();
            Sheet sheet = wb.getSheetAt(i);
            //去除第一行表头
            for (int j = 1, rows = sheet.getLastRowNum(); j < rows; j++){
                List<String> rowList = new ArrayList();
                Row row = sheet.getRow(j);
                //循环每行
                for (int k = 0, rowSize = row.getLastCellNum(); k < rowSize; k++){
                    Cell cell = row.getCell(k);
                    rowList.add(String.valueOf(getCell(cell)));
                }
                sheetList.add(rowList);
            }
            maps.put(sheet.getSheetName(), sheetList);
        }
        return maps;
    }

    /**
     * 获取单元格值
     * @param cell 单元格对象
     * @return 单元格内容
     */
    private static Object getCell(Cell cell){
        if (cell == null){
            return null;
        }
        int cellType = cell.getCellType();
        switch (cellType){
            case Cell.CELL_TYPE_BLANK:
                return null;
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_STRING:
                return StrUtils.isEmity(cell.getStringCellValue())? null : cell.getStringCellValue();
            default:
                return null;
        }
    }

    /**
     * 根据int类型的单元格类型返回单元格类型描述
     * @param cellType 单元格类型标志
     * @return 单元格类型描述
     */
    private static String getCellTypeByInt(int cellType) {
        switch (cellType) {
            case Cell.CELL_TYPE_BLANK:
                return "Null type";
            case Cell.CELL_TYPE_BOOLEAN:
                return "Boolean type";
            case Cell.CELL_TYPE_ERROR:
                return "Error type";
            case Cell.CELL_TYPE_FORMULA:
                return "Formula type";
            case Cell.CELL_TYPE_NUMERIC:
                return "Numeric type";
            case Cell.CELL_TYPE_STRING:
                return "String type";
            default:
                return "Unknown type";
        }
    }

}
