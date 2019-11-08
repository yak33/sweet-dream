package com.smiletou.sweet.dream.result;

import java.io.Serializable;

/**
 * @author DWL 2017-07-27
 * @version 1.1.0
 * @description: 用于返回相关信息的对象
 * @copyright: Copyright (c) 2017 FFCS All Rights Reserved
 * @company: 济南悦码信息科技有限公司
 * @modifiedBy zxh
 * @history: 修改了set方法的返回值
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ResponseMessage implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6504782430228638393L;
    /**
     * 状态码
     */
    private String status = "";
    /**
     * 消息说明
     */
    private String msg = "";
    /**
     * 状态
     */
    private Boolean success = false;
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

    public ResponseMessage(Boolean success, String status, String msg, Object data) {
        this.success = success;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "{" + "\"status\":\"" +
                status + '\"' +
                ",\"msg\":\"" +
                msg + '\"' +
                ",\"success\":" +
                success +
                ",\"data\":" +
                data +
                '}';
    }
}
