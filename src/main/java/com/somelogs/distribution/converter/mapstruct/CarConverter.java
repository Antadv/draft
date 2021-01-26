package com.somelogs.distribution.converter.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 描述
 *
 * @author LBG - 1/25/21
 */
@Mapper
public interface CarConverter {

	CarConverter INSTANCE = Mappers.getMapper(CarConverter.class);

	/**
	 * Mapping 用来处理属性名不一样的情况
	 *
	 * 一般情况下，MapStruct 对于以下情况可以做自动类型转换：
	 * 	- 基本类型及他们对应的包装类型
	 * 	- 基本类型的包装类型和 String 类型之间的转换
	 * 	- String 类型和枚举类型之间
	 */
	@Mappings({
			@Mapping(source = "numberOfSeats", target = "seatCount"), // 属性名不一样
			@Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss") // 日期格式转换
	})
	CarDto carToCarDto(Car car);
}
