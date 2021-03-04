package com.somelogs.distribution.converter.mapstruct;

import lombok.Data;

import java.util.Date;
import java.util.List;

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
	private Integer amount;
	private UserEntity user;
	private List<Wheel> wheelList;
}
