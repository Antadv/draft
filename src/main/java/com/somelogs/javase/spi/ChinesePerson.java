package com.somelogs.javase.spi;

/**
 * 描述
 *
 * @author LBG - 2020/9/17
 */
public class ChinesePerson implements Person {

	@Override
	public void sayHello() {
		System.out.println("你好！");
	}
}
