package com.somelogs.netty.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * EventLoopGroup
 *
 * Netty 为每个 Channel 分配了一个 EventLoop，用于处理用户连接请求、对用户请求的处理等所有事件。
 * EventLoop 本身只是一个线程驱动，在其生命周期内只会绑定一个线程让该线程处理一个 Channel 的所有 IO 事件，
 * 这样 channel 只会由一个线程处理，也就自然线程安全
 *
 * 可以这么理解：
 * 	handler: 具体要执行的载体
 * 	EventLoop: 本身就是线程池，来执行 handler
 *
 * @author LBG - 2022/10/16
 */
@Slf4j
public class EventLoopServer {

	public static void main(String[] args) {

		// 给 Handler 创建一个单独的 EventLoopGroup
		EventLoopGroup group = new DefaultEventLoopGroup();

		new ServerBootstrap()
				// parentGroup(acceptor), childGroup(client)
				.group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						ch.pipeline().addLast("handler1", new ChannelInboundHandlerAdapter() {
							@Override
							public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
								ByteBuf buf = (ByteBuf) msg;
								log.info(buf.toString(Charset.defaultCharset()));
								ctx.fireChannelRead(msg); // 让消息传递给下一个 Handler
							}
						}).addLast(group, "handler2", new ChannelInboundHandlerAdapter() {
							@Override
							public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
								ByteBuf buf = (ByteBuf) msg;
								log.info(buf.toString(Charset.defaultCharset()));
							}
						});
					}
				}).bind(8080);

	}
}
