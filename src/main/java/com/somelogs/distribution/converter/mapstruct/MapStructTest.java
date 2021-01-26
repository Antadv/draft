package com.somelogs.distribution.converter.mapstruct;

import com.alibaba.fastjson.JSON;

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
		CarDto carDto = CarConverter.INSTANCE.carToCarDto(car);
		System.out.println(JSON.toJSONString(carDto));
	}
}
