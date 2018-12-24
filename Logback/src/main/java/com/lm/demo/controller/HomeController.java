package com.lm.demo.controller;

import com.lm.demo.annotation.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @ClassName HomeController
 * @Description TODO
 * @Author Admin
 * @DATE 2018/12/24 17:15
 **/
@RestController
@RequestMapping("/home")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Log("首页HomeController")
    @GetMapping("/log")
    public String home (String content) {
        LocalDateTime localDateTime = LocalDateTime.now();
        logger.trace("请求参数：content:{}" , content);
        logger.debug("请求参数：content:{}" , content);
        logger.warn("请求参数：content:{}" , content);
        logger.info("请求参数：content:{}" , content);
        logger.error("请求参数：content:{}" , content);
        return localDateTime + ", content:" + content;
    }

    @GetMapping("/test")
    public String testhome (String content) {
        LocalDateTime localDateTime = LocalDateTime.now();
        logger.trace("请求参数：content:{}" , content);
        logger.debug("请求参数：content:{}" , content);
        logger.warn("请求参数：content:{}" , content);
        logger.info("请求参数：content:{}" , content);
        logger.error("请求参数：content:{}" , content);
        return localDateTime + ", content:" + content;
    }

}
