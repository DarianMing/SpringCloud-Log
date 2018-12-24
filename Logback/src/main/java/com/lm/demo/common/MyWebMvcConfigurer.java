package com.lm.demo.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName MyWebMvcConfigurer
 * @Description 拦截器配置
 * @Author liming
 * @DATE 2018/12/24 17:03
 **/
@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
          * 多个拦截器组成一个拦截器链
          * addPathPatterns 用于添加拦截规则
          * excludePathPatterns 用于排除拦截
        */
        registry.addInterceptor(new ControllerInterceptor()).addPathPatterns("/**");
    }
}

