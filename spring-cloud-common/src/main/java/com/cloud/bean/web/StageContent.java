package com.cloud.bean.web;

import java.io.Serializable;

/**
 * stage_content
 * @author 
 */
public class StageContent implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 文档一级标题
     */
    private String contentTitle;

    /**
     * 文档二级标题
     */
    private String contentSubTitle;

    /**
     * 文档代码
     */
    private String contentCode;

    /**
     * 图片地址
     */
    private String contentImage;

    /**
     * 分类ID
     */
    private Integer cateId;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentSubTitle() {
        return contentSubTitle;
    }

    public void setContentSubTitle(String contentSubTitle) {
        this.contentSubTitle = contentSubTitle;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }
}