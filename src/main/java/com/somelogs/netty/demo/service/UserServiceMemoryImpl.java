package com.somelogs.netty.demo.service;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述
 *
 * @author LBG - 2022/10/21
 */
public class UserServiceMemoryImpl implements UserService {

	private final Map<String, String> userMap = new ConcurrentHashMap<>();

	 {
		userMap.put("zs", "123");
		userMap.put("ls", "123");
		userMap.put("ww", "123");
		userMap.put("zl", "123");
		userMap.put("qq", "123");
	}

	@Override
	public boolean login(String username, String pwd) {
		String password = userMap.get(username);
		return StringUtils.isNotBlank(password) && Objects.equals(pwd, password);
	}
}
