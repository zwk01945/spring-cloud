package com.cloud.bean;

/**
 * ClassName: DsProperties
 * Description:  数据连接条件参数
 * date: 2020/7/21 12:35
 * @author Mr.zhang
 */
public class DsProperties {
    private String dbName;
    private String jdbcUrl;
    private String username;
    private String password;


    public DsProperties() {
    }

    public DsProperties(String dbName, String jdbcUrl, String username, String password) {
        this.dbName = dbName;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
