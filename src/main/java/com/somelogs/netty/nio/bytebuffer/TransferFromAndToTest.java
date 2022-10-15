package com.somelogs.netty.nio.bytebuffer;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Transfer from & Transfer to
 *
 * @author LBG - 2022/10/12
 */
public class TransferFromAndToTest {

	/**
	 * transferFrom 和 transferTo 唯一的区别就是调用的对象不同，其他都一样
	 * 效率高，零拷贝
	 */
	public static void main(String[] args) {
		//transferFrom();
		transferTo();
	}

	private static void transferFrom() {
		try {
			RandomAccessFile fromFile = new RandomAccessFile("src/main/resources/nio/bytebuffer/transferFrom.txt", "rw");
			FileChannel fromChannel = fromFile.getChannel();

			RandomAccessFile toFile = new RandomAccessFile("src/main/resources/nio/bytebuffer/transferTo.txt", "rw");
			FileChannel toChannel = toFile.getChannel();

			long position = 0;
			long count = fromChannel.size();

			toChannel.transferFrom(fromChannel, position, count);
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}

	private static void transferTo() {
		try {
			RandomAccessFile fromFile = new RandomAccessFile("src/main/resources/nio/bytebuffer/transferFrom.txt", "rw");
			FileChannel fromChannel = fromFile.getChannel();

			RandomAccessFile toFile = new RandomAccessFile("src/main/resources/nio/bytebuffer/transferTo.txt", "rw");
			FileChannel toChannel = toFile.getChannel();

			long position = 0;
			long count = fromChannel.size();

			fromChannel.transferTo(position, count, toChannel);
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
