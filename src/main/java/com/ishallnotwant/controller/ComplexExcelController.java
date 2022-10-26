package com.ishallnotwant.controller;

import com.ishallnotwant.utils.excel2.ExcelStyleUtils;
import com.ishallnotwant.utils.excel2.RequestUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


/**
 * 拼接excel
 *  rowNum  : 合并后在视觉上它只算做一行，但是它却占据了x行，因此它下一行的应该是rowNum+x;
 */




@RestController
@RequestMapping("complex")
public class ComplexExcelController {
    @GetMapping("/001")
    public void exportExcel001(HttpServletRequest request, HttpServletResponse response) throws IOException {
        XSSFWorkbook wb=new XSSFWorkbook();
        XSSFSheet xssfSheet=null;
        XSSFRow xssfRow=null;
        XSSFCell xssfCell=null;

        String XLSX_NAME="测试一号";

        xssfSheet=wb.createSheet("测试001-sheet_01");
        xssfSheet.setDefaultColumnWidth(15);

        xssfSheet.setColumnWidth(1,252*15+2000);
        xssfSheet.setColumnWidth(1, 252 * 15 + 3000);

        XSSFCellStyle mainStyle= ExcelStyleUtils.getMainStyle(wb);//主标题
        XSSFCellStyle cellStyle=ExcelStyleUtils.getCellStyle(wb);//内容

        CellRangeAddress cra1=new CellRangeAddress(0,0,0,9);
        xssfSheet.addMergedRegion(cra1);

        int rowNum=0;

        //第一行
        xssfRow=xssfSheet.createRow(rowNum);
        xssfRow.setHeight((short) 1000);
        xssfCell=xssfRow.createCell(0);
        xssfCell.setCellValue("测试一号001");
        xssfCell.setCellStyle(mainStyle);
        ExcelStyleUtils.setBorderStyle(cra1,xssfSheet,wb);

        //第二行
        rowNum++;
        xssfRow=xssfSheet.createRow(rowNum);
        xssfCell=xssfRow.createCell(0);
        xssfCell.setCellValue("任务项你才可能擦叫我骗进去的气温平稳的");
        xssfCell.setCellStyle(cellStyle);
        CellRangeAddress cra2=new CellRangeAddress(1,3,0,2);
        xssfSheet.addMergedRegion(cra2);
        ExcelStyleUtils.setBorderStyle(cra2,xssfSheet,wb);

        CellRangeAddress cra3=new CellRangeAddress(1,1,3,6);
        xssfSheet.addMergedRegion(cra3);
        xssfCell=xssfRow.createCell(1);//index和行列的占了多少个单位不管，这就是依次第几个，从0开始
        xssfCell.setCellValue("8月");
        xssfCell.setCellStyle(cellStyle);
        ExcelStyleUtils.setBorderStyle(cra3,xssfSheet,wb);

        CellRangeAddress cra4=new CellRangeAddress(1,3,7,9);
        xssfSheet.addMergedRegion(cra4);
        xssfCell=xssfRow.createCell(2);
        xssfCell.setCellValue("右边");
        xssfCell.setCellStyle(cellStyle);
        ExcelStyleUtils.setBorderStyle(cra4,xssfSheet,wb);

        rowNum++;
        CellRangeAddress cra5=new CellRangeAddress(2,2,3,6);
        xssfSheet.addMergedRegion(cra5);
        xssfRow=xssfSheet.createRow(rowNum);
        xssfCell=xssfRow.createCell(1);
        xssfCell.setCellValue("9月");
        xssfCell.setCellStyle(mainStyle);
        ExcelStyleUtils.setBorderStyle(cra5,xssfSheet,wb);

        rowNum++;
        CellRangeAddress cra6=new CellRangeAddress(3,3,3,6);
        xssfSheet.addMergedRegion(cra6);
        xssfRow=xssfSheet.createRow(rowNum);
        xssfCell=xssfRow.createCell(1);
        xssfCell.setCellValue("10月");
        xssfCell.setCellStyle(mainStyle);
        ExcelStyleUtils.setBorderStyle(cra6,xssfSheet,wb);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-disposition", "attachment;filename="
        + RequestUtil.getDownloadFileName(request,XLSX_NAME+".xlsx"));
        response.setCharacterEncoding("utf-8");
        OutputStream outputStream= response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @GetMapping("/002")
    public void exportExcel002(HttpServletRequest request, HttpServletResponse response) throws Exception{
        XSSFWorkbook wb=new XSSFWorkbook();
        XSSFSheet xssfSheet=null;
        XSSFRow xssfRow=null;
        XSSFCell xssfCell=null;

        String XLSX_NAME="测试二号";

        xssfSheet=wb.createSheet("测试002-sheet_01");
        xssfSheet.setDefaultColumnWidth(15);

        XSSFCellStyle mainStyle= ExcelStyleUtils.getMainStyle(wb);//主标题
        XSSFCellStyle cellStyle=ExcelStyleUtils.getCellStyle(wb);//内容



        int rowNum=0;

        //第一行
        CellRangeAddress cra1=new CellRangeAddress(0,0,0,6);
        xssfSheet.addMergedRegion(cra1);
        xssfRow=xssfSheet.createRow(rowNum);
        xssfRow.setHeight((short) 1000);
        xssfCell=xssfRow.createCell(0);
        xssfCell.setCellValue("办公室1履职日统计表");
        xssfCell.setCellStyle(mainStyle);
        ExcelStyleUtils.setBorderStyle(cra1,xssfSheet,wb);

        rowNum++;

        /*CellRangeAddress cra2=new CellRangeAddress(1,1,0,0);
        xssfSheet.addMergedRegion(cra2);*/
        xssfRow=xssfSheet.createRow(rowNum);
        xssfCell=xssfRow.createCell(0);
        xssfCell.setCellValue("2022/08/02");
        xssfCell.setCellStyle(cellStyle);
        //ExcelStyleUtils.setBorderStyle(cra2,xssfSheet,wb);

        for(int i=1;i<=6;i++){
           /* CellRangeAddress cra=new CellRangeAddress(1,1,i,i);
            xssfSheet.addMergedRegion(cra);*/
            xssfCell=xssfRow.createCell(i);
            xssfCell.setCellValue("45");
            xssfCell.setCellStyle(cellStyle);
            //ExcelStyleUtils.setBorderStyle(cra,xssfSheet,wb);
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-disposition", "attachment;filename="
                + RequestUtil.getDownloadFileName(request,XLSX_NAME+".xlsx"));
        response.setCharacterEncoding("utf-8");
        OutputStream outputStream= response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }
}
