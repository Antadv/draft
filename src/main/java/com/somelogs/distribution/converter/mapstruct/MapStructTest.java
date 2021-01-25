package com.somelogs.distribution.converter.mapstruct;

/**
 * 描述
 *
 * @author LBG - 1/25/21
 */
public class MapStructTest {

	public static void main(String[] args) {
		Car car = new Car("Morris", 5, CarType.FOUR_G);
		CarDto carDto = CarConverter.INSTANCE.carToCarDto(car);
		System.out.println(carDto);
	}
}
