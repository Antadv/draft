package com.somelogs.netty.demo.session;

import io.netty.channel.Channel;

/**
 * 描述
 *
 * @author LBG - 2022/10/21
 */
public interface Session {

	void bind(Channel channel, String username);

	void unbind(Channel channel);

	Channel getChannel(String username);
}
