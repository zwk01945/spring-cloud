package com.cloud.bean.ohters;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ClassName: IcpCode
 * Description: ICP_CODE
 * date: 2020/7/21 15:25
 * @author Mr.zhang
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("icp_code")
public class IcpCode implements Serializable {

    @TableId("id")
    private Integer id;

    @TableField("name")
    private String name;

    public IcpCode(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public IcpCode() {
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
}
