package com.cloud.bean.web;

import lombok.Data;

import java.io.Serializable;

/**
 * stage_user
 * @author 
 */
public class StageUser implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    private Integer userId;

    private String userName;

    private String userPassword;

    private String userAddress;

    private String userPhone;

    private String userMail;

    private String dataYhbs;

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getDataYhbs() {
        return dataYhbs;
    }

    public void setDataYhbs(String dataYhbs) {
        this.dataYhbs = dataYhbs;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}