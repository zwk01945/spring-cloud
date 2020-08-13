//package com.cloud.config.dynamicds;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.util.StringUtils;
//
///**************************************************************
// ***       S  T  A  G  E    多模块依赖项目                    ***
// **************************************************************
// *                                                            *
// *         Project Name : base                                *
// *                                                            *
// *         File Name : DataSourceContextHolder.java           *
// *                                                            *
// *         Programmer : Mr.zhang                              *
// *                                                            *
// *         Start Date : 2020/7/24 17:28                       *
// *                                                            *
// *         Last Update : 2020/7/24 17:28                      *
// *                                                            *
// *------------------------------------------------------------*
// * 功能:                                                       *
// *   多数据源容器,用于切换当前线程中的数据源                         *
// * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
//public class DataSourceContextHolder implements DisposableBean {
//    public static Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);
//    public static final String DEFAULT_DS="master";
//    public static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
//
//    public static void setDbType (String dbType) {
//        logger.info("切换数据库:" + dbType);
//        contextHolder.set(dbType);
//    }
//    public static String getDbType () {
//        String dbType = (String) contextHolder.get();
//        if (StringUtils.isEmpty(dbType)) {
//            dbType = DEFAULT_DS;
//        }
//        return dbType;
//    }
//
//    public static void clearDbType() {
//        contextHolder.remove();
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        contextHolder.remove();
//    }
//}
