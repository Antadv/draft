package com.somelogs.netty.future;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
		ChannelFuture future = new Bootstrap()
				.group(new NioEventLoopGroup())
				.channel(NioSocketChannel.class)
				// 添加处理器
				.handler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						ch.pipeline().addLast(new StringEncoder());
					}
				})
				// 异步非阻塞，执行 connect 是 nio 线程
				.connect(new InetSocketAddress("localhost", 8080));


		// 1. Waits for this future until it is done
		//future.sync();
		//Channel channel = future.channel();
		//channel.writeAndFlush("aaa");

		// 2. addListener
		future.addListener((ChannelFutureListener) future1 -> future1.channel().writeAndFlush("bbb"));
	}
}
