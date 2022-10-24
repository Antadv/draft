package com.somelogs.netty.demo.session;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

/**
 * 描述
 *
 * @author LBG - 2022/10/21
 */
public class GroupServiceMemoryImpl implements GroupService {



	@Override
	public Group createGroup(String groupName, Set<String> memberSet) {
		return null;
	}

	@Override
	public void removeGroup(String groupName) {

	}

	@Override
	public void addMember(String groupName, String memberName) {

	}

	@Override
	public String removeMember(String groupName, String memberName) {
		return null;
	}

	@Override
	public Set<String> listMember(String groupName) {
		return null;
	}

	@Override
	public List<Channel> listChannel(String groupName) {
		return null;
	}
}
