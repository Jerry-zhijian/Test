package c.b.s.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * Created by guiqingqing on 2018/8/29.
 */
public class ExcelUtil {
    public static String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            return "";
        }
        cell.setCellType(CellType.STRING);
        String value = cell.getStringCellValue();
        return null == value ? "" : value;
    }
}