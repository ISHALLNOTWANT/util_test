package com.ishallnotwant.controller;


import com.ishallnotwant.dao.task;
import com.ishallnotwant.utils.pdf.PdfUtil;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    /**
     * pdf导出测试
     * @param httpServletResponse
     */
    @GetMapping("/001")
    public void export001(HttpServletRequest request,HttpServletResponse httpServletResponse) throws Exception {
        List<task> beforeWork=new ArrayList<>();
        List<task> betweenWork=new ArrayList<>();
        List<task> afterWork=new ArrayList<>();

        for(int i=0;i<9;i++){
            task taskBefore=new task(i+1,"营业前履职"+(i+1),"√","小明");
            task taskBetween=new task(i+10,"营业中履职"+(i+10),"√","小明");
            task taskAfter=new task(i+19,"营业后履职"+(i+19),"√","小明");
            beforeWork.add(taskBefore);
            betweenWork.add(taskBetween);
            afterWork.add(taskAfter);
        }

        System.out.println("前："+beforeWork);
        System.out.println("中："+betweenWork);
        System.out.println("后："+afterWork);

        PdfUtil pdf=new PdfUtil();
        //Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        pdf.createParagraph("安全日志检查", 18);
        pdf.createParagraphRight("2022 年 8月 30日 星期二", 14);
        pdf.setMargin(36, 36, 36, 10);

        //开始创建表格
        pdf.createTable(35);
        pdf.createTitleCell("检查内容", 26, 1, 20, 14f);
        pdf.createTitleCell("检查情况", 5, 1, 20, 14f);
        pdf.createTitleCell("检查人", 4, 1, 20, 14f);
        pdf.createCellCenterSetFontSize("营业前",2, 9,14);
        for(int i=0;i<9;i++){
            pdf.createCell(beforeWork.get(i).getTaskName(),24, 1, 14);
            pdf.createCell(beforeWork.get(i).getTaskResult(),5, 1, 14);
            pdf.createCell(beforeWork.get(i).getExecuteName(),4, 1, 14);
        }
        pdf.createCellCenterSetFontSize("营业中",2, 9,14);
        for(int i=0;i<9;i++){
            pdf.createCell(betweenWork.get(i).getTaskName(),24, 1, 14);
            pdf.createCell(betweenWork.get(i).getTaskResult(),5, 1, 14);
            pdf.createCell(betweenWork.get(i).getExecuteName(),4, 1, 14);
        }
        pdf.createCellCenterSetFontSize("营业后",2, 9,14);
        for(int i=0;i<9;i++){
            pdf.createCell(afterWork.get(i).getTaskName(),24, 1, 14);
            pdf.createCell(afterWork.get(i).getTaskResult(),5, 1, 14);
            pdf.createCell(afterWork.get(i).getExecuteName(),4, 1, 14);
        }
        pdf.createCellCenterSetFontSize("备注",2, 3,14);
        pdf.createCellCenterByRowspan("",33,3,60);
        pdf.createParagraph("注：检查情况正常打“√”；发现问题打“×”，并在备注中具体说明。",14);



       //String fileName = "安全日志检查" + "2022-8-30";
       /* httpServletResponse.setHeader("content-disposition", "attachment;filename=" + RequestUtil.getDownloadFileName(request, fileName + ".pdf"));
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/pdf");*/
        OutputStream outPutStream = httpServletResponse.getOutputStream();
        pdf.downLoadPdf(outPutStream);
    }
}
