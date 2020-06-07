package c.b.s.common.util.excel;

import lombok.Data;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import java.util.ArrayList;
import java.util.List;

@Data
public class WorkbookDTO {

    private int rowIndex = 1;

    private int currentRowIndex = 1;

    private boolean isSheetFirst = true;

    private Sheet sheet = null;

    private List<Sheet> sheetList = new ArrayList<>();

    private SXSSFWorkbook sxssfWorkbook ;

    public WorkbookDTO(int rowIndex, int currentRowIndex, boolean isSheetFirst, Sheet sheet, List<Sheet> sheetList, SXSSFWorkbook sxssfWorkbook) {
        this.rowIndex = rowIndex;
        this.currentRowIndex = currentRowIndex;
        this.isSheetFirst = isSheetFirst;
        this.sheet = sheet;
        this.sheetList = sheetList;
        this.sxssfWorkbook = sxssfWorkbook;
    }
}
