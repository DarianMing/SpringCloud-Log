package com.lm.demo.common;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lm.demo.annotation.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ClassName LogAspect
 * @Description 日志切面
 * @Author liming
 * @DATE 2018/12/24 17:07
 **/

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String STRING_START = "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n";
    private static final String STRING_END = "\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n";

    @Pointcut("execution(* com.lm.demo.controller..*(..))")
    public void serviceLog () {

    }

    @Around("serviceLog()")
    public Object around (ProceedingJoinPoint joinPoint){
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?> targetClass = method.getDeclaringClass();
            StringBuffer classAndMethod = new StringBuffer();
            Log classAnnotation = targetClass.getAnnotation(Log.class);
            Log methodAnnotation = method.getAnnotation(Log.class);
            if (classAnnotation != null) {
                if (classAnnotation.ignore()) {
                    return joinPoint.proceed();
                }
                classAndMethod.append(classAnnotation.value()).append("-");
            }
            if (methodAnnotation != null) {
                if (methodAnnotation.ignore()) {
                    return joinPoint.proceed();
                }
                classAndMethod.append(methodAnnotation.value()).append("-");
            }
            String target = targetClass.getName() + "#" + method.getName();
            String params = JSONObject.toJSONStringWithDateFormat(joinPoint.getArgs() , dateFormat , SerializerFeature.WriteMapNullValue);
            logger.info(STRING_START + "{} 开始调用--> {} 参数 ：{} ", classAndMethod.toString() , target , params);
            Long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            Long  timeConsuming = System.currentTimeMillis() - start;
            logger.info("\n{} 调用结束<-- {} 返回值:{} 耗时:{}ms" + STRING_END, classAndMethod.toString(), target, JSONObject.toJSONStringWithDateFormat(result, dateFormat, SerializerFeature.WriteMapNullValue), timeConsuming);
            return result;
        }catch (Throwable throwable){
            logger.error(throwable.getMessage() , throwable);
        }
        return null;
    }
}
