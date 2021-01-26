package com.somelogs.distribution.converter.mapstruct;

import lombok.Data;

import java.util.Date;

/**
 * 描述
 *
 * @author LBG - 1/25/21
 */
@Data
public class Car {

	private String make;
	private int numberOfSeats;
	private CarType type;
	private Date createTime;
}
