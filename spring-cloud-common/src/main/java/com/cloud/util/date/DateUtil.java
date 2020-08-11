package com.cloud.util.date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * ClassName: DateUtils
 * Description: 日期工具类
 * date: 2020/7/15 13:38
 * @author Mr.zhang
 */
public class DateUtil {


    /**
     * 指定日期转为字符串格式
     * @param date 日期
     * @param format 转换格式
     */
    public static String getStrByDate(Date date, String format) {
        return DateFormatUtils.format(date, format);
    }

    public static Date getDateByStr(String date,String format){
        try {
            return DateUtils.parseDateStrictly(date, Locale.TRADITIONAL_CHINESE, format);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String getYearWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        if (month == 12 && week == 1) {
            year += 1;
        }
        return year + "_" + week;

    }

    /**
     * 查询用“年-周”返回，根据第二参数判断当天是周几，并返回上一周的年-周数，否则为正常的年-周
     * @param date 日期
     * @param changeWeekDay 返回上周数据的周天，用0-6分别表示周日到周六
     */
    public static String getYearWeek(Date date,int changeWeekDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int weekDay=calendar.get(Calendar.DAY_OF_WEEK)-1;
        //判断是否给定日子，返回上周数据
        if (weekDay == changeWeekDay) {
            if (month == 12 && week == 1) {
                week = 52;
            }else if(month == 1 && week == 1) {
                week = 52;
                year -= 1;
            }else {
                week -= 1;
            }
        } else {
            if (month == 12 && week == 1) {
                year += 1;
            }
        }
        return year + "_" + week;
    }

    /**
     * 查询当天周数
     * @param date 日期
     * @return Calendar的周天常量值
     */
    public static int getYearWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 查询用“年-周”返回上周数据
     * @param date 上周日期
     * @author lsh
     */
    public static String getYearWeekPrevious(Date date) {
        Calendar calendar = Calendar.getInstance();
        int weekDay=calendar.get(Calendar.DAY_OF_WEEK)-1;
        return DateUtil.getYearWeek(date,weekDay);
    }
    /**
     * 获取过去第几天的日期
     * @param past 过去的天数
     */
    public static Date getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        return calendar.getTime();
    }




    public static void main(String[] args) {
        Date time = new Date();
        String dateToStr = DateUtil.getStrByDate(time, DateConstant.YYYY_MM_DD_HH_MM_SS);
        System.out.println(dateToStr);
    }




}
