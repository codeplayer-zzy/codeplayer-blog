package com.codeplayer.config;

import com.codeplayer.interceptor.LoginHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* @description: 配置MVC相关信息
* @author CodePlayer
* @date 2021/4/7 21:23
*/
@Configuration
public class MvcConfig implements WebMvcConfigurer{
    @Autowired
    private LoginHandlerInterceptor loginHandlerInterceptor;

    /**
     * 注册登录拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginHandlerInterceptor)
                // 拦截的请求
                .addPathPatterns("/**");
    }
}
