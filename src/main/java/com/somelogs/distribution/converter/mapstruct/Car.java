package com.somelogs.distribution.converter.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 描述
 *
 * @author LBG - 1/25/21
 */
@Data
@AllArgsConstructor
public class Car {

	private String make;
	private int numberOfSeats;
	private CarType type;
}
