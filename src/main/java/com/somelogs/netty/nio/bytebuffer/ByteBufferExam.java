package com.somelogs.netty.nio.bytebuffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 使用 ByteBuffer 解决网络黏包、半包问题
 *
 * @author LBG - 2022/10/11
 */
public class ByteBufferExam {

	public static void main(String[] args) {
		/*
		* 原始数据有 3 条：
		* Hello,world\n
		* I'm tom\n
		* How are you?\n
		*
		* 实际网络传输时，由于某种原因，数据被重新组合了，如下：
		* Hello,world\nI'm tom\nHo
		* w are you?\n
		*
		* 现在需要将消息进行还原
		* */

		ByteBuffer buffer = ByteBuffer.allocate(50);
		buffer.put("Hello,world\nI'm tom\nHo".getBytes(StandardCharsets.UTF_8));
		split(buffer);

		buffer.put("w are you?\n".getBytes(StandardCharsets.UTF_8));
		split(buffer);
	}



	private static void split(ByteBuffer buffer) {
		buffer.flip();
		for (int i = 0; i < buffer.limit(); i++) {
			if (buffer.get(i) == '\n') { // 注意：\n 当作换行符时，算一个字符
				int length = i + 1 - buffer.position();
				ByteBuffer target = ByteBuffer.allocate(length);
				for (int j = 0; j < length; j++) {
					target.put(buffer.get());
				}
				target.flip(); // 这里注意切换到读模式
				System.out.println(StandardCharsets.UTF_8.decode(target));
			}
		}

		buffer.compact();
	}
}
