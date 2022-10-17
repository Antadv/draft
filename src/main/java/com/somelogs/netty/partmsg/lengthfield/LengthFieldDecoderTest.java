package com.somelogs.netty.partmsg.lengthfield;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

/**
 * 描述
 *
 * @author LBG - 2022/10/17
 */
public class LengthFieldDecoderTest {

	public static void main(String[] args) {
		EmbeddedChannel channel = new EmbeddedChannel(
				/*
				 * int maxFrameLength > frame 的最大长度，如果大于这个值，会抛出 TooLongFrameException
				 * int lengthFieldOffset > 长度字段偏移量
				 * int lengthFieldLength > 长度字段的长度
				 * int lengthAdjustment > 以长度字段为基准，还有几个字节是内容
				 * int initialBytesToStrip > 从头剥离几个字节
				 */
				new LengthFieldBasedFrameDecoder(1024, 0, 4, 1, 5),
				new LoggingHandler(LogLevel.DEBUG)
		);

		ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
		write(buffer, "hello, world!");
		write(buffer, "Netty!");
		channel.writeInbound(buffer);
	}

	private static void write(ByteBuf buf, String content) {
		byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
		int length = bytes.length;
		buf.writeInt(length); // 内容长度加入占 4 个字节，int 刚好是 4 个字节
		buf.writeByte('a');
		buf.writeBytes(bytes); // 写入实际内容
	}
}
