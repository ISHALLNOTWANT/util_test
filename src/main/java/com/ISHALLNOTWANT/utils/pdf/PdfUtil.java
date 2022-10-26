package com.ISHALLNOTWANT.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggz
 * @version 1.0.0
 * @date 2020-12-22 15:39
 */
@Slf4j
public class PdfUtil {

    public static String USER_SESSION = "sessionVo";

    /**
     * 系统打印默认字体
     */
    public  static String PRINT_FONT = "/fonts/SIMSUN.TTF";

    /**
     * 全部Element
     */
    private List<Element> list;

    /**
     * margin in x direction starting from the left
     */
    private float marginLeft = 36;

    /**
     * margin in x direction starting from the right
     */
    private float marginRight = 36;

    /**
     * margin in y direction starting from the top
     */
    private float marginTop = 36;

    /**
     * margin in y direction starting from the bottom
     */
    private float marginBottom = 36;

    /**
     * 标题字体大小
     */
    private float titleFontSize = 8;
    /**
     * 正常字体大小
     */
    private float normalFontSize = 8;

    /**
     * 是否横向展示
     */
    protected boolean isHorizontal = false;

    /**
     * 行最小高度
     */
    private int minHeight = 20;

    /**
     * 当前操作的table
     */
    private PdfPTable t;

    public PdfUtil() {
        this.list = new ArrayList<>();
    }

    public PdfUtil(boolean isHorizontal) {
        this.list = new ArrayList<>();
        this.isHorizontal = isHorizontal;
    }

    public void setTitleFontSize(float titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    public void setNormalFontSize(float normalFontSize) {
        this.normalFontSize = normalFontSize;
    }

    public void createTable(int numColumns) {
        createTable(numColumns, 10, 10);
    }

    //spacingBefore:距离上部元素宽带
    public void createTable(int numColumns, float spacingBefore, float spacingAfter) {
        this.t = new PdfPTable(numColumns);
        this.t.setSpacingBefore(spacingBefore);
        this.t.setSpacingAfter(spacingAfter);
        t.setTotalWidth(520f);
        t.setLockedWidth(true);
        list.add(t);
    }

    public void setMargin(float marginLeft, float marginRight, float marginTop, float marginBottom) {
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
    }

    /**
     * 流下载
     *
     * @param outPutStream 输出流
     * @throws Exception 异常
     */
    public void downLoadPdf(OutputStream outPutStream) throws Exception {
        // 建立Document实例
        Document document = new Document(
                isHorizontal ? new RectangleReadOnly(842, 595) : PageSize.A4,
                marginLeft,
                marginRight,
                marginTop,
                marginBottom
        );
        PdfWriter.getInstance(document, outPutStream);
        document.open();
        if (CollectionUtils.isEmpty(list)) {
            createParagraph(" ", 10);
        }
        for (Element obj : list) {
            if (obj == null) {
                document.newPage();
            } else {
                document.add(obj);
            }
        }
        document.close();
    }

    /**
     * 标题字体
     */
    public Font titleFont() {
        return createFont(titleFontSize, Font.BOLD);
    }

    /**
     * 字体大小
     */
    public Font font() {
        return createFont(normalFontSize, Font.NORMAL);
    }

    /**
     * 字体大小 + 字体样式
     */
    public static Font createFont(float size, int style) {
        try {
            // 中文字体设置
            BaseFont bfChinese = BaseFont.createFont(PRINT_FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            return new Font(bfChinese, size, style);
        } catch (Exception e) {
            log.error("中文字体设置异常：", e);
            return new Font();
        }
    }

    /**
     * 创建新的页面
     */
    public void newPage() {
        list.add(null);
    }

    /**
     * 创建段略
     *
     * @param content 内容
     * @param size    字体大小
     */
    public void createParagraph(String content, float size) {
        Paragraph p = new Paragraph(content, createFont(size, Font.NORMAL));
        // 设置段落对齐方式
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(0f);
        p.setSpacingBefore(0f);
        list.add(p);
    }

    /**
     * 创建靠右的段落
     */
    public void createParagraphRight(String content, float size) {
        Paragraph p = new Paragraph(content, createFont(size, Font.NORMAL));
        // 设置段落对齐方式
        p.setAlignment(Element.ALIGN_RIGHT);
        p.setSpacingAfter(0f);
        p.setSpacingBefore(0f);
        list.add(p);
    }

    /**
     * 创建单元格
     *
     * @param content             内容
     * @param colspan             列合并
     * @param rowspan             行合并
     * @param minimumHeight       最小高度
     * @param font                字体
     * @param verticalAlignment   上下居中
     * @param horizontalAlignment 左右居中
     */
    private void createCell(String content, int colspan, int rowspan, int minimumHeight, Font font, int verticalAlignment, int horizontalAlignment) {
        if (t == null) {
            throw new RuntimeException("PdfPTable is null, please createTable");
        }
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setColspan(Math.max(colspan, 1));
        cell.setRowspan(Math.max(rowspan, 1));
        cell.setMinimumHeight(minimumHeight);
        cell.setVerticalAlignment(verticalAlignment);
        cell.setHorizontalAlignment(horizontalAlignment);
        t.addCell(cell);
    }

    /**
     * 创建无边框单元格
     *
     * @param content             内容
     * @param colspan             列合并
     * @param rowspan             行合并
     * @param minimumHeight       最小高度
     * @param font                字体
     * @param verticalAlignment   上下居中
     * @param horizontalAlignment 左右居中
     */
    private void createBorderDisplayCell(String content, int colspan, int rowspan, int minimumHeight, Font font, int verticalAlignment, int horizontalAlignment) {
        if (t == null) {
            throw new RuntimeException("PdfPTable is null, please createTable");
        }
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setColspan(Math.max(colspan, 1));
        cell.setRowspan(Math.max(rowspan, 1));
        cell.setMinimumHeight(minimumHeight);
        cell.setVerticalAlignment(verticalAlignment);
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.disableBorderSide(15);
        t.addCell(cell);
    }

    /**
     * 创建图片单元格
     */
    public void createImageCell(Image image, int fixedHeight) {
        if (t == null) {
            throw new RuntimeException("PdfPTable is null, please createTable");
        }
        PdfPCell cell = new PdfPCell();
        cell.setFixedHeight(fixedHeight);
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        cell.setImage(image);
        t.addCell(cell);
    }

    /**
     * 创建标题单元格
     */
    public void createTitleCell(String content) {
        createTitleCell(content, 1);
    }

    /**
     * 创建标题单元格
     * 设置字体
     */
    public void createTitleCell(String content, int colspan, int rowspan, int minimumHeight,float size){
        Font font=createFont(size,Font.BOLD);
        createCell(content,colspan,rowspan,minimumHeight,font,Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    //创建单元格
    public void createCell(String content,int colspan,int rowspan,float size){
        Font font=createFont(size,Font.NORMAL);
        createCell(content,colspan,rowspan,minHeight,font,Element.ALIGN_CENTER,Element.ALIGN_LEFT);
    }

    /**
     * 创建标题单元格 - 列合并
     */
    public void createTitleCell(String content, int colspan) {
        createCell(content, colspan, 1, minHeight, titleFont(), Element.ALIGN_MIDDLE, Element.ALIGN_LEFT);
    }

    /**
     * 创建标题单元格 - 列合并
     */
    public void createTitleCell(String content, int colspan, int minHeight) {
        createCell(content, colspan, 1, minHeight, titleFont(), Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    /**
     * 创建标题单元格 - 居中
     */
    public void createCenterTitleCell(String content) {
        createCenterTitleCell(content, 1);
    }

    /**
     * 创建标题单元格 - 居中
     */
    public void createBorderDisplayCenterTitleCell(String content) {
        createBorderDisplayCenterTitleCell(content, 1);
    }

    /**
     * 创建标题单元格 - 行合并，居中
     */
    public void createCenterTitleCell(int rowspan, String content) {
        createCenterTitleCell(content, 1, rowspan);
    }

    /**
     * 创建标题单元格 - 居中 - 列合并
     */
    public void createCenterTitleCell(String content, int colspan) {
        createCell(content, colspan, 1, minHeight, titleFont(), Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    /**
     * 创建无边框标题单元格 - 居中 - 列合并
     */
    public void createBorderDisplayCenterTitleCell(String content, int colspan) {
        createBorderDisplayCell(content, colspan, 1, minHeight, titleFont(), Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    /**
     * 创建标题单元格 - 居中 - 列合并
     */
    public void createCenterTitleCell(int minHeight, String content, int colspan) {
        createCell(content, colspan, 1, minHeight, titleFont(), Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    /**
     * 创建标题单元格 - 居中 - 列合并
     */
    public void createCenterTitleCell(String content, int colspan, int rowspan) {
        createCell(content, colspan, rowspan, minHeight, titleFont(), Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    /**
     * 创建标题单元格
     */
    public void createEmptyCell() {
        createCell("");
    }

    /**
     * 创建标题单元格
     */
    public void createEmptyCell(int colspan) {
        createCell("", colspan);
    }

    /**
     * 创建单元格
     */
    public void createCell(String content) {
        createCell(content, 1);
    }

    /**
     * 创建标题单元格 - 列合并
     */
    public void createCell(String content, int colspan) {
        createCell(content, colspan, minHeight);
    }

    /**
     * 创建标题单元格
     */
    public void createEmptyCell(int colspan, int minHeight) {
        createCell("", colspan, minHeight);
    }

    /**
     * 创建标题单元格 - 列合并
     */
    public void createCell(String content, int colspan, int minHeight) {
        createCell(content, colspan, 1, minHeight, font(), Element.ALIGN_MIDDLE, Element.ALIGN_LEFT);
    }

    /**
     * 创建单元格 - 无上下左右居中
     */
    public void createCellNoCenter(String content) {
        createCellNoCenter(content, 1);
    }

    /**
     * 创建标题单元格 - 列合并
     */
    public void createCellNoCenter(String content, int colspan) {
        createCellNoCenter(content, colspan, minHeight);
    }

    /**
     * 创建标题单元格 - 列合并
     */
    public void createCellNoCenter(String content, int colspan, int minHeight) {
        createCell(content, colspan, 1, minHeight, font(), Element.ALIGN_TOP, Element.ALIGN_LEFT);
    }

    /**
     * 创建标题单元格 - 居中
     */
    public void createCenterEmptyCell() {
        createTitleCell("", 1);
    }

    /**
     * 创建标题单元格 - 居中
     */
    public void createCenterCell(String content) {
        createCenterCell(content, 1);
    }

    /**
     * 创建标题单元格 - 居中 - 列合并
     */
    public void createCenterCell(String content, int colspan) {
        createCenterCell(content, colspan, minHeight);
    }

    /**
     * 创建标题单元格 - 居中 - 列合并
     */
    public void createBorderDisplayCenterCell(String content, int colspan) {
        createBorderDisplayCenterCell(content, colspan, minHeight);
    }

    /**
     * 创建标题单元格 - 居中 - 列合并
     */
    public void createUDCenterCell(String content, int colspan) {
        createUDCenterCell(content, colspan, minHeight);
    }



    /**
     * 创建标题单元格 - 列合并
     */
    public void createCenterCell(String content, int colspan, int minHeight) {
        createCell(content, colspan, 1, minHeight, font(), Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    /**
     * 创建标题单元格 - 列合并
     */
    public void createBorderDisplayCenterCell(String content, int colspan, int minHeight) {
        createBorderDisplayCell(content, colspan, 1, minHeight, font(), Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    /**
     * 创建标题单元格 - 列合并
     */
    public void createUDCenterCell(String content, int colspan, int minHeight) {
        createCell(content, colspan, 1, minHeight, font(), Element.ALIGN_MIDDLE, Element.ALIGN_LEFT);
    }

    /**
     * 创建标题单元格 - 行列合并
     */
    public void createCenterCell(String content, int colspan, int rowspan, int minHeight) {
        createCell(content, colspan, rowspan, minHeight, font(), Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    /**
     * 创建标题单元格 - 行列合并
     */
    public void createUDCenterCell(String content, int colspan, int rowspan, int minHeight) {
        createCell(content, colspan, rowspan, minHeight, font(), Element.ALIGN_MIDDLE, Element.ALIGN_LEFT);
    }

    /**
     * 创建单元格 - 行合并
     */
    public void createCellByRowspan(String content, int rowspan) {
        createCell(content, 1, rowspan, minHeight, font(), Element.ALIGN_TOP, Element.ALIGN_LEFT);
    }

    /**
     * 创建单元格 - 行合并 - 上下左右居中
     */
    public void createCellCenterByRowspan(String content, int rowspan) {
        createCell(content, 1, rowspan, minHeight, font(), Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    /**
     * 创建单元格 - 行列合并 - 上下左右居中
     */
    public void createCellCenterByRowspan(String content, int colspan, int rowspan) {
        createCell(content, colspan, rowspan, minHeight, font(), Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    /**
     * 创建单元格-行列合并-上下左右剧中-指定字体大小
     */
    public void createCellCenterSetFontSize(String content,int colspan,int rowspan,float size){
        Font font=createFont(size,Font.NORMAL);
        createCell(content,colspan,rowspan,minHeight,font,Element.ALIGN_MIDDLE,Element.ALIGN_CENTER);
    }
    /**
     * 创建单元格 - 行列合并 - 上下左右居中
     */
    public void createCellCenterByRowspan(String content, int colspan, int rowspan, int minHeight) {
        createCell(content, colspan, rowspan, minHeight, font(), Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
    }

    /**
     * 创建单元格 - 行列合并 - 上下居中
     */
    public void createCellUDCenterByRowspan(String content, int colspan, int rowspan) {
        createCell(content, colspan, rowspan, minHeight, font(), Element.ALIGN_MIDDLE, Element.ALIGN_LEFT);
    }

    /**
     * pdf加水印
     * @param srcPath 输入
     * @param destPath 输出
     * @param word 添加文字
     * @throws Exception
     */
    public static void addPDFWaterMark(String srcPath, String destPath, String word) throws Exception {
        PdfReader reader = new PdfReader(srcPath);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destPath));

        //创建字体，第一个参数是字体路径
        BaseFont base = BaseFont.createFont(PRINT_FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        PdfGState gs = new PdfGState();
        // 图片水印透明度
        gs.setFillOpacity(0.2f);
        PdfContentByte content;

        // pdf文件页数
        int total = reader.getNumberOfPages();
        for (int i = 0; i < total; i ++) {
            //页宽度
            float x = reader.getPageSize(i + 1).getWidth();
            //页高度
            float y = reader.getPageSize(i + 1).getHeight();
            content = stamper.getOverContent(i + 1);
            content.setGState(gs);
            content.beginText();
            //字体大小
            content.setFontAndSize(base, 20);
            //每页7行，一行3个
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 7; k++) {
                    // showTextAligned 方法的参数（文字对齐方式，位置内容，输出水印X轴位置，Y轴位置，旋转角度）
                    content.showTextAligned(Element.ALIGN_CENTER, word, x / 3 * j + 90, y / 7 * k, 25);
                }
            }
            content.endText();
        }
        //关闭流
        stamper.close();
        reader.close();
    }

}
