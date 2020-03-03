package com.junya.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangchao
 * @since 2020-03-03
 */
@Data
@TableName("sys_role")
public class SysRole {

    private static final long serialVersionUID = 1L;

    @TableId("UUID")
    private String uuid;

    /**
     * 角色编码
     */
    @TableField("code")
    private String code;

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 排序号
     */
    @TableField("sortNo")
    private Integer sortNo;

    /**
     * 角色类型
     */
    @TableField("roleType")
    private Integer roleType;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField("SysCreateDate")
    private Date SysCreateDate;

    @TableField("SysCreateBy")
    private String SysCreateBy;

    @TableField("SysUpdateDate")
    private Date SysUpdateDate;

    @TableField("SysUpdateBy")
    private String SysUpdateBy;

    @TableField("VersionNum")
    private Integer VersionNum;

    @TableField("SysDelFlag")
    private String SysDelFlag;

}
