package com.cloud.bean.web;

import java.io.Serializable;
import java.util.Date;

/**
 * stage_route
 * @author 
 */
public class StageRoute implements Serializable {
    /**
     * 主键路由ID
     */
    private Integer routeId;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 路由地址URL
     */
    private String routeUrl;

    /**
     * 分类ID
     */
    private Integer cateId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否应用
     */
    private Byte isEffictive;

    /**
     * 路由图标
     */
    private String routeIcon;

    private static final long serialVersionUID = 1L;

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
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

    public Byte getIsEffictive() {
        return isEffictive;
    }

    public void setIsEffictive(Byte isEffictive) {
        this.isEffictive = isEffictive;
    }

    public String getRouteIcon() {
        return routeIcon;
    }

    public void setRouteIcon(String routeIcon) {
        this.routeIcon = routeIcon;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        StageRoute other = (StageRoute) that;
        return (this.getRouteId() == null ? other.getRouteId() == null : this.getRouteId().equals(other.getRouteId()))
            && (this.getRouteName() == null ? other.getRouteName() == null : this.getRouteName().equals(other.getRouteName()))
            && (this.getRouteUrl() == null ? other.getRouteUrl() == null : this.getRouteUrl().equals(other.getRouteUrl()))
            && (this.getCateId() == null ? other.getCateId() == null : this.getCateId().equals(other.getCateId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getIsEffictive() == null ? other.getIsEffictive() == null : this.getIsEffictive().equals(other.getIsEffictive()))
            && (this.getRouteIcon() == null ? other.getRouteIcon() == null : this.getRouteIcon().equals(other.getRouteIcon()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRouteId() == null) ? 0 : getRouteId().hashCode());
        result = prime * result + ((getRouteName() == null) ? 0 : getRouteName().hashCode());
        result = prime * result + ((getRouteUrl() == null) ? 0 : getRouteUrl().hashCode());
        result = prime * result + ((getCateId() == null) ? 0 : getCateId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getIsEffictive() == null) ? 0 : getIsEffictive().hashCode());
        result = prime * result + ((getRouteIcon() == null) ? 0 : getRouteIcon().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", routeId=").append(routeId);
        sb.append(", routeName=").append(routeName);
        sb.append(", routeUrl=").append(routeUrl);
        sb.append(", cateId=").append(cateId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", remark=").append(remark);
        sb.append(", isEffictive=").append(isEffictive);
        sb.append(", routeIcon=").append(routeIcon);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}