package com.junya.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangchao
 * @since 2019-08-11
 */
@Data
public class Carinfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String typeid;

    private String carnum;

    private Double ratedcarload;

    private Double selfcarload;

    private Double ratedcubage;

    private Double oilweight;

    private String cardriver;

    private String status;

    private String speedstatus;

    private Integer carcompanyid;

    private String pathid;

    private String worktime;

    private String carmodel;

    private String chezaitype;

    private Integer zuzhiid;

    private String gopathid;

    private String gpsnum;

    @TableField("tingcheId")
    private Integer tingcheId;

    @TableField("deviceId")
    private String deviceId;

    private String fenzu;

    private String oilstyle;

    private String tel;

    private String carpadcode;

    private Integer detailstype;

    private Integer workerid;

    private Integer ishuoer;

    private Integer isacc;

    private String waterstyle;

    private String videodeviceids;

    private Integer driverid;

    @TableField("IFID_CODE")
    private String ifidCode;

    @TableField("TENANT_ID")
    private Integer tenantId;

}
