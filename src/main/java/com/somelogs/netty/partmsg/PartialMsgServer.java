package com.somelogs.netty.partmsg;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 黏包、半包现象
 *
 * @author LBG - 2022/10/17
 */
public class PartialMsgServer {

	public static void main(String[] args) {
		NioEventLoopGroup acceptor = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();

			// 接收缓冲区设置较小，容易发生半包现象（全局）
			//bootstrap.option(ChannelOption.SO_RCVBUF, 10);

			// 调整 netty 的接收缓冲区（ByteBuf）
			bootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(16, 16, 16));

			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.group(acceptor, worker);
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
				}
			});
			ChannelFuture future = bootstrap.bind(8080).sync();
			future.channel().closeFuture().sync();
		} catch(Exception e) {
		    e.printStackTrace();
		} finally {
			acceptor.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
}
