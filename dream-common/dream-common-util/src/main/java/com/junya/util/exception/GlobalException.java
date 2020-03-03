package com.junya.util.exception;

/**
 * 自定义全局异常类
 *
 * @author ZhangChao
 * @date 2019/10/18 13:23
 * @since 1.0.0
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 6958499252468627021L;

    /**
     * 错误码
     */
    private String code;

    public GlobalException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public GlobalException(GlobalErrorCodeEnum errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public GlobalException(GlobalErrorCodeEnum errorCode, String msg){
        super(errorCode.getMsg()+msg);
        this.code = errorCode.getCode();
    }

    public GlobalException(String code, String msg, Throwable throwable){
        super(msg,throwable);
        this.code = code;
    }
    public GlobalException(GlobalErrorCodeEnum errorCode, Throwable throwable){
        super(errorCode.getMsg(),throwable);
        this.code = errorCode.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
