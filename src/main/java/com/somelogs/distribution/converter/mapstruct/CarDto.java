package com.somelogs.distribution.converter.mapstruct;

import lombok.Data;

import java.util.List;

/**
 * 描述
 *
 * @author LBG - 1/25/21
 */
@Data
public class CarDto {

	private String make;
	private int seatCount;
	private String type;
	private String createTime;
	private String amount;
	private UserEntity user;
	private List<Wheel> wheelList;
}
