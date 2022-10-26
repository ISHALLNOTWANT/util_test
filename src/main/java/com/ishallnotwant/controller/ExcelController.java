package com.ishallnotwant.controller;

import com.ishallnotwant.utils.excel.ExcelUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("excel")
public class ExcelController {
    /**
     * 基本导出
     * @param response
     */
    @GetMapping("/001")
    public void outPut001(HttpServletResponse response){
        // 表头数据
        List<Object> head = Arrays.asList("姓名","年龄","性别","头像");
        // 用户1数据
        List<Object> user1 = new ArrayList<>();
        user1.add("Jack");
        user1.add(22);
        user1.add("男");
        user1.add("C:......");
        // 用户2数据
        List<Object> user2 = new ArrayList<>();
        user2.add("大乔");
        user2.add(23);
        user2.add("女");
        user2.add("D:......");
        // 将数据汇总
        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(head);
        sheetDataList.add(user1);
        sheetDataList.add(user2);
        // 导出数据
        ExcelUtils.export(response,"用户表", sheetDataList);
    }

    /**
     *
     *导出将图片输出
     * @param response
     * @throws Exception
     */
    @GetMapping("/002")
    public void outPut002(HttpServletResponse response)throws Exception{
        // 表头数据
        List<Object> head = Arrays.asList("姓名","年龄","性别","头像");
        // 用户1数据
        List<Object> user1 = new ArrayList<>();
        user1.add("Jack");
        user1.add(22);
        user1.add("男");
        user1.add(new URL("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg-baofun.zhhainiao.com%2Ffs%2F7036314f7316d42eb0c9a94dbf2ec426.jpg&refer=http%3A%2F%2Fimg-baofun.zhhainiao.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1664006320&t=cbd162c10aba92ea93c5372116665f4d"));
        // 用户2数据
        List<Object> user2 = new ArrayList<>();
        user2.add("大乔");
        user2.add(23);
        user2.add("女");
        user2.add(new URL("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_360_360%2F29%2F7d%2Fff%2F297dff2dd679c5e22e08b81b66771716.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1664006504&t=8659c9ecd684ac1e01cde8dedd2d2879"));
        // 将数据汇总
        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(head);
        sheetDataList.add(user1);
        sheetDataList.add(user2);
        // 导出数据
        ExcelUtils.export(response,"用户表带图片", sheetDataList);
    }

    /**
     * 导出带下拉框的列
     * @param response
     */
    @GetMapping("/003")
    public void outPut003(HttpServletResponse response){
        // 表头数据
        List<Object> head = Arrays.asList("姓名","年龄","性别","城市");
        // 用户1数据
        List<Object> user1 = new ArrayList<>();
        user1.add("Jack");
        user1.add(22);
        user1.add("男");
        user1.add("上海");
        // 用户2数据
        List<Object> user2 = new ArrayList<>();
        user2.add("大乔");
        user2.add(23);
        user2.add("女");
        user2.add("北京");
        // 将数据汇总
        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(head);
        sheetDataList.add(user1);
        sheetDataList.add(user2);
        //设置
        // 导出数据设置下拉框（键值是多少就是第几列，从0开始）
        Map<Integer,List<String>> selectMap=new HashMap<>(1);
        selectMap.put(2,Arrays.asList("男","女"));
        selectMap.put(3,Arrays.asList("上海","北京","Xian","SanYa"));

        ExcelUtils.export(response,"用户表带下拉框", sheetDataList,selectMap);
    }

    /**
     * 导出带横向合并
     * @param response
     */
    @GetMapping("/004")
    public void outPut004(HttpServletResponse response){
        // 表头数据
        List<Object> head = Arrays.asList("姓名","年龄",ExcelUtils.COLUMN_MERGE,"性别","城市",ExcelUtils.COLUMN_MERGE);
        // 用户1数据
        List<Object> user1 = new ArrayList<>();
        user1.add("Jack");
        user1.add(22);
        user1.add("岁");
        user1.add("男");
        user1.add("上海");
        user1.add("浦东新区金桥");
        // 用户2数据
        List<Object> user2 = new ArrayList<>();
        user2.add("大乔");
        user2.add(23);
        user2.add("岁");
        user2.add("女");
        user2.add("北京");
        user2.add("朝阳区");
        // 将数据汇总
        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(head);
        sheetDataList.add(user1);
        sheetDataList.add(user2);
        //设置
        // 导出数据设置下拉框（键值是多少就是第几列，从0开始）
        Map<Integer,List<String>> selectMap=new HashMap<>(1);
        selectMap.put(2,Arrays.asList("男","女"));
        selectMap.put(3,Arrays.asList("上海","北京","Xian","SanYa"));

        ExcelUtils.export(response,"用户表横向合并", sheetDataList,selectMap);
    }

    /**
     * 导出带纵向合并
     * @param response
     */
    @GetMapping("/005")
    public void outPut005(HttpServletResponse response){
        // 表头数据
        List<Object> head = Arrays.asList("归属地","姓名","年龄","性别","城市",ExcelUtils.COLUMN_MERGE);
        // 用户1数据
        List<Object> user1 = new ArrayList<>();
        user1.add("中国");
        user1.add("Jack");
        user1.add(22);
        user1.add("男");
        user1.add("上海");
        user1.add("浦东新区金桥");
        // 用户2数据
        List<Object> user2 = new ArrayList<>();
        user2.add("吴国");
        user2.add("大乔");
        user2.add(23);
        user2.add("女");
        user2.add("北京");
        user2.add("朝阳区");

        List<Object> user3 = new ArrayList<>();
        user3.add(ExcelUtils.ROW_MERGE);
        user3.add("小乔");
        user3.add(23);
        user3.add("女");
        user3.add("北京");
        user3.add("朝阳区");
        // 将数据汇总
        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(head);
        sheetDataList.add(user1);
        sheetDataList.add(user2);
        sheetDataList.add(user3);
        //设置
        // 导出数据设置下拉框（键值是多少就是第几列，从0开始）
        Map<Integer,List<String>> selectMap=new HashMap<>(1);
        selectMap.put(2,Arrays.asList("男","女"));
        selectMap.put(3,Arrays.asList("上海","北京","Xian","SanYa"));

        ExcelUtils.export(response,"用户表纵向合并", sheetDataList,selectMap);
    }


}
