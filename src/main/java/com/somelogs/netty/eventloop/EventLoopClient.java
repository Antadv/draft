package com.somelogs.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * 描述
 *
 * @author LBG - 2022/10/15
 */
public class EventLoopClient {

	public static void main(String[] args) throws InterruptedException {
		Channel channel = new Bootstrap()
				.group(new NioEventLoopGroup())
				.channel(NioSocketChannel.class)
				// 添加处理器
				.handler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						ch.pipeline().addLast(new StringEncoder());
					}
				}).connect(new InetSocketAddress("localhost", 8080))
				// 阻塞方法，直到连接建立
				.sync()
				// Returns a channel where the I/O operation associated with this future takes place.
				.channel();

		System.out.println(channel);
		// 断点调试
		System.out.println("");
	}
}
