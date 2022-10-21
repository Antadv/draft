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
				new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
				new LoggingHandler(),
				new MessageCodec());

		// encode
		LoginMessage message = new LoginMessage("zs", "123", "张三");
		channel.writeOutbound(message);

		// decode
		ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
		new MessageCodec().encode(null, message, buf);
		channel.writeInbound(buf);
	}
}
