package com.cloud.handler;

import com.cloud.util.date.DateConstant;
import com.cloud.util.date.DateUtil;
import org.springframework.format.Formatter;

import java.util.Date;
import java.util.Locale;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base             *
 *                                                            *
 *         File Name : DateFormatter.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/29 12:55                       *
 *                                                            *
 *         Last Update : 2020/7/29 12:55                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   请求参数日期格式化                                          *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class DateFormatter implements Formatter<Date> {


    @Override
    public Date parse(String s, Locale locale) {
        return DateUtil.getDateByStr(s, DateConstant.YYYY_MM_DD);
    }

    @Override
    public String print(Date date, Locale locale) {
        return DateUtil.getStrByDate(date, DateConstant.YYYY_MM_DD);
    }
}
