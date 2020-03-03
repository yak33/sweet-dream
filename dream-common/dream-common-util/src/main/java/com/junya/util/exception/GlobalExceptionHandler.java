package com.junya.util.exception;

import com.junya.util.result.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @program sweet-dream
 * @description: 自定义全局异常处理类
 * @author: zhangchao
 * @date: 2020/02/26 22:05
 * @since: 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常拦截处理
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResponseMessage exceptionHandler(HttpServletRequest request, Exception e){
        String requestURI = request.getRequestURI();//得到请求的URL地址
        String queryString = request.getQueryString();//得到请求的URL地址中附带的参数
        String remoteAddr = request.getRemoteAddr();//得到来访者的IP地址
        String method = request.getMethod();//得到请求URL地址时使用的方法
        //异常堆栈信息放入日志中
        ExceptionUtil.getFullStackTrace(e,requestURI);
        ResponseMessage responseMessage = new ResponseMessage();
        String eStr = e.toString();
        for (GlobalErrorCodeEnum global : GlobalErrorCodeEnum.values()){
            if (eStr.toUpperCase().contains(global.toString().replaceAll("_",""))){
                responseMessage.setCode(global.getCode());
                responseMessage.setMsg(global.getMsg());
                break;
            }
        }
        if (eStr.contains("GlobalException")){
            for (GlobalErrorCodeEnum statusCodeEnum : GlobalErrorCodeEnum.values()){
                if (eStr.contains(statusCodeEnum.getMsg())){
                    if (eStr.contains("Data too long for column")){
                        String field = eStr.replaceAll("[\\s\\S]+Data too long for column '(.+)' at[\\s\\S]+","$1");
                        responseMessage.setCode(statusCodeEnum.getCode());
                        responseMessage.setMsg(statusCodeEnum.getMsg()+"，字段长度超过限制："+field);
                        break;
                    }
                    if (eStr.contains("Unknown column")){
                        String field = eStr.replaceAll("[\\s\\S]+Unknown column '(.+)' in[\\s\\S]+","$1");
                        responseMessage.setCode(statusCodeEnum.getCode());
                        responseMessage.setMsg(statusCodeEnum.getMsg()+"，未知的字段："+field);
                        break;
                    }
                    if (eStr.contains("doesn't have a default value")){
                        String field = eStr.replaceAll("[\\s\\S]+Field '(.+)' doesn't have a default value[\\s\\S]+","$1");
                        responseMessage.setCode(statusCodeEnum.getCode());
                        responseMessage.setMsg(statusCodeEnum.getMsg()+"，此字段必须有值："+field);
                        break;
                    }
                    responseMessage.setCode(statusCodeEnum.getCode());
                    responseMessage.setMsg(statusCodeEnum.getMsg());
                    break;
                }
            }
        }
        if ("".equals(responseMessage.getCode()) || "".equals(responseMessage.getMsg())){
            responseMessage.setCode(GlobalErrorCodeEnum.UNKNOWN_EXCEPTION.getCode());
            responseMessage.setMsg(GlobalErrorCodeEnum.UNKNOWN_EXCEPTION.getMsg());
        }
        responseMessage.setData("{请求PATH:"+requestURI+"，请求参数:"+queryString+"，来访者IP:"+remoteAddr+"，请求方法类型:"+method+"}");
        return responseMessage;
    }

}
