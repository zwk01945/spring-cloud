package com.cloud.bean.ohters;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Coustomer_zt表
 * @author Mr.zhang
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("coustomer_zt")
public class CoustomerZt implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("ID")
    private Integer id;

    @TableField("NAME")
    private String name;

    @TableField("JOB")
    private String job;

    @TableField("PROVINCE_FLAG")
    private Integer provinceFlag;

    @TableField("AGE")
    private Integer age;

    @TableField("LOCAL_ADDRESS")
    private String localAddress;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getProvinceFlag() {
        return provinceFlag;
    }

    public void setProvinceFlag(Integer provinceFlag) {
        this.provinceFlag = provinceFlag;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }
}
