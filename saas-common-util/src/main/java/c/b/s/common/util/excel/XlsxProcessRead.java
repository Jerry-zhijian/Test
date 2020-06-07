package c.b.s.common.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @Author: lingjie
 * @Date: 2018/8/29 16:21
 */
public class XlsxProcessRead {
    private final Logger logger = LoggerFactory.getLogger(XlsxProcessRead.class);

    //开始读取行数从第0行开始计算
    private int rowIndex = -1;

    private int minColumns = 0;
    /**
     * Destination for data
     */
    private final StringBuffer rowStrs = new StringBuffer();

    Map<Integer, List<String>> resultMap = new HashMap<>();

    /**
     * 支持遍历同一个excle文件下多个sheet的解析
     * excel记录行操作方法，以行索引和行元素列表为参数，对一行元素进行操作，元素为String类型
     *
     * @param filename
     * @return
     */
    public Map processAllSheet(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ);
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
        XSSFReader xssfReader = new XSSFReader(pkg);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        InputStream stream = null;
        while (iter.hasNext()) {
            try {
                stream = iter.next();
                parserSheetXml(styles, strings, new SheetToCSV(), stream);
            } catch (Exception e) {
                logger.error("parserSheetXml error: ", e);
            } finally {
                stream.close();
            }
        }
        return resultMap;
    }

    /**
     * 支持遍历同一个excle文件下多个sheet的解析
     * excel记录行操作方法，以行索引和行元素列表为参数，对一行元素进行操作，元素为String类型
     *
     * @param xlsxFile
     * @return
     * @throws Exception
     */
    public Map processAllSheet(MultipartFile xlsxFile) throws Exception {
        return processAllSheet(xlsxFile.getInputStream());
    }


    /**
     * 通过InputStream流的方式解析Sheet
     */
    public Map processAllSheet(InputStream inputStream) throws Exception {
        OPCPackage pkg = OPCPackage.open(inputStream);
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
        XSSFReader xssfReader = new XSSFReader(pkg);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        InputStream stream = null;
        // 只处理第一个sheet
        if (iter.hasNext()) {
            try {
                stream = iter.next();
                parserSheetXml(styles, strings, new SheetToCSV(), stream);
            } catch (Exception e) {
                logger.error("parserSheetXml error: ", e);
            } finally {
                stream.close();
            }
        }
        return resultMap;
    }


    /**
     * 解析excel 转换成xml
     *
     * @param styles
     * @param strings
     * @param sheetHandler
     * @param sheetInputStream
     * @throws IOException
     * @throws SAXException
     */
    public void parserSheetXml(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler sheetHandler, InputStream sheetInputStream) throws IOException, SAXException {
        DataFormatter formatter = new DataFormatter();
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("SAX parser appears to be broken - " + e);
        }
    }

    /**
     * 读取excel行、列值
     *
     * @author nevin.zhang
     */
    private class SheetToCSV implements SheetContentsHandler {
        private boolean firstCellOfRow = false;
        private int currentRowNumber = -1;
        private int currentColNumber = -1;

        /**
         * 处理cell中为空值的情况
         *
         * @param number
         */
        private void processCellBlankCells(int number) {
            for (int i = 0; i < number; i++) {
                for (int j = 0; j < minColumns; j++) {
                    rowStrs.append("|@|");
                }
                rowStrs.append('\n');
            }
        }

        @Override
        public void startRow(int rowNum) {
            processCellBlankCells(rowNum - currentRowNumber - 1);
            firstCellOfRow = true;
            currentRowNumber = rowNum;
            currentColNumber = -1;
        }

        @Override
        public void endRow(int rowNum) {
            if(rowStrs.length()>0){
                for (int i = currentColNumber; i < minColumns; i++) {
                    rowStrs.append("|@|");
                }
                // 从设置的rowIndex的行数开始加入到list，前三行为标题，多个sheet都从第三行开始读取的数据加入到list
                String endRowStrs = rowStrs.toString();
                if (!rowStrs.toString().equals("|@|")) {

                    resultMap.put(currentRowNumber, Lists.newArrayList(Splitter.on("|@|")
                            .split(endRowStrs)));
                }
                // 清空buffer
                rowStrs.delete(0, rowStrs.length());
            }
        }

        @Override
        public void cell(String cellReference, String cellValue, XSSFComment comment) {
            if (firstCellOfRow) {
                firstCellOfRow = false;
            } else {
                rowStrs.append("|@|");
            }
            if (cellReference == null) {
                cellReference = new CellAddress(currentRowNumber, currentColNumber).formatAsString();
            }
            int thisCol = (new CellReference(cellReference)).getCol();
            int missedCols = thisCol - currentColNumber - 1;
            for (int i = 0; i < missedCols; i++) {
                // excel中为空的值设置为“|@|”
                rowStrs.append("|@|");
            }
            currentColNumber = thisCol;
            rowStrs.append(cellValue);
            minColumns = minColumns > thisCol ? minColumns : thisCol;
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }
    }
}
