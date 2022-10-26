package com.ISHALLNOTWANT.utils.excel2;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggz
 * @version 1.0.0
 * @date 2018/6/28
 */
public class ExcelUtil {

    /**
     * 合并单元格
     *
     * @param sheet     sheet
     * @param mergedMap 合并单元格map
     */
    public static void mergeCell(XSSFSheet sheet, Map<String, ExcelMergedLocation> mergedMap) {
        for (String o : mergedMap.keySet()) {
            ExcelMergedLocation location = mergedMap.get(o);
            // 如果只出现一次（不存在合并），则lastRow等没有设定过值，则为默认0
            if (location.getLastRow() > location.getFirstRow() || location.getLastCol() > location.getFirstCol()) {
                sheet.addMergedRegion(new CellRangeAddress(location.getFirstRow(), location.getLastRow(), location.getFirstCol(), location.getLastCol()));
            }
        }
    }

    /**
     * 合并单元格
     *
     * @param sheet      sheet
     * @param mergedList 合并单元格列表
     */
    public static void mergeCell(XSSFSheet sheet, List<ExcelMergedLocation> mergedList) {
        for (ExcelMergedLocation location : mergedList) {
            // 如果只出现一次（不存在合并），则lastRow等没有设定过值，则为默认0
            if (location.getLastRow() > location.getFirstRow() || location.getLastCol() > location.getFirstCol()) {
                sheet.addMergedRegion(new CellRangeAddress(location.getFirstRow(), location.getLastRow(), location.getFirstCol(), location.getLastCol()));
            }
        }
    }

    /**
     * 根据key获取ExcelMergedLocation
     *
     * @param mergedMap 合并单元格map
     * @param key       键
     * @param rowNum    行号
     * @param firstCol  第一列
     * @return 合并单元格
     */
    public static ExcelMergedLocation getExcelMergedLocation(Map<String, ExcelMergedLocation> mergedMap, String key, int rowNum, int firstCol) {
        ExcelMergedLocation location = mergedMap.get(key);
        if (location == null) {
            location = new ExcelMergedLocation();
            location.setFirstRow(rowNum);
            location.setFirstCol(firstCol);
        }
        return location;
    }

    /**
     * 获取单元格信息
     *
     * @param cell 单元格
     * @return 单元格信息
     */
    public static String getValue(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                return String.valueOf(cell.getBooleanCellValue()).trim();
            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                return String.valueOf(cell.getNumericCellValue()).trim();
            } else {
                return String.valueOf(cell.getStringCellValue()).trim();
            }
        } else {
            return "";
        }
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet  sheet
     * @param row    行
     * @param column 列
     * @return 合并单元格信息
     */
    public static String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }
        return null;
    }

    /**
     * 获取单元格的值
     *
     * @param cell 单元格
     * @return 单元格的值
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }


    /**
     * 判断指定的单元格所占的行数
     *
     * @param sheet  sheet
     * @param row    行下标
     * @param column 列下标
     * @return 行数
     */
    public static POIUtilBean getMergedRegionRows(Sheet sheet, int row, int column) {
        POIUtilBean b = new POIUtilBean();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    b.setRowWidth(lastRow - firstRow + 1);
                    b.setColWidth(lastColumn - firstColumn + 1);
                    b.setMerged(true);
                    return b;
                }
            }
        }
        // 非合并单元格
        return b;
    }

    /**
     * 根据单元格是否合并单元格，获取单元格值
     *
     * @param sheet       sheet
     * @param row         行
     * @param columnIndex 当前列
     * @return 单元格值
     */
    public static String getCellValue(Sheet sheet, Row row, int columnIndex) {
        if (sheet == null || row == null || columnIndex < 0) {
            return "";
        }
        // 获取当前单元格的合并信息
        POIUtilBean mergedRegionRows = ExcelUtil.getMergedRegionRows(sheet, row.getRowNum(), columnIndex);
        String value;
        if (mergedRegionRows.isMerged()) {
            value = ExcelUtil.getMergedRegionValue(sheet, row.getRowNum(), columnIndex);
        } else {
            Cell cell = row.getCell(columnIndex);
            value = getCellValue(cell);
        }
        return value;
    }

    /**
     * 设置列宽
     *
     * @param sheet sheet
     * @param width 宽度
     */
    public static void setColumnsWidth(XSSFSheet sheet, int... width) {
        for (int i = 0; i < width.length; i++) {
            sheet.setColumnWidth(i, width[i]);
        }
    }

    /**
     * 获取标题默认style
     *
     * @param wb 工作簿
     * @return 样式
     */
    public static XSSFCellStyle getDefaultTitleStyle(XSSFWorkbook wb) {
        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setWrapText(false);
        titleStyle.setFont(getDefaultTitleFont(wb));
        return titleStyle;
    }

    /**
     * 获取标题style - 带边框
     *
     * @param wb 工作簿
     * @return 样式
     */
    public static XSSFCellStyle getBorderTitleStyle(XSSFWorkbook wb) {
        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setWrapText(false);
        XSSFFont font = getDefaultTitleFont(wb);
        titleStyle.setFont(font);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        return titleStyle;
    }

    /**
     * 获取内容默认style
     *
     * @param wb 工作簿
     * @return 样式
     */
    public static XSSFCellStyle getDefaultCellStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(false);
        return cellStyle;
    }

    /**
     * 获取内容默认样式，自动换行
     *
     * @param wb 工作簿
     * @return 样式
     */
    public static XSSFCellStyle getDefaultCellStyle2(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        //cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    /**
     * 获取内容style - 带边框
     *
     * @param wb 工作簿
     * @return 样式
     */
    public static XSSFCellStyle getBorderCellStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        return cellStyle;
    }

    /**
     * 获取内容style
     *
     * @param wb                工作簿
     * @param alignment         水平样式，如：水平居中
     * @param verticalAlignment 垂直样式，如：垂直居中
     * @param wrapText          自动换行
     * @param font              字体样式
     * @return 内容样式
     */
    public static XSSFCellStyle getCellStyle(XSSFWorkbook wb, HorizontalAlignment alignment, VerticalAlignment verticalAlignment, Boolean wrapText, XSSFFont font) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        if (alignment != null) {
            cellStyle.setAlignment(alignment);
        }
        if (verticalAlignment != null) {
            cellStyle.setVerticalAlignment(verticalAlignment);
        }
        if (wrapText != null) {
            cellStyle.setWrapText(wrapText);
        }
        if (font != null) {
            cellStyle.setFont(font);
        }
        return cellStyle;
    }


    /**
     * 获取默认字体
     *
     * @param wb 工作簿
     * @return 字体样式
     */
    public static XSSFFont getDefaultTitleFont(XSSFWorkbook wb) {
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 13);
        return font;
    }

    /**
     * 设置单元格
     *
     * @param sheet       sheet
     * @param rowIndex    行索引
     * @param startColumn 开始列索引
     * @param cellStyle   单元格样式
     * @param values      单元格值
     */
    public static void setCell(XSSFSheet sheet, int rowIndex, int startColumn, XSSFCellStyle cellStyle, String... values) {
        XSSFRow row = getRow(sheet, rowIndex);
        setCellValue(row, cellStyle, startColumn, values);
    }

    public static void setCell(XSSFSheet sheet, int rowIndex, int startColumn, String... values) {
        XSSFRow row = getRow(sheet, rowIndex);
        setCellValue(row, null, startColumn, values);
    }

    private static XSSFRow getRow(XSSFSheet sheet, int rowIndex) {
        XSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        return row;
    }

    private static void setCellValue(XSSFRow row, XSSFCellStyle cellStyle, int startColumn, String... values) {
        int i = 0;
        for (String value : values) {
            XSSFCell cell = row.createCell(startColumn + i);
            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }
            cell.setCellValue(value);
            i++;
        }
    }

    /**
     * 设置单元格
     *
     * @param sheet          sheet
     * @param rowIndex       行索引
     * @param startColumn    开始列索引
     * @param cellStyle      单元格样式
     * @param heightInPoints 单元格高度
     * @param values         单元格值
     */
    public static void setCallWithHeightInPoints(XSSFSheet sheet, int rowIndex, int startColumn, XSSFCellStyle cellStyle, Integer heightInPoints, String... values) {
        XSSFRow row = sheet.createRow(rowIndex);
        row.setHeightInPoints(heightInPoints);
        int i = 0;
        for (String value : values) {
            XSSFCell cell = row.createCell(startColumn + i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(value);
            i++;
        }
    }

    /**
     * 设置单元格文件
     */
    public static void setCallFile(XSSFWorkbook wb, XSSFDrawing drawing, int rowIndex, int columnIndex, File file) throws Exception {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        //anchor主要用于设置图片的属性
        XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) columnIndex, rowIndex, (short) columnIndex + 1, rowIndex + 1);
        anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
        drawing.createPicture(anchor, wb.addPicture(inputStream, XSSFWorkbook.PICTURE_TYPE_JPEG));
    }

    /**
     * 获取内容在sheet中的位置
     *
     * @param sheet   sheet
     * @param content 内存
     * @param type    0:等于 1：包含
     * @return 结果
     */
    public static Map<String, Integer> getContentLocation(Sheet sheet, String content, int type) {
        // 位置map
        Map<String, Integer> map = new HashMap<>(4);
        for (int y = 0; y < sheet.getLastRowNum(); y++) {
            Row row = sheet.getRow(y);
            if (row == null) {
                continue;
            }
            for (int x = row.getFirstCellNum(); x < row.getLastCellNum(); x++) {
                Cell c = row.getCell(x);
                if (c == null) {
                    continue;
                }
                if (type == 0 && content.equals(c.getRichStringCellValue().toString())) {
                    map.put("Y", y);
                    map.put("X", x);
                    return map;
                } else if (type == 1 && c.getRichStringCellValue().toString().contains(content)) {
                    map.put("Y", y);
                    map.put("X", x);
                    return map;
                }
            }
        }
        return map;
    }

    /**
     * 获取内容列index
     */
    public static Integer getContentColumnIndex(Sheet sheet, String content, int type) {
        Map<String, Integer> contentLocation = getContentLocation(sheet, content, type);
        return contentLocation.get("X");
    }


    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet  工作表
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
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
        }
        return false;
    }


    /**
     * 如果excel是wps格式，获取合并单元格的cell时，cell会是null，此时不能用该方法，请用isMergedRegion(Sheet sheet, int row, int column)
     *
     * @param sheet
     * @param cell
     * @return
     * @description
     */
    public static boolean isMergedRegion(Sheet sheet, Cell cell) {
        int row = cell.getRowIndex();
        int column = cell.getColumnIndex();
        return isMergedRegion(sheet, row, column);
    }

    /**
     * 创建行并设置行高
     *
     * @param sheet  表
     * @param rowNum 行号
     * @param height 高度
     * @return 行
     */
    public static XSSFRow createRowAndSetHeight(XSSFSheet sheet, int rowNum, int height) {
        XSSFRow row = sheet.createRow(rowNum);
        row.setHeight((short) height);
        return row;
    }

    /**
     * 获取单元格并赋值
     *
     * @param row    行
     * @param colNum 列号
     * @param style  样式
     * @param value  值
     */
    public static void setCellValue(XSSFRow row, int colNum, XSSFCellStyle style, String value) {
        if (null != row && colNum >= 0) {
            XSSFCell cell = row.createCell((short) colNum);
            cell.setCellStyle(style);
            cell.setCellValue(value);
        }
    }

    /**
     * 获取单元格并赋值
     *
     * @param row    行
     * @param colNum 列号
     * @param style  样式
     * @param value  值
     */
    public static void setCellValue(XSSFRow row, int colNum, XSSFCellStyle style, RichTextString value) {
        if (null != row && colNum >= 0) {
            XSSFCell cell = row.createCell((short) colNum);
            cell.setCellStyle(style);
            cell.setCellValue(value);
        }
    }

    /**
     * 合并单元格
     *
     * @param sheet    表
     * @param firstRow 首行
     * @param lastRow  末行
     * @param firstCol 首列
     * @param lastCol  末列
     */
    public static CellRangeAddress mergeCell(XSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress cra = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(cra);
        return cra;
    }

}
