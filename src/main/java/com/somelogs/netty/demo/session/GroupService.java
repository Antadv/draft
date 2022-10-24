package com.somelogs.netty.demo.session;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

/**
 * 描述
 *
 * @author LBG - 2022/10/21
 */
public interface GroupService {

	Group createGroup(String groupName, Set<String> memberSet);

	void removeGroup(String groupName);

	void addMember(String groupName, String memberName);

	String removeMember(String groupName, String memberName);

	Set<String> listMember(String groupName);

	List<Channel> listChannel(String groupName);
}
