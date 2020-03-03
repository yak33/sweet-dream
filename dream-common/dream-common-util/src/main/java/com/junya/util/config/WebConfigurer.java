package com.junya.util.config;

import com.junya.util.config.intercepors.LoginIntercepor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ZHANGCHAO
 * @date 2020/3/2 13:51
 * @since 1.0.0
 */
//@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private LoginIntercepor loginIntercepor;

    /**
     * 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
     *
     * @Author ZHANGCHAO
     * @Date 2020/3/2 13:54
     * @param
     * @param registry
     * @retrun void
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        registry.addInterceptor(loginIntercepor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/register","/getCarinfoByTypeId");
    }

    /**
     * 主要配置静态资源，如HTML、CSS、JS等
     *
     * @Author ZHANGCHAO
     * @Date 2020/3/2 13:53
     * @param
     * @param registry
     * @retrun void
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }
}
