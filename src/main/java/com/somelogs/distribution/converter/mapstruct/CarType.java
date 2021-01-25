package com.somelogs.distribution.converter.mapstruct;

import lombok.Getter;

/**
 * 描述
 *
 * @author LBG - 1/25/21
 */
@Getter
public enum CarType {

	FOUR_G(4, "4g"),
	SIX_G(6, "6g"),
	EIGHT(8, "8g")
	;

	private int code;
	private String desc;

	CarType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
