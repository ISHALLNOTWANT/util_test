package com.ishallnotwant.utils.excel2;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * @author Administrator
 */
public class RequestUtil {

    public static String getServerPath(HttpServletRequest request) {
        return request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
    }

    public static String getContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    public static String getDownloadFileName(HttpServletRequest request, String fileName) {
        String ret = fileName;
        // 解决中文文件名乱码问题
        try {
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                // firefox浏览器
                ret = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                // IE浏览器
                ret = URLEncoder.encode(fileName, "UTF-8");
            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
                // 谷歌
                ret = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else {
                // 默认
                ret = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
