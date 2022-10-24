package com.somelogs.netty.hello.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 描述
 *
 * @author LBG - 2022/9/28
 */
public class EchoClient {

	private final int port;
	private final String host;

	public EchoClient(int port, String host) {
		this.port = port;
		this.host = host;
	}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
					.channel(NioSocketChannel.class)
					.remoteAddress(new InetSocketAddress(host, port))
					.handler(new ChannelInitializer<SocketChannel>() {

						/**
						 * This method will be called once the {@link Channel} was registered. After the method returns this instance
						 * will be removed from the {@link ChannelPipeline} of the {@link Channel}.
						 */
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new EchoClientHandler());
						}
					});

			ChannelFuture future = b.connect().sync();
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}
}
