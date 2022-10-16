package com.somelogs.netty.nio.socketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 描述
 *
 * @author LBG - 2022/10/15
 */
public class MultiThreadServer {

	public static void main(String[] args) {
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);

			Selector connSelector = Selector.open();
			serverSocketChannel.register(connSelector, SelectionKey.OP_ACCEPT);


			serverSocketChannel.bind(new InetSocketAddress(8080));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
