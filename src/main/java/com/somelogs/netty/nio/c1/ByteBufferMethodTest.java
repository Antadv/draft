package com.somelogs.netty.nio.c1;

import java.nio.ByteBuffer;

import static com.somelogs.netty.nio.c1.ByteBufferPrintUtil.printPositionAndLimit;
import static com.somelogs.netty.nio.c1.ByteBufferPrintUtil.printTitle;

/**
 * 描述
 *
 * @author LBG - 2022/10/11
 */
public class ByteBufferMethodTest {

	/**
	 * buffer 中 position 和 limit 的理解如下：
	 * 写模式：position 就是该写到哪个位置了（比如写了 3 字节，那么 position 就等于 3，从 0 开始），
	 * 			limit 表示是最多能写几个字节，那就是等于 capacity - 1
	 * 读模式：position 表示即将要读到的字节位置，limit 表示最多能读多少字节
	 */
	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		buffer.put(new byte[]{0x61, 0x62, 0x63, 0x64}); // a b c d

		// capacity 为容量，不会变
		System.out.println("capacity: " + buffer.capacity());

		// 写模式下，position 为写入字节的下一个位置索引，这里为 4
		printPositionAndLimit(buffer);

		// buffer 从写模式切换到读模式
		// position 重置为 0，limit 为能读到的最大值，为刚刚 position 的值
		buffer.flip();
		printTitle("写 >> 读");
		printPositionAndLimit(buffer);

		// 读一个字节，position 往后移一位
		byte b = buffer.get();
		printTitle("开始读数据...");
		System.out.println((char)b);
		printPositionAndLimit(buffer);

		// rewind() 会把 position 重置为0，也就是重新读数据
		buffer.rewind();
		printTitle("rewind 后开始读数据...");
		System.out.println(buffer.get()); // a
		printPositionAndLimit(buffer);

		// mark() and reset()，一般组合使用
		// mark() 是记住 position 的位置，调用 reset() 后，再把 position 设置为 mark 的值
		printTitle("开始使用 mark 和 rest 了");
		System.out.println((char)buffer.get()); // b
		printPositionAndLimit(buffer);
		buffer.mark(); // mark = position;
		System.out.println((char)buffer.get()); // c
		printPositionAndLimit(buffer);
		buffer.reset(); // set position = mark
		System.out.println((char)buffer.get()); // c
		printPositionAndLimit(buffer);
	}
}
