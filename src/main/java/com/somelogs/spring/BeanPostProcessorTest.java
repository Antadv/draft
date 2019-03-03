package com.somelogs.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 描述
 *
 * @author LBG - 2019/3/2
 */
@Component
public class BeanPostProcessorTest implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        if (o instanceof BeanLifeCycle) {
            System.out.println("在init-method方法前调用...");
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (o instanceof BeanLifeCycle) {
            System.out.println("在init-method方法后调用...");
        }
        return o;
    }
}
