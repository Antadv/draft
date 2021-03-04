package com.somelogs.distribution.converter.mapstruct;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.Date;

/**
 * 描述
 *
 * @author LBG - 1/25/21
 */
public class MapStructTest {

	public static void main(String[] args) {
		Car car = new Car();
		car.setMake("ba");
		car.setNumberOfSeats(2);
		car.setType(CarType.FOUR_G);
		car.setCreateTime(new Date());
		car.setAmount(100);
		car.setUser(new UserEntity(23, "tim"));
		car.setWheelList(Lists.newArrayList(new Wheel("white")));
		CarDto carDto = CarConverter.INSTANCE.carToCarDto(car);
		System.out.println(JSON.toJSONString(carDto));
	}
}
