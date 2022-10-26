package com.ishallnotwant.utils.excel2;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

/**
 * @author: bill09
 * @create: 2019/05/23
 **/
public class ExcelStyleUtils {


	// 主标题  
	public static XSSFCellStyle getMainStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();

		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);// 字号
		font.setFontName("宋体");
		font.setBold(true);// 加粗
		//mainFont.setColor(new XSSFColor(new java.awt.Color(255, 0, 0)));
		style.setFont(font);

		style.setBorderBottom(BorderStyle.THIN);// 下边框
		style.setBorderLeft(BorderStyle.THIN);// 左边框
		style.setBorderRight(BorderStyle.THIN);// 右边框
		style.setBorderTop(BorderStyle.THIN);// 上边框

		style.setWrapText(true);// 设置换行
		style.setAlignment(HorizontalAlignment.CENTER);// 左右居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
		return style;
	}

	// 合并表格标题
	public static XSSFCellStyle setBorderStyle(CellRangeAddress region, XSSFSheet sheet, XSSFWorkbook workbook) {
		XSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);

		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(false);
		style.setFont(font);
		// 左边框
		style.setBorderLeft(BorderStyle.THIN);
		// 下边框
		style.setBorderBottom(BorderStyle.THIN);
		// 上边框
		style.setBorderTop(BorderStyle.THIN);
		// 右边框
		style.setBorderRight(BorderStyle.THIN);
		setRegionStyle(style, region, sheet);
		return style;
	}

	private static void setRegionStyle(XSSFCellStyle cs, CellRangeAddress region, XSSFSheet sheet) {
		for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
			XSSFRow row = sheet.getRow(i);
			if (row == null) row = sheet.createRow(i);
			for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					cell = row.createCell(j);
					cell.setCellValue("");
				}
				cell.setCellStyle(cs);
			}
		}
	}
	//内容
	public static XSSFCellStyle getCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();

		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);// 字号
		font.setFontName("宋体");

		style.setBorderBottom(BorderStyle.THIN);// 下边框
		style.setBorderLeft(BorderStyle.THIN);// 左边框
		style.setBorderRight(BorderStyle.THIN);// 右边框
		style.setBorderTop(BorderStyle.THIN);// 上边框
		// 设置填充方案
		//style.setFillForegroundColor(new XSSFColor(new java.awt.Color(240,230,140)));
		//style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(font);

		style.setWrapText(true);// 设置换行
		style.setAlignment(HorizontalAlignment.CENTER);// 左右居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
		return style;
	}


}