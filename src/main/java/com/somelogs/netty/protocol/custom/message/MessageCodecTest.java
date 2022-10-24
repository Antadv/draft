package com.somelogs.netty.protocol.custom.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * 描述
 *
 * @author LBG - 2022/10/20
 */
public class MessageCodecTest {

	public static void main(String[] args) throws Exception {
		EmbeddedChannel channel = new EmbeddedChannel(
				new LoggingHandler(),
				new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
				new MessageCodec());

		// encode
		LoginMessage message = new LoginMessage("zs", "123", "张三");
		//channel.writeOutbound(message);

		// decode
		ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
		new MessageCodec().encode(null, message, buf);
		//channel.writeInbound(buf);

		// 模拟黏包、半包
		ByteBuf s1 = buf.slice(0, 100);
		ByteBuf s2 = buf.slice(100, buf.readableBytes() - 100);
		s1.retain(); // 引用计数 2
		channel.writeInbound(s1);
		channel.writeInbound(s2);
	}
}
