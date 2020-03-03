package com.junya.util.result;

import java.io.Serializable;

/**
 * 用于返回相关信息的对象
 *
 * @Author ZHANGCHAO
 * @Date 2020/2/26 12:17
 * @retrun
 **/
@SuppressWarnings("unused")
public class ResponseMessage implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6504782430228638393L;
    /**
     * 状态
     */
    private Boolean success = false;
    /**
     * 状态码
     */
    private String code = "";
    /**
     * 消息说明
     */
    private String msg = "";
    /**
     *
     */
    private Object data;

    public ResponseMessage() {
    }

    public ResponseMessage(Boolean success) {
        this.success = success;
    }



    public ResponseMessage(Boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public ResponseMessage(Boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public ResponseMessage(Boolean success, String code, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
        /*if (success) {
            this.status = "200";
        }
        return this;*/
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
       /* return this;*/
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
        /*return this;*/
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
       /* if (null != this.success) this.success = true;
        if (null == this.status || "".equals(this.status)) this.status = "200";
        return this;*/
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
