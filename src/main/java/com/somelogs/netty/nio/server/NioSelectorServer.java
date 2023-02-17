package com.somelogs.netty.nio.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO 服务端实现 2.0 版本：使用 Selector 事件监听
 */
@Slf4j
public class NioSelectorServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        serverSocketChannel.configureBlocking(false);

        /*
         * Selector 底层使用了 Linux 的 epoll 机制（epoll 是为了优化 c10k 问题的优化手段），
         * open() 方法其实就是调用操作系统函数 epoll_create 来创建 epoll 实例，
         * 所以 Selector 就是对操作系统创建的 epoll 对象的 Java 层面的封装
         *
         * 什么是 Channel：代表可以访问实体（硬件设备、文件、socket等）的连接，或者叫着媒介，从实体中读取数据，然后放到 buffer 中。
         * 通常每个 Channel 内部都会和操作系统的fd一一对应。
         *
         * 所以：
         * epoll 内部持有所有注册的fd集合，
         * selector 内部持有所有注册的channel的集合
         *
         * epoll 使用操作系统中断程序来感知事件的。
         */
        Selector selector = Selector.open();

        // 服务端监听连接建立事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        log.info("server startup!");

        while (true) {
            // 如果没有事件，这里会阻塞，直到有事件发生
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    // 当是 accept 事件，则拿到客户端 channel
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel sc = server.accept();

                    // 然后把客户端 channel 注册读事件，这样后续该客户端发送数据，selector 就能感知到
                    sc.configureBlocking(false);

                    // 这里为什么是读事件：当前是服务端程序，客户端发送数据通过 SocketChannel 传输，代表一个 socket，
                    // Channel 其中一个作用就是从实体（这里为网络socket）读数据，然后放到buffer中，所以是读事件。
                    sc.register(selector, SelectionKey.OP_READ);
                    log.info("client[{}] connected!", sc.socket().getInetAddress().toString());
                } else if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(128);
                    int len = sc.read(buffer);
                    if (len > 0) {
                        log.info("client[{}] send message {}",
                                sc.socket().getInetAddress(), new String(buffer.array()));
                    } else if (len == -1) {
                        log.info("client[{}] disconnected", sc.socket().getInetAddress());
                        sc.close();
                    }
                }

                // 事件处理完，需要把事件从selector事件集合中删除，防止重复处理
                iterator.remove();
            }

        }
    }
}
