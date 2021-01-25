package com.somelogs.distribution.converter.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 描述
 *
 * @author LBG - 1/25/21
 */
@Mapper
public interface CarConverter {

	CarConverter INSTANCE = Mappers.getMapper(CarConverter.class );

	@Mapping(source = "numberOfSeats", target = "seatCount")
	CarDto carToCarDto(Car car);
}
