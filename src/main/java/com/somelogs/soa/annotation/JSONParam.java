package com.somelogs.soa.annotation;

import java.lang.annotation.*;

/**
 * the annotation represents that argument is a JSON parameter,
 * and then custom argument resolver will deserialize
 * the parameter to specified Java bean.
 *
 * @author LBG - 2018/1/4 0004
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JSONParam {
}
