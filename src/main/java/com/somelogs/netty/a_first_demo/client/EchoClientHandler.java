package com.somelogs.netty.a_first_demo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 描述
 *
 * @author LBG - 2022/9/28
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	/**
	 * 将在一个连接建立时被调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
	}

	/**
	 * 每当接收数据时，都会调用这个方法。
	 *
	 * 需要注意的是：服务器发送的消息可能会被分块接收。
	 * 也就是说，如果服务器发送了 5 个字节，那么不能保证这个 5 个字节会被一次性接收。
	 * 即使是对于这么少量的数据，该方法也可能会调用两次。
	 *
	 * 作为一个面向流的协议，TCP 保证了字节数组将会按照服务器发送它们的顺序被接收。
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		// 记录已接收消息的转储
		System.out.println("Client received: " + msg.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 发生异常时，记录错误并关闭 Channel
		cause.printStackTrace();
		ctx.close();
	}
}
