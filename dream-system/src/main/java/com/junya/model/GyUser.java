package com.junya.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户表设置
 * </p>
 *
 * @author zhangchao
 * @since 2020-03-02
 */
@Data
@TableName("Gy_User")
public class GyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识ID
     */
    @TableId(value = "UUID",type = IdType.ASSIGN_UUID)
    private String uuid;

    /**
     * 用户编码
     */
    @TableField("UserCode")
    private String UserCode;

    /**
     * 用户名称
     */
    @TableField("UserName")
    private String UserName;

    /**
     * 用户密码
     */
    @TableField("PassWord")
    private String PassWord;

    /**
     * 联系方式
     */
    @TableField("Mobile")
    private String Mobile;

    /**
     * 科室编码
     */
    @TableField("DeptCode")
    private String DeptCode;

    /**
     * HIS用户对应编码
     */
    @TableField("HISUserCode")
    private String HISUserCode;

    /**
     * 备注
     */
    @TableField("Remark")
    private String Remark;

    /**
     * 用户身份标识
     */
    @TableField("IdentityFlag")
    private Boolean IdentityFlag;

    /**
     * 创建时间
     */
    @TableField("SysCreateDate")
    private Date SysCreateDate;

    /**
     * 创建者
     */
    @TableField("SysCreateBy")
    private String SysCreateBy;

    /**
     * 更新时间
     */
    @TableField("SysUpdateDate")
    private Date SysUpdateDate;

    /**
     * 更新者
     */
    @TableField("SysUpdateBy")
    private String SysUpdateBy;

    /**
     * 版本号
     */
    @TableField("VersionNum")
    private Integer VersionNum;

    /**
     * 删除标识
     */
    @TableField("SysDelFlag")
    private String SysDelFlag;

    /**
     * 别名
     */
    @TableField("alias")
    private String alias;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 开始时间
     */
    @TableField("startDate")
    private Date startDate;

    /**
     * 结束时间
     */
    @TableField("endDate")
    private Date endDate;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

}
