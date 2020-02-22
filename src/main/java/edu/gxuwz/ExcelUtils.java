package edu.gxuwz;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author zyydd
 * @date 2019/3/15 15:00
 */
public class ExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    public static void main(String[] args) {
        String[][] rowArray = getArrayFromXLSX("/Users/mac/Desktop/123456.xlsx", 0);
        for (String[] row : rowArray) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 获取xlsx文件内容某个sheet（从0开始）的内容，以二维数组形式返回
     *
     * @param fileAbsolutePath xlsx文件的绝对路径
     * @return xlsx文件的文本内容
     */
    public static String[][] getArrayFromXLSX(String fileAbsolutePath, int bookIndex) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(fileAbsolutePath));
            XSSFWorkbook book = new XSSFWorkbook(inputStream);
            if (bookIndex >= book.getNumberOfSheets()) {
                LOGGER.error("getArrayFromXLSX error: bookIndex={} is to large! ", bookIndex);
                return null;
            }
            XSSFSheet sheet = book.getSheetAt(bookIndex);
            int rowNum = sheet.getLastRowNum() + 1;
            int coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();
            String[][] contents = new String[rowNum][coloumNum];
            for (int j = 0; j < rowNum; j++) {
                XSSFRow row = sheet.getRow(j);
                if (row != null) {
                    for (int k = 0; k < row.getLastCellNum(); k++) {
                        contents[j][k] = getXCellFormatValue(row.getCell(k));
                    }
                }
            }
            return contents;
        } catch (FileNotFoundException fe) {
            LOGGER.error("getArrayFromXLSX error: FileNotFoundException", fe);
        } catch (IOException ie) {
            LOGGER.error("getArrayFromXLSX error: IOException", ie);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    LOGGER.error("getArrayFromXLSX close Exception", e);
                }
            }
        }
        return null;
    }

    private static String getXCellFormatValue(XSSFCell cell) {
        String cellValue = "";
        if (null != cell) {
            switch (cell.getCellType()) {
                case XSSFCell.CELL_TYPE_STRING:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                case XSSFCell.CELL_TYPE_NUMERIC:
                    cellValue = (new Double(cell.getNumericCellValue())).intValue() + "";
                    break;
                default:
                    cellValue = " ";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

}