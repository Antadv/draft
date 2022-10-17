package com.somelogs.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 描述
 *
 * @author LBG - 2022/10/17
 */
@Slf4j
public class PooledByteBufTest {

	public static void main(String[] args) {
		/*
		 * 默认池化，可以用系统变量 io.netty.allocator.type 指定
		 * String allocType = SystemPropertyUtil.get(
		 *                 "io.netty.allocator.type", PlatformDependent.isAndroid() ? "unpooled" : "pooled");
		 */
		ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
		log.info("class is {}", buffer.getClass()); // PooledUnsafeDirectByteBuf

		log.info("{}", buffer);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 300; i++) {
			sb.append("a");
		}

		buffer.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8));
		log.info("{}", buffer);
	}
}
