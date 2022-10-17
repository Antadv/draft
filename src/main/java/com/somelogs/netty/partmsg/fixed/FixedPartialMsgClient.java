package com.somelogs.netty.partmsg.fixed;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.lang3.RandomUtils;

/**
 * Netty
 *
 * @author LBG - 2022/10/17
 */
public class FixedPartialMsgClient {

	public static void main(String[] args) {
		send();
		System.out.println("finish");
	}

	private static byte[] fill10Bytes(char c, int len) {
		byte[] arr = new byte[10];
		for (int i = 0; i < 10; i++) {
			arr[i] = i < len ? (byte) c : (byte) '-';
		}
		System.out.println(new String(arr));
		return arr;
	}

	private static void send() {
		NioEventLoopGroup worker = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.group(worker);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
					ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
						@Override
						public void channelActive(ChannelHandlerContext ctx) throws Exception {
							char a = '0';
							ByteBuf buffer = ctx.alloc().buffer();
							for (int i = 0; i < 10; i++) {
								byte[] bytes = fill10Bytes(a, RandomUtils.nextInt(1, 10));
								buffer.writeBytes(bytes);
								a++;
							}
							ctx.channel().writeAndFlush(buffer);
						}
					});
				}
			});
			ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
			future.channel().closeFuture().sync();
		} catch(Exception e) {
		    e.printStackTrace();
		} finally {
			worker.shutdownGracefully();
		}
	}
}
