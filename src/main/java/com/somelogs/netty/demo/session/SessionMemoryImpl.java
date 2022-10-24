package com.somelogs.netty.demo.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述
 *
 * @author LBG - 2022/10/21
 */
public class SessionMemoryImpl implements Session {

	private final Map<String, Channel> usernameChannelMap = new ConcurrentHashMap<>();
	private final Map<Channel, String> channelUsernameMap = new ConcurrentHashMap<>();

	@Override
	public void bind(Channel channel, String username) {
		usernameChannelMap.put(username, channel);
		channelUsernameMap.put(channel, username);
	}

	@Override
	public void unbind(Channel channel) {
		String username = channelUsernameMap.remove(channel);
		usernameChannelMap.remove(username);
	}

	@Override
	public Channel getChannel(String username) {
		return usernameChannelMap.get(username);
	}
}
