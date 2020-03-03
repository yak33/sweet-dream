package com.junya.util.exception;

import com.junya.util.result.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 保存异常堆栈信息
 * -- 两种方式 Throwable和Exception
 *
 * @author ZhangChao
 * @date 2019/9/6 9:56
 * @since 1.0.0
 */
public class ExceptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    /**
     * 异常堆栈信息保存到日志中
     * @param ex
     */
    public static void getFullStackTrace(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        ex.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception e) {
        }
        ex.printStackTrace();
        logger.error(ret);
    }

    /**
     * 异常堆栈信息保存到日志中，并保存消息记录
     * @param ex
     * @param msg
     */
    public static void getFullStackTrace(Exception ex, String msg) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        ex.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception e) {
        }
        ex.printStackTrace();
        logger.error("出现异常==>: "+msg+" ==> \n"+ret);
//        logger.error(ret);
    }

    /**
     * 参数是Throwable
     * @param e
     * @return
     */
    public static void getFullStackTrace(Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        try {
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            logger.error(sw.toString());
        } finally {
            pw.close();
        }
    }

    /**
     * 封装异常类
     * @param e
     * @return
     */
    public static ResponseMessage ResExHandle(Throwable e){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setSuccess(false);
        String eStr = e.toString();
        for (GlobalErrorCodeEnum global : GlobalErrorCodeEnum.values()){
            if (eStr.toUpperCase().contains(global.toString().replaceAll("_",""))){
                responseMessage.setCode(global.getCode());
                responseMessage.setMsg(global.getMsg());
                break;
            }
        }
        //如果是以前封装的那个YmException异常
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
        return responseMessage;
    }

}
