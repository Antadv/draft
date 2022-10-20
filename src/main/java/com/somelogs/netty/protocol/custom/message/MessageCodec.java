package com.somelogs.netty.protocol.custom.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * 描述
 *
 * @author LBG - 2022/10/20
 */
public class MessageCodec extends ByteToMessageCodec<Message> {

	/**
	 * 编码 > 出站
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		// 1. 魔数 4字节
		out.writeBytes(new byte[]{'d', 'd', 'w', 'x'});

		// 2. 版本 1 字节
		out.writeByte(1);

		// 3. 序列化算法 1 字节 如 jdk 0
		out.writeByte(0);

	}

	/**
	 * 解码 > 进站
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

	}
}
