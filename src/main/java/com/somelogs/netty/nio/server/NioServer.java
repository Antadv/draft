package com.somelogs.netty.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * NIO 服务端实现 1.0 版本：把所有的客户端连接放到一个list中
 */
public class NioServer {
    // 客户端连接集合
    private static List<SocketChannel> channelList = new ArrayList<>();

    /**
     * 怎么理解 channel 和 socket 的关系
     */
    public static void main(String[] args) throws IOException {
        // 新建一个 socket channel
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        // socket() 拿到 channel 对应的 socket
        serverSocket.socket().bind(new InetSocketAddress(9000));
        // 配置 channel 为非阻塞
        serverSocket.configureBlocking(false);
        System.out.println("Server startup!");

        while (true) {
            // 接收客户端连接
            // 如果 ServerSocketChannel 配置为非阻塞，即使没有连接到来，accept() 方法会立即返回 null
            // 如果 ServerSocketChannel 配置为阻塞，没有可用连接时，accept() 方法会阻塞
            SocketChannel socketChannel = serverSocket.accept();
            if (socketChannel != null) {
                System.out.println("connect success");
                // 配置客户端 channel 为非阻塞，及时客户端没有数据，这里也不会阻塞
                socketChannel.configureBlocking(false);
                channelList.add(socketChannel);
            }

            // 把所有的连接放入 list 的做法会有个问题：如果有 10 万个连接，但只有一个连接有数据，为了
            // 读取该连接的数据，需要遍历所有的连接，性能极其低下。那能不能把有数据的连接单独放到一个list中，
            // 所以引入了 Selector
            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer buffer = ByteBuffer.allocate(128);
                int len = sc.read(buffer);
                if (len > 0) {
                    System.out.println("received msg: " + new String(buffer.array()));
                } else if (len == -1) {
                    iterator.remove();
                    System.out.println("client disconnected.");
                }
            }
        }
    }
}

