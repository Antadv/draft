package com.somelogs.netty.protocol.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

/**
 * HttpServerCodec Http 编码解码器
 *
 * @author LBG - 2022/10/18
 */
public class HttpProtocolServer {

	public static void main(String[] args) {
		NioEventLoopGroup acceptor = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.group(acceptor, worker);
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
					// 配置 http 编码解码器
					// A combination of HttpRequestDecoder and HttpResponseEncoder which enables easier server side HTTP implementation.
					ch.pipeline().addLast(new HttpServerCodec());
					// 只处理 HttpRequest 类型的消息
					// which allows to explicit(明确的) only handle a specific type of messages
					ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
						@Override
						protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
							// 需求：接收 http 请求，并响应 hello netty!

							DefaultFullHttpResponse response = new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);

							byte[] bytes = "<h1>Hello Netty!</h1>".getBytes(StandardCharsets.UTF_8);
							response.content().writeBytes(bytes);
							// 不设置长度的话，浏览器会一直请求，认为还有数据要读取
							response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, bytes.length);

							ctx.writeAndFlush(response);
						}
					});
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
