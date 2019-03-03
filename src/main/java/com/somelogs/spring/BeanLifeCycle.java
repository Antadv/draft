package com.somelogs.spring;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 的生命周期
 *
 * @author LBG - 2019/3/2
 */
public class BeanLifeCycle implements BeanNameAware {

    private String test;
    private String val4Processor;
    private String beanName;

    public String getVal4Processor() {
        return val4Processor;
    }

    public void setVal4Processor(String val4Processor) {
        this.val4Processor = val4Processor;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        System.out.println("test属性注入...");
        this.test = test;
    }

    public void initMethod() {
        System.out.println("initMethod被调用...");
    }

    public void destroyMethod() {
        System.out.println("destroyMethod被调用...");
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        BeanLifeCycle bean = (BeanLifeCycle) context.getBean("beanLifeCycle");
        System.out.println("beanName=" + bean.getBeanName());

        System.out.println("bean 实例化完成，容器开始关闭...");
        context.registerShutdownHook();
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("调用setBeanName，s = " + s);
        beanName = s;
    }

    public String getBeanName() {
        return beanName;
    }
}
