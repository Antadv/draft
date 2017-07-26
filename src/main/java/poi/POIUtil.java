package poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 描述
 * Created by liubingguang on 2017/7/24.
 */
public class POIUtil {

    private static final short CELL_HEIGHT = 330;
    private static final int PERCENT_CELL_WIDTH = 14 * 256;
    private static final int SUB_TITLE_CELL_WIDTH = 18 * 256;
    private static final int DATA_CELL_WIDTH = 14 * 256;

    private static List<List<String>> data;

    static {
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> list = new ArrayList<>();
            list.add("浦东区");
            list.add("浦东" + i + "区");
            list.add("金阳店");
            list.add("A组（全）");
            for (int j = 0; j < 30; j++) {
                list.add("500" + i + j);
            }
            data.add(list);
        }
    }

    public static List<List<String>> getData() {
        return data;
    }

    public static void main(String[] args) {
        Workbook workbook = buildWorkbook();
        fillData(workbook, data);

        String excelPath = "D:/PartnerShipData.xls";
        saveFile(workbook, excelPath);
        openFile(excelPath);
    }

    public static Workbook buildWorkbook() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("合伙制利润均分");
        sheet.setColumnWidth(0, PERCENT_CELL_WIDTH);
        sheet.setColumnWidth(1, SUB_TITLE_CELL_WIDTH);
        sheet.setDefaultRowHeight(CELL_HEIGHT);

        HSSFCellStyle defaultCellStyle = workbook.createCellStyle();
        sheet.setDefaultColumnStyle(1, defaultCellStyle);
        /**
         * 下面对CreateFreezePane的参数作一下说明：
         * 第一个参数表示要冻结的列数；
         * 第二个参数表示要冻结的行数，这里只冻结列所以为0；
         * 第三个参数表示右边区域可见的首列序号，从1开始计算；
         * 第四个参数表示下边区域可见的首行序号，也是从1开始计算，这里是冻结列，所以为0
         */
        sheet.createFreezePane(2, 0, 2, 0);
        sheet.createFreezePane(0, 4, 0, 4);

        // 一级标题通用格式
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setFillBackgroundColor(HSSFColor.OLIVE_GREEN.index);

        cellStyle.setFont(font);

        // 二级标题样式
        HSSFCellStyle subTitleCellStyle = workbook.createCellStyle();
        subTitleCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        subTitleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        int rowOffset = 0;
        for (int i = rowOffset; i < 4; i++) {
            HSSFRow row = sheet.createRow(i);
            row.createCell(1);
            rowOffset++;
        }

        // 业绩
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);

        Row achievementRow = sheet.createRow(rowOffset);
        achievementRow.setRowStyle(cellStyle);
        Cell achievementCell = achievementRow.createCell(0);
        achievementCell.setCellValue("业绩");
        achievementCell.setCellStyle(cellStyle);

        Cell actualAchievementCell = achievementRow.createCell(1);//实收归属业绩
        actualAchievementCell.setCellValue("合伙制实收归属业绩");
        actualAchievementCell.setCellStyle(cellStyle);

        //sheet.addMergedRegion(new CellRangeAddress(rowOffset, rowOffset, 0, achievementRow.getLastCellNum() - 1));

        // 业绩-来源
        int rowNum = sheet.getLastRowNum();
        String[] achievementSourceArray = {"- 新房", "- 二手房", "- 租赁", "- 房管", "- 其他"};
        addSubTitle(sheet, achievementSourceArray, subTitleCellStyle, rowNum + 1);

        HSSFRow nonPartnerActualRow = sheet.createRow(sheet.getLastRowNum() + 1);
        nonPartnerActualRow.createCell(0);
        HSSFCell nonPartnerActualCell = nonPartnerActualRow.createCell(1);
        nonPartnerActualCell.setCellValue("非合伙制实收归属");
        nonPartnerActualCell.setCellStyle(cellStyle);

        // 提佣成本
        rowNum = sheet.getLastRowNum() + 2;
        Row commissionCostRow = sheet.createRow(rowNum);
        Cell commissionCostCell = commissionCostRow.createCell(0);
        commissionCostRow.createCell(1);//用来fix合并单元格后数据显示错位
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, commissionCostRow.getLastCellNum() - 1));

        commissionCostCell.setCellValue("提佣成本");
        commissionCostCell.setCellStyle(cellStyle);

        String[] commissionArray = {"- M1管理提佣", "- M2管理提佣", "- D1管理提佣", "- D2管理提佣", "- 公司管理提佣", "- 经纪人提佣", "- 房管提佣"};
        addSubTitle(sheet, commissionArray, subTitleCellStyle, rowNum + 1);

        // 其他变动成本
        rowNum = sheet.getLastRowNum() + 2;
        HSSFRow otherRow = sheet.createRow(rowNum);
        Cell otherCell = otherRow.createCell(0);
        otherRow.createCell(1);//用来fix合并单元格后数据显示错位
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, otherRow.getLastCellNum() - 1));
        otherCell.setCellValue("其它变动成本");
        otherCell.setCellStyle(cellStyle);

        String[] otherSubTitleArray = {"- 营业税"};
        addSubTitle(sheet, otherSubTitleArray, subTitleCellStyle, rowNum + 1);

        // 门店运营成本
        rowNum = sheet.getLastRowNum() + 2;
        HSSFRow storeRow = sheet.createRow(rowNum);
        Cell storeCell = storeRow.createCell(0);
        storeRow.createCell(1);//用来fix合并单元格后数据显示错位
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, storeRow.getLastCellNum() - 1));
        storeCell.setCellValue("门店运营成本");
        storeCell.setCellStyle(cellStyle);

        // 门店运营成本-固定运营成本合计
        HSSFRow mixedOperationCostRow = sheet.createRow(++rowNum);
        mixedOperationCostRow.createCell(0);
        HSSFCell mixedOperationCostCell = mixedOperationCostRow.createCell(1);
        //sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));
        mixedOperationCostCell.setCellValue("固定运营成本合计");
        mixedOperationCostCell.setCellStyle(cellStyle);

        String[] storeSubTitleArray = {"- 装修费", "- 房租物业", "- IT/办公设备折旧", "- 运营支持费", "- 保洁费", "- 绿化租赁费"};
        addSubTitle(sheet, storeSubTitleArray, subTitleCellStyle, rowNum + 1);

        return workbook;
    }

    public static Workbook fillData(Workbook workbook, List<List<String>> data) {
        if (workbook == null) {
            throw new RuntimeException("workbook is null");
        }

        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            throw new RuntimeException("sheet index at 0 is null");
        }

        if (data == null) {
            return workbook;
        }

        CellStyle orgLevelStyle = workbook.createCellStyle();
        orgLevelStyle.setAlignment(CellStyle.ALIGN_CENTER);
        orgLevelStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        DataFormat dataFormat = workbook.createDataFormat();
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(CellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        dataStyle.setDataFormat(dataFormat.getFormat("#,#0"));

        Font font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        orgLevelStyle.setFont(font);

        for (List<String> dataList : data) {
            Iterator<Row> rowItr = sheet.rowIterator();
            int dataIndex = 0;
            while (rowItr.hasNext()) {
                Row row = rowItr.next();
                Cell cell = row.createCell(row.getLastCellNum());
                if (dataIndex < 4) {
                    cell.setCellValue(dataList.get(dataIndex));
                    cell.setCellStyle(orgLevelStyle);
                } else {
                    cell.setCellStyle(dataStyle);
                    cell.setCellValue(Integer.parseInt(dataList.get(dataIndex)));
                }
                sheet.setColumnWidth(row.getLastCellNum() - 1, DATA_CELL_WIDTH);

                dataIndex++;
            }
        }

        return workbook;
    }



    public static void saveFile(Workbook workbook, String filePath) {
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void addSubTitle(Sheet sheet, String[] subTitleArray, CellStyle cellStyle, int startRowNum) {
        for (String subTitle : subTitleArray) {
            Row row = sheet.createRow(startRowNum++);
            Cell cell = row.createCell(1);
            cell.setCellValue(subTitle);
            cell.setCellStyle(cellStyle);
        }
    }

    public static void openFile(String filePath) {
        try {
            Runtime.getRuntime().exec("cmd /c " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("exception occurs when opening file " + filePath, e);
        }
    }

}
