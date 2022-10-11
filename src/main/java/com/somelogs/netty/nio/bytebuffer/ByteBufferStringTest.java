package com.somelogs.netty.nio.bytebuffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.somelogs.netty.nio.bytebuffer.ByteBufferPrintUtil.printPositionAndLimit;

/**
 * ByteBuffer 和 String 互转
 *
 * @author LBG - 2022/10/11
 */
public class ByteBufferStringTest {

	public static void main(String[] args) {
		// String 转 ByteBuffer

		// 1. 字符串转为 ByteBuffer
		ByteBuffer buffer1 = ByteBuffer.allocate(10);
		buffer1.put("hello".getBytes(StandardCharsets.UTF_8));
		printPositionAndLimit(buffer1); // position = 5，还处于写模式

		// 2. Charset
		ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
		printPositionAndLimit(buffer2); // position = 0，自动切换到读模式

		// 3. Wrap
		ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));
		printPositionAndLimit(buffer3); // position = 0，自动切换到读模式

		// ByteBuffer 转 String

		buffer1.flip(); // 如果 buffer1 不切换到读模式，将没数据
		System.out.println(StandardCharsets.UTF_8.decode(buffer1)); // ?
		System.out.println(StandardCharsets.UTF_8.decode(buffer2)); // hello
		System.out.println(StandardCharsets.UTF_8.decode(buffer3)); // hello
	}
}
