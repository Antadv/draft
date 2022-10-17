package com.somelogs.netty.pipeline;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.Scanner;

/**
 * 描述
 *
 * @author LBG - 2022/10/15
 */
@Slf4j
public class PipelineEventLoopClient {

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

		Channel channel = future.sync().channel();
		log.info("{}", channel);

		new Thread(() -> {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String line = scanner.nextLine();
				if (Objects.equals("q", line)) {
					channel.close();
					break;
				}
				channel.writeAndFlush(line);
			}
		}, "input").start();
	}
}
