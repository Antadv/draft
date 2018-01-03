package soa.annotation;

import java.lang.annotation.*;

/**
 * SOA 请求URL注解
 *
 * @author LBG - 2018/1/3 0003
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SOARequestURL {

    String value() default "";
}
