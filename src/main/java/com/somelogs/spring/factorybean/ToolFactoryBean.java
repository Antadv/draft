package com.somelogs.spring.factorybean;

import org.springframework.beans.factory.FactoryBean;

/**
 * 描述
 *
 * @author LBG - 2022/8/10
 */
public class ToolFactoryBean implements FactoryBean<Tool> {

	@Override
	public Tool getObject() throws Exception {
		return new Tool();
	}

	@Override
	public Class<?> getObjectType() {
		return Tool.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public static void main(String[] args) {

	}
}
