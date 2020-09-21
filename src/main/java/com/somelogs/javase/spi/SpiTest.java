package com.somelogs.javase.spi;

import java.util.ServiceLoader;

/**
 * Java SPI
 *
 * @author LBG - 2020/9/17
 */
public class SpiTest {

	public static void main(String[] args) {
		ServiceLoader<Person> loader = ServiceLoader.load(Person.class);
		System.out.println("Java SPI");
		//loader.forEach(Person::sayHello);
		for (Person person : loader) {
			person.sayHello();
		}
	}
}
