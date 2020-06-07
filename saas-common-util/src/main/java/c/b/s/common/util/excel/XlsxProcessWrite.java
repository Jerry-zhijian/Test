package c.b.s.common.util.excel;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Author: lingjie
 * @Date: 2018/8/29 20:24
 */
public class XlsxProcessWrite {

    private final Integer MAX_SHEET_ROWS = 1000000;

    /**
     * 生成SXSSFWorkbook对象
     * sheetData 的Key为Excel的 RowNo，从1开始
     */
    public SXSSFWorkbook generateWorkbook(Map sheetHead, Map sheetData) throws IOException {
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);
        //启用临时文件压缩
        wb.setCompressTempFiles(true);
        //定义Sheet
        Sheet sheet = null;
        List<Sheet> sheetList = this.initSheet(wb, sheetHead, sheetData.size());
        Iterator<Map.Entry<Integer, List<String>>> iterator = sheetData.entrySet().iterator();
        int rowIndex = 1;
        boolean isSheetFirst = true;
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<String>> entry = iterator.next();
            //每 MAX_SHEET_ROWS为一个sheet
            if (isSheetFirst) {
                int currentSheet = rowIndex / MAX_SHEET_ROWS;
                sheet = sheetList.get(currentSheet);
                rowIndex = 1;
                isSheetFirst = false;
            }
            int cellIndex = 0;
            Row row = sheet.createRow(rowIndex);
            for (String cellVal : entry.getValue()) {
                Cell cell = row.createCell(cellIndex);
                cell.setCellValue(cellVal);
                cellIndex++;
            }
            rowIndex++;
            isSheetFirst = (rowIndex - 1) % MAX_SHEET_ROWS == 0;
        }
        return wb;
    }

    /**
     * 创建一个带有表头的空excel文件
     * @param sheetHead
     * @param count
     * @return
     * @throws IOException
     */
    public WorkbookDTO createWorkbook(Map sheetHead ,int count) throws IOException {
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);
        //启用临时文件压缩
        wb.setCompressTempFiles(true);
        List<Sheet> sheetList = this.initSheet(wb, sheetHead, count);
        WorkbookDTO workbookDTO = new WorkbookDTO(1,1 ,true ,null ,sheetList ,wb);
        return workbookDTO;
    }

    /**
     * 往创建的excel文件里面添加数据
     * @param workbookDTO
     * @param sheetData
     * @return
     * @throws IOException
     */
    public SXSSFWorkbook dealWorkbookData(WorkbookDTO workbookDTO ,Map sheetData) throws IOException {
        int rowIndex = workbookDTO.getRowIndex();
        int currentRowIndex = workbookDTO.getCurrentRowIndex();
        boolean isSheetFirst = workbookDTO.isSheetFirst();
        Sheet sheet = workbookDTO.getSheet();
        List<Sheet> sheetList = workbookDTO.getSheetList();
        SXSSFWorkbook wb = workbookDTO.getSxssfWorkbook() ;

        Iterator<Map.Entry<Integer, List<String>>> iterator = sheetData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<String>> entry = iterator.next();
            //每 MAX_SHEET_ROWS为一个sheet
            if (isSheetFirst) {
                int currentSheet = currentRowIndex / MAX_SHEET_ROWS;
                sheet = sheetList.get(currentSheet);
                rowIndex = 1;
                isSheetFirst = false;
            }
            int cellIndex = 0;
            Row row = sheet.createRow(rowIndex);
            for (String cellVal : entry.getValue()) {
                Cell cell = row.createCell(cellIndex);
                cell.setCellValue(cellVal);
                cellIndex++;
            }
            rowIndex++;
            currentRowIndex++;
            isSheetFirst = (rowIndex - 1) % MAX_SHEET_ROWS == 0;
        }

        workbookDTO.setCurrentRowIndex(currentRowIndex);
        workbookDTO.setSheetFirst(isSheetFirst);
        workbookDTO.setSheet(sheet);
        workbookDTO.setRowIndex(rowIndex);
        return wb ;
    }

    /**
     * 初始化工作薄Sheet
     */
    private List<Sheet> initSheet(SXSSFWorkbook wb, Map sheetHead, Integer totalSize) {
        List<Sheet> sheetList = new ArrayList<>();
        int sheetCount = (int) Math.ceil((double) totalSize / MAX_SHEET_ROWS);
        for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = wb.createSheet();
            //定义表头
            defineTitle(sheet, sheetHead);
            sheetList.add(sheet);
        }
        return sheetList;
    }

    private void defineTitle(Sheet sheet, Map sheetHead) {
        Row row = sheet.createRow(0);
        Iterator<Map.Entry<Integer, String>> iterator = sheetHead.entrySet().iterator();
        int cellIndex = 0;
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            Cell cell = row.createCell(cellIndex);
            cell.setCellValue(entry.getValue());
            cellIndex++;
        }
    }

    public static void main(String[] args) throws IOException {
        Map<Integer,String> headMap = new HashMap<>();
        headMap.put(0 ,"序号");
        headMap.put(1 ,"姓名");
        headMap.put(2 ,"年龄");

        XlsxProcessWrite xlsxProcessWrite = new XlsxProcessWrite();
        WorkbookDTO workbookDTO = xlsxProcessWrite.createWorkbook(headMap ,1000);

        SXSSFWorkbook workbook = workbookDTO.getSxssfWorkbook();
        for(int k =0 ; k< 10 ; k++) {
            Map sheetData = new HashMap();
            for (int i = 0; i < 100; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(k).append("|").append(k+"_a_" + i).append("|").append(18).append("|");
                sheetData.put(i, Lists.newArrayList(Splitter.on("|").split(sb.toString())));
                // 清空buffer
                sb.delete(0, sb.length());
            }
            workbook = xlsxProcessWrite.dealWorkbookData(workbookDTO ,sheetData);
            System.out.println("rowIndex="+ workbookDTO.getRowIndex());
        }

        File file = new File("D:\\result.xlsx");
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        workbook.write(outputStream);
    }

}
