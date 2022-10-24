package com.somelogs.netty.hello.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 描述
 *
 * @author LBG - 2022/9/28
 */
public class EchoServer {

	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
		}

		int port = Integer.parseInt(args[0]);
		new EchoServer(port).start();
	}

	public void start() throws Exception {
		// 1. 创建 EventLoopEvent
		final EchoServerHandler serverHandler = new EchoServerHandler();
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			// 2. 创建 ServerBootstrap
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(group)
					// 3. 指定所使用的的 NIO 传输 Channel
					.channel(NioServerSocketChannel.class)
					// 4. 使用指定的端口设置套接字地址
					.localAddress(new InetSocketAddress(port))
					// 5. 添加一个 EchoServerHandler 到 ChannelPipeline
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// EchoServerHandler 被标注为 @Shareable，所以我们可以总是使用相同的实例
							ch.pipeline().addLast(serverHandler);
						}
			});

			// 6. 异步绑定服务器；调用 sync() 方法阻塞等待直到完成
			ChannelFuture future = bootstrap.bind().sync();
			// 7. 获取 Channel 的 closeFuture，并且阻塞当前线程直到它完成
			future.channel().closeFuture().sync();
		} finally {
			// 8. 关闭 EventLoopGroup，释放所有资源
			group.shutdownGracefully().sync();
		}
	}
}
