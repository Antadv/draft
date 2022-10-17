package com.somelogs.netty.pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

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
public class PipelineEventLoopServer {

	public static void main(String[] args) {
		new ServerBootstrap()
				.group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						// pipeline 中 handler 的数据结构，双向链表
						// head <> h1 <> h2 <> h3 <> h4 <> h5 <> h6 <> tail
						// addLast 并不是加在链表最后面，而是插在 tail 之前

						ChannelPipeline pipeline = ch.pipeline();

						pipeline.addLast("handler1", new ChannelInboundHandlerAdapter() {
							@Override
							public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
								log.info("h1");
								// 想要把数据传递给下一个 handler，需要调用此方法
								// 内部调用了 ctx.fireChannelRead(msg);
								super.channelRead(ctx, msg);
							}
						}).addLast("handler2", new ChannelInboundHandlerAdapter() {
							@Override
							public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
								log.info("h2");
								super.channelRead(ctx, msg);
							}
						}).addLast("handler4", new ChannelOutboundHandlerAdapter() {
							@Override
							public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
								log.info("4");
								super.write(ctx, msg, promise);
							}
						}).addLast("handler3", new ChannelInboundHandlerAdapter() {
							@Override
							public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
								log.info("h3");
								super.channelRead(ctx, msg);

								/*
								 * 想要执行出站 handler，必须要写数据
								 * ch.write() 和 ctx.write() 的区别:
								 *  ch.write() 会从 tail 往前找出站 handler（会执行所有的 OutBoundHandler）
								 * 	ctx.write() 只会从当前出站 handler 往前找出站 handler（只会执行在当前handler前面的 OutBoundHandler）
								 */

								//ch.writeAndFlush(ctx.alloc().buffer().writeBytes("server ...".getBytes()));
								ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("server ...".getBytes(StandardCharsets.UTF_8)));
							}
						}).addLast("handler5", new ChannelOutboundHandlerAdapter() {
							@Override
							public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
								log.info("5");
								super.write(ctx, msg, promise);
							}
						}).addLast("handler6", new ChannelOutboundHandlerAdapter() {
							@Override
							public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
								log.info("6");
								super.write(ctx, msg, promise);
							}
						});
					}
				}).bind(8080);

	}
}
