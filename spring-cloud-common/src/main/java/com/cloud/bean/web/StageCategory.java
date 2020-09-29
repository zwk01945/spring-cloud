package com.cloud.bean.web;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * stage_category
 * @author 
 */
public class StageCategory implements Serializable {
    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 分类名字
     */
    private String categoryName;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户标识
     */
    private String dataYhbs;

    private static final long serialVersionUID = 1L;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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