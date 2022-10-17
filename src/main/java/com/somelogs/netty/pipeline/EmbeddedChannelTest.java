package com.somelogs.netty.pipeline;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * EmbeddedChannel： 专门用来测试 handler
 *
 * @author LBG - 2022/10/17
 */
@Slf4j
public class EmbeddedChannelTest {

	public static void main(String[] args) {

		// InBound

		ChannelInboundHandlerAdapter h1 = new ChannelInboundHandlerAdapter() {
			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				log.info("h1");
				super.channelRead(ctx, msg);
			}
		};

		ChannelInboundHandlerAdapter h2 = new ChannelInboundHandlerAdapter() {
			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				log.info("h2");
				super.channelRead(ctx, msg);
			}
		};

		ChannelInboundHandlerAdapter h3 = new ChannelInboundHandlerAdapter() {
			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				log.info("h3");
				super.channelRead(ctx, msg);
			}
		};

		// OutBound

		ChannelOutboundHandlerAdapter h4 = new ChannelOutboundHandlerAdapter() {
			@Override
			public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
				log.info("h4");
				super.write(ctx, msg, promise);
			}
		};

		// test

		EmbeddedChannel channel = new EmbeddedChannel(h1, h2, h3, h4);

		// 模拟进站操作
		//channel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("hello".getBytes(StandardCharsets.UTF_8)));
		// 模拟出站操作
		channel.writeOutbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("hello".getBytes(StandardCharsets.UTF_8)));
	}
}
