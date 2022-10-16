package com.somelogs.netty.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 描述
 *
 * @author LBG - 2022/10/15
 */
public class HelloServer {

	public static void main(String[] args) {
		// 1. 启动器，负责组装 netty 组件，启动服务器
		new ServerBootstrap()
				// 2. 事件循环组
				.group(new NioEventLoopGroup())
				// 3. 选择服务器的 ServerSocketChannel 实现
				.channel(NioServerSocketChannel.class)
				// 4. Handles an I/O event or intercepts an I/O operation, and forwards it to its next handler in its ChannelPipeline.
				// 处理 IO 事件或者拦截 IO 操作，并向后传递给下一个Handler，内部有一个 ChannelPipeline.
				.childHandler(
						// A special ChannelInboundHandler which offers an easy way to initialize a Channel once it was registered to its EventLoop
						new ChannelInitializer<NioSocketChannel>() {
							@Override
							protected void initChannel(NioSocketChannel ch) throws Exception {
								// pipeline 内部是个链表
								ch.pipeline().addLast(new StringDecoder()); // 将 ByteBuf 转换为 String
								ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
									@Override
									public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
										System.out.println(msg);
									}
								});
							}
				})
				.bind(8080);
	}
}
