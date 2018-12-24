package com.lm.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//https://mp.weixin.qq.com/s?__biz=MzA3MTUzOTcxOQ==&mid=2452964308&idx=1&sn=56df7e457c61cb0092830087ee8949df&chksm=88edeebcbf9a67aa1ff96d3ebbb016c63859501cd1b0a25a10a5280d34547712bc4d6df4d25b&mpshare=1&scene=1&srcid=1216RdR9dVUMRdS8lmrndcOg#rd
/**
 * @ClassName ControllerInterceptor
 * @Description 自定义拦截器 logback requestUUID
 * @Author liming
 * @DATE 2018/12/24 16:49
 **/
public class ControllerInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

    //在请求处理之前回调方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUUID = MDC.get("requestUUID");
        if (requestUUID == null || "".equals(requestUUID)) {
            String uuid = UUID.randomUUID().toString();
            uuid = uuid.replaceAll("-" , "").toUpperCase();
            MDC.put("requestUUID" , uuid);
            logger.info("ControllerInterceptor preHandle 在请求处理之前生成 logback requestUUID:{}" , uuid);
        }
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    //请求处理之后回调方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MDC.remove("requestUUID");
        logger.info("ControllerInterceptor postHandle 请求处理之后清除 logback MDC requestUUID");
    }

    //整个请求处理完毕回调方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*整个请求线程结束后需要清除,否则当前线程会一直占用这个requestId值 */
        MDC.clear();
        logger.info("ControllerInterceptor afterCompletion 整个请求处理完毕清清除 logback MDC requestUUID");
    }
}
