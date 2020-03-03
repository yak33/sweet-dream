package com.junya.util.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义全局异常枚举
 *
 * @author ZhangChao
 * @date 2019/10/18 13:15
 * @since 1.0.0
 */
public enum GlobalErrorCodeEnum {

    /** 未知异常 */
    UNKNOWN_EXCEPTION("CSEP001","未知异常，请联系管理员"),
    /** 请求参数不存在 */
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION("CSEP002","请求参数不存在或拼写错误！"),
    /** 类转换异常 */
    CLASS_CAST_EXCEPTION("CSEP003","类型强制转换异常"),
    /** 算术条件异常 */
    ARITHMETIC_EXCEPTION("CSEP004","算术条件异常"),
    /** 空指针异常 */
    NULL_POINTER_EXCEPTION("CSEP005","空指针异常"),
    /** 字符串转换为数字异常 */
    NUMBER_FORMAT_EXCEPTION("CSEP006","字符串转换为数字异常"),
    /** 数组下标越界异常 */
    ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION("CSEP007","数组下标越界异常"),
    /** 方法未找到异常 */
    NO_SUCH_METHOD_EXCEPTION("CSEP008","方法未找到异常"),
    /** 未找到类定义错误 */
    NO_CLASS_DEF_FOUND_ERROR("CSEP009","未找到类定义错误"),
    /** 未找到类定义错误 */
    CLASS_NOT_FOUND_EXCEPTION("CSEP010","找不到类异常"),
    /** 索引越界异常 */
    INDEX_OUT_OF_BOUNDS_EXCEPTION("CSEP011","索引越界异常"),
    /** 数据库异常 */
    DB_ERROR("CSEP012","数据库异常")
    ;

    private String code;
    private String msg;

    GlobalErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
