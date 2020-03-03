package com.junya.util.config.intercepors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author ZHANGCHAO
 * @date 2020/3/2 13:55
 * @since 1.0.0
 */
@Slf4j
//@Component
public class LoginIntercepor implements HandlerInterceptor {

    /**
     * 预处理回调方法，实现处理器的预处理。
     * 返回值：true表示继续流程；false表示流程中断，不会继续调用其他的拦截器或处理器。
     * 
     * @Author ZHANGCHAO
     * @Date 2020/3/2 14:02
     * @param 
     * @param request
     * @param response
     * @param handler
     * @retrun boolean     
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("---------------------------------------进入拦截器！---------------------------------------");
        log.info(request.getRequestURI());
//        log.info(String.valueOf(request.getRequestURL()));
//        log.info(request.getRemoteAddr());
        return false;
    }

    /**
     * 后处理回调方法，实现处理器（controller）的后处理，但在渲染视图之前
     * 此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理
     * 
     * @Author ZHANGCHAO
     * @Date 2020/3/2 14:03
     * @param 
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @retrun void     
     **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，
     * 如性能监控中我们可以在此记录结束时间并输出消耗时间，
     * 还可以进行一些资源清理，类似于try-catch-finally中的finally，
     * 但仅调用处理器执行链中
     *
     * @Author ZHANGCHAO
     * @Date 2020/3/2 14:03
     * @param
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @retrun void
     **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
