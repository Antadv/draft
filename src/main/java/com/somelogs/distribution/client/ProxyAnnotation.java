package com.somelogs.distribution.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述
 * Created by liubingguang on 2017/5/15.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProxyAnnotation {
    String content() default "";
    String url() default "";
}
