package com.somelogs.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.somelogs.netty.ByteBufLogUtil.log;

/**
 * ByteBuf 切片
 *
 * Slice 后的 ByteBuf 不是新生成的，只是使用了独立的读写指针，
 * 可以理解为视图，内存还是同一块内存
 *
 * @author LBG - 2022/10/17
 */
public class SliceTest {

	public static void main(String[] args) {
		ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);
		buffer.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
		log(buffer);

		//buffer.retain();

		ByteBuf s1 = buffer.slice(0, 5);
		s1.retain();

		// slice 后的 ByteBuf 不能再写入字节
		//s1.writeByte('x');

		ByteBuf s2 = buffer.slice(5, 5);
		s1.retain();
		//log(s1);
		//log(s2);

		// 释放原始 buffer 内存，slice 后的 ByteBuf 不能使用
		// 如果想要不被释放，可以先调用 retain()
		buffer.release();

		s1.setByte(0, 'b');
		log(buffer); // 改变 s1 内容，原始 buffer 内容也会变
		log(s1);
		log(s2);

		// 使用完，ByteBuf 分别 release
		s1.release();
		s2.release();
	}
}
