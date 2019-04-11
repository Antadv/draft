package com.somelogs.concurrent.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 描述
 *
 * @author LBG - 2018/12/21 0021
 */
public class FileChannelDemo {

    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream(new File("E:\\test.txt"));
        FileChannel channel = inputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("java nio test".getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }
}
