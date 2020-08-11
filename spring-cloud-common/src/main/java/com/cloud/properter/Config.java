package com.cloud.properter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : Config.java                            *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/24 17:28                       *
 *                                                            *
 *         Last Update : 2020/7/24 17:28                      *
 *                                                            *
 *------------------------------------------------------------*
 * 功能:                                                       *
 *   系统sys.properties文件的资源读取类                           *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class Config {


    public static final String SYS_NAME_WIN = "win";
    public static final String SYS_NAME_LINUX = "linux";
    public static final String PROPERTY_FILE = "sys.properties";
    private static Config instance = null;
    private static final Properties paraProps = new Properties();

    public Config() {

        InputStream is = getClass().getResourceAsStream("/" + PROPERTY_FILE);

        try {

            paraProps.load(is);

        } catch (Exception e) {

            System.err.println("Can not read system.properties file!");

        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取实例
     */
    public static synchronized Config getInstance() {

        if (instance == null) {
            instance = new Config();
        }
        return instance;

    }

    /**
     * 获取属性
     * @param paraName key
     */
    public String getProperty(String paraName) {
        String property = "";
        try {
            property = paraProps.getProperty(paraName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return property;
    }

}
