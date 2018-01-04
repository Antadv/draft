package com.somelogs.poi;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * 描述
 * Created by liubingguang on 2017/7/26.
 */
public class POICommonUtil {

    public static void main(String[] args) throws IOException {
        ExcelExportParam param = new ExcelExportParam();

        String path = "D:/PartnerShipData.xlsx";
        //param.setTitles(Arrays.asList("厉害了我的哥", "厉害了我的哥", "厉害了我的哥", "厉害了我的哥", "厉害了我的哥"));
        InputStream inputStream = new FileInputStream(new File(path));
        param.setInputStream(inputStream);
        param.setData(POIUtil.getData());
        Workbook workbook = initWorkbookWithVerticalStyle(param);

        POIUtil.saveFile(workbook, path);
        POIUtil.openFile(path);
    }

    /**
     * 垂直数据排列报表导出
     * 注：SXSSFWorkbook 不能读取模板中数据
     *    使用模板并获取行时，请使用 XSSFWorkbook
     *
     * @param param
     * @return
     * @throws IOException
     */
    public static XSSFWorkbook initWorkbookWithVerticalStyle(ExcelExportParam param) throws IOException {
        if (param == null) {
            throw new RuntimeException("param is null");
        }
        if (CollectionUtils.isEmpty(param.getTitles()) && param.getInputStream() == null) {
            throw new RuntimeException("titles and input stream can't be null at the same time");
        }

        XSSFWorkbook workbook;
        XSSFSheet sheet;
        if (param.getInputStream() != null) {
            workbook = new XSSFWorkbook(param.getInputStream());
            sheet = workbook.getSheetAt(0);
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet();

            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

            Font titleFont = workbook.createFont();
            titleFont.setFontName("微软雅黑");
            titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            titleStyle.setFont(titleFont);
            List<String> titles = param.getTitles();
            for (int i = 0; i < titles.size(); i++) {
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(titles.get(i));
                cell.setCellStyle(titleStyle);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            }
            sheet.autoSizeColumn(0);
        }
        if (CollectionUtils.isEmpty(param.getData())) {
            return workbook;
        }

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        Font dataFont = workbook.createFont();
        dataFont.setFontName("微软雅黑");
        dataStyle.setFont(dataFont);

        for (List<String> dataList : param.getData()) {
            Iterator<Row> rowItr = sheet.rowIterator();
            int dataIndex = 0;
            while (rowItr.hasNext()) {
                Row row = rowItr.next();
                short currentCellNum = row.getLastCellNum();
                Cell cell = row.createCell(currentCellNum);
                cell.setCellStyle(dataStyle);
                dataStyle.setWrapText(true);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new XSSFRichTextString(dataList.get(dataIndex++)));
            }
        }

        return workbook;
    }
}
