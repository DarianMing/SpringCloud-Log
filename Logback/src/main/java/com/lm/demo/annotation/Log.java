package com.lm.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 说明信息
     *
     * @return
     */
    String value() default "";

    /**
     * 是否忽略
     *
     * @return
     */
    boolean ignore() default false;
}
