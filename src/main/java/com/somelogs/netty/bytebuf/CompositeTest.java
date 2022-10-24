package com.somelogs.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static com.somelogs.netty.util.ByteBufLogUtil.log;

/**
 * Composite 组合的意思，也就是把多个 ByteBuf 组合成一个 ByteBuf
 *
 * @author LBG - 2022/10/17
 */
public class CompositeTest {

	public static void main(String[] args) {
		ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
		buffer.writeBytes(new byte[]{'a', 'b'});

		ByteBuf buffer2 = ByteBufAllocator.DEFAULT.buffer();
		buffer2.writeBytes(new byte[]{'c', 'd'});

		CompositeByteBuf compositeBuffer = ByteBufAllocator.DEFAULT.compositeBuffer();

		// 这个方法不会增加写指针
		//compositeBuffer.addComponents(buffer, buffer2);
		compositeBuffer.addComponents(true, buffer, buffer2);
		log(compositeBuffer);
	}
}
