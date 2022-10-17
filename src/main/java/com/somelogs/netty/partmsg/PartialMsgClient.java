package com.somelogs.netty.partmsg;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 描述
 *
 * @author LBG - 2022/10/17
 */
public class PartialMsgClient {

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			send();
		}
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
					ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
						@Override
						public void channelActive(ChannelHandlerContext ctx) throws Exception {
							// 1. 发 10 次
							//for (int i = 0; i < 10; i++) {
							//	ByteBuf buffer = ctx.alloc().buffer(16);
							//	buffer.writeBytes(new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});
							//	ctx.channel().writeAndFlush(buffer);
							//}

							// 2. 使用短连接的方式，发送完就关闭 channel
							ByteBuf buffer = ctx.alloc().buffer();
							buffer.writeBytes(new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17});
							ctx.channel().writeAndFlush(buffer);
							ctx.channel().close(); // 发送完就关闭 channel（模拟短连接）
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
