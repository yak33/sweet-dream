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
@TableName("sys_resource")
public class SysResource {

    private static final long serialVersionUID = 1L;

    @TableId("UUID")
    private String uuid;

    /**
     * 资源编码
     */
    @TableField("code")
    private String code;

    /**
     * 资源名称
     */
    @TableField("name")
    private String name;

    /**
     * 排序号
     */
    @TableField("sortNo")
    private Integer sortNo;

    /**
     * 应用id（V2.0不使用）
     */
    @TableField("bizsysId")
    private String bizsysId;

    /**
     * 父id
     */
    @TableField("parentId")
    private String parentId;

    /**
     * 程序自动填充
     */
    @TableField("level")
    private Integer level;

    /**
     * 名称的拼音简写，程序自动填充
     */
    @TableField("abbName")
    private String abbName;

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
