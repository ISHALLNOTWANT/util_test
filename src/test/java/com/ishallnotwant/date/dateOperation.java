package com.ishallnotwant.date;

import cn.hutool.core.date.DateUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class dateOperation {

    /**
     * 时间格式转换
     * date格式、截取有用信息
     *
     */
    @Test
    public void test001() throws ParseException {
        Date date=new Date();                                           //获取当前时间
        System.out.println(date);                                       //:Tue Sep 13 17:48:38 CST 2022
        System.out.println(date.toString().substring(11,19));           //:17:53:11
        System.out.println(date.getDay());                              //获取指定日期是星期几， 0 1 2 3 4 5 6 日 一 二 三 ......
        System.out.println(getSearchTimeRange("2022-12-31",1));
        System.out.println(getSearchTimeRange("2022-12-31",2));
        System.out.println(getSearchTimeRange("2022-12-31",3));

        System.out.println(getSearchTimeRange("2022-9-23",1));
        System.out.println(getSearchTimeRange("2022-9-30",2));
        System.out.println(getSearchTimeRange("2022-9-30",3));

        System.out.println(getSearchTimeRange("2022-3-2",1));
        System.out.println(getSearchTimeRange("2022-3-31",2));
        System.out.println(getSearchTimeRange("2022-3-31",3));
    }
    @Test
    public void test007(){
        List<String> list =new ArrayList<>();
        for (String x:list){
            System.out.println("1");
        }
    }

    @Test
    public void test006(){
        String start="";
        String end="";

        Integer x=10;
        Integer y=5;

        Object z=y/x;

        BigDecimal zxz = new BigDecimal(1.2);

    }

    public void test0033(){

    }

    @Test
    public void test0012(){
        long x=1662051600000l;

        long y=1667232000000l; //2022-11-1
        long z=1667318399000l;//2022-11-1 23:59:59

        long cc=1664553599000l;
        Date v=new Date(cc);
        System.out.println(v);
    }

    public static Date toDate(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date =simpleDateFormat.parse(dateString);
        return date;
    }

    @Test
    public void getSuffix() throws ParseException {
        String date="2022-3-31";
        List<Date> dates=getSearchTimeRange(date,2);
        List<String> sufFix=new ArrayList<>();

        String startStr=toDateString(dates.get(0),"YYYY")+"_"+431;
        String endStr=toDateString(dates.get(1),"YYYY")+"_"+431;
        sufFix.add(startStr);
        sufFix.add(endStr);
        System.out.println(sufFix);
    }

    /**
     * 整理前端传过来的数据,获取查询时间范围，天：前7天；月：前6个月；季度：前4个季度
     *
     * @param endDate 结束日期
     * @param dateType 类型
     * @return 时间List<Date>  index 0-开始时间，1-结束时间
     */
    public List<Date> getSearchTimeRange(String endDate,Integer dateType) throws ParseException {
        List<Date> range = new ArrayList<>();
        Date startDate;
        endDate=endDate+" 23:59:59";
        //String endDateTimeStr=toDateString(toDate(endDate),"yyyy-MM-dd 23:59:59");
        Date endDateTime=toDate(endDate);
        System.out.println(endDate);
        switch (dateType) {
            case 1:
                startDate = new Date(endDateTime.getTime() - 6 * 24 * 3600000);
                break;
            case 2:
                startDate = getMouthStartDate(monthAddNum(endDateTime, -5));
                break;
            case 3:
                startDate = getFirstDateOfSeason(monthAddNum(endDateTime, -10));
                break;
            //case 4: 年 待处理
            default:
                startDate = endDateTime;
                break;
        }
        range.add(startDate);
        range.add(endDateTime);

        //System.out.println(toDateStr(startDate,"yyyy-MM-dd"));
        System.out.println("$"+endDateTime);
        return range;
    }

    /**
     * @param date    Date对象
     * @param pattern 时间格式
     * @return 日期字符串
     */
    public static String toDateString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        return getDateParser(pattern).format(date);
    }



    private static SimpleDateFormat getDateParser(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 指定时间的月份第一天
     *
     * @param date 指定时间
     * @return 日期
     */
    public static Date getMouthStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据时间类型时间返回时间字符串
     *
     * @param o            时间类 接受类型：Date、 Long(时间戳）
     * @param stringPatten 字符串样式
     * @return 时间字符串
     * @author wang zhen
     */
    public static String toDateStr(Object o, String stringPatten) {
        Date date;
        if (o instanceof Long) {
            date = new Date((Long) o);
        } else if (o instanceof Date) {
            date = (Date) o;
        } else {
            return "时间类型错误！！！";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(stringPatten);
        return dateFormat.format(date);
    }
    /**
     * 取得季度第一天
     *
     * @param date 时间
     * @return 取得季度第一天
     */
    public static Date getFirstDateOfSeason(Date date) {
        return getFirstDateOfMonth(getSeasonDate(date)[0]);
    }

    /**
     * @param date 时间
     * @return 取得季度月
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        int nSeason = getSeason(date);
        // 第一季度
        if (nSeason == 1) {
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
            // 第二季度
        } else if (nSeason == 2) {
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
            // 第三季度
        } else if (nSeason == 3) {
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
            // 第四季度
        } else if (nSeason == 4) {
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * @param date 时间
     * @return 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     */
    public static int getSeason(Date date) {
        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }


    /**
     * @param date 时间
     * @return 取得月第一天
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取近四个季度
     *
     * @param now
     * @return
     */
    public static List<String> getQuarter(Date now) {
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            dateList.add(getYear(now) + "-" + (getMonth(now) + 2) / 3);
            now = monthAddNum(now, -3);
        }
        return dateList;
    }

    /**
     * 获取日期的月份
     *
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的年
     *
     * @param date 日期
     * @return 年
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * @param time 时间
     * @param num  加的数，-num就是减去
     * @return 减去相应的数量的月份的日期
     */

    public static Date monthAddNum(Date time, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     * 指定字符串格式时间转换成date
     *
     */
    @Test
    public void test002() throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date =simpleDateFormat.parse("2022-08-21");
        System.out.println(date);

    }

    /**
     *
     * 获取指定日期所在时间范围内的最后时间
     */
    @Test
    public void test003(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        System.out.println(DateUtil.endOfMonth(date));
        System.out.println("本月结束时间" + DateUtil.endOfMonth(date).getTime());
        System.out.println(DateUtil.endOfWeek(date));
        System.out.println("本周结束时间" + DateUtil.endOfWeek(date).getTime());
        System.out.println(DateUtil.endOfQuarter(date));
        System.out.println("本季度结束时间" + DateUtil.endOfQuarter(date).getTime());
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void test004() throws ParseException {
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 =simpleDateFormat1.parse("2022-08-21 12:00:00");
        System.out.println(date1.getTime());

        SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 =simpleDateFormat2.parse("2022-08-22 12:05:07");
        System.out.println(date2.getTime());

        String diff=getTimeDifferenceStr(date2,date2);
        System.out.println(diff);

        String diff2=getTimeDifferenceStr(date1,date2.getTime());
        System.out.println(diff2);
    }
    /**
     * 返回两个时间的时间差，格式：”*天*时*分*秒“
     *
     * @param start 开始的时间类型 接受类型：Date、 Long(时间戳）
     * @param end   结束的时间类型 接受类型：Date、 Long(时间戳）
     * @return
     * @author wang zhen
     */
    public static String getTimeDifferenceStr(Object start, Object end) {
        Long startTime = 0L;
        Long endTime = 0L;
        if (start instanceof Date) {
            startTime = ((Date) start).getTime();
        } else if (start instanceof Long) {
            startTime = (Long) start;
        }
        if (end instanceof Date) {
            endTime = ((Date) end).getTime();
        } else if (end instanceof Long) {
            endTime = (Long) end;
        }
        if (startTime < endTime) {
            Long diff = endTime - startTime;
            Long diffSeconds = diff / 1000 % 60;
            Long diffMinutes = diff / (1000 * 60) % 60;
            Long diffHours = diff / (1000 * 60 * 60) % 24;
            Long diffDays = diff / (1000 * 60 * 60 * 24);
            StringBuilder differenceStr = new StringBuilder();
            if (diffDays != 0) {
                differenceStr.append(diffDays + "天");
            }
            if (diffHours != 0) {
                differenceStr.append(diffHours + "时");
            }
            if (diffMinutes != 0) {
                differenceStr.append(diffMinutes + "分");
            }
            if (diffSeconds != 0) {
                differenceStr.append(diffSeconds + "秒");
            }
            return differenceStr.toString();
        } else {
            return "时间格式不对或者结束时间至少大于开始时间！！！";
        }
    }

}
