package com.somelogs.netty.demo.session;

import lombok.Data;

import java.util.Set;

/**
 * 描述
 *
 * @author LBG - 2022/10/21
 */
@Data
public class Group {

	private String groupName;
	private Set<String> memberList;
}
