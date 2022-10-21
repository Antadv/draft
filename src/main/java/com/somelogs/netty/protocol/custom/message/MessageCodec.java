package com.somelogs.netty.protocol.custom.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 自定义消息协议
 * <p>
 * A Codec for on-the-fly encoding/decoding of bytes to messages and vise-versa.
 * This can be thought of as a combination of ByteToMessageDecoder and MessageToByteEncoder.
 *
 * @author LBG - 2022/10/20
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {

	/**
	 * 编码 > 出站
	 *
	 * 魔数：用来在第一时间判定是否是无效数据包，例如 java class 文件前 4 个字节就是魔数 0xCAFEBABE
	 * 版本号：可以支持协议的升级
	 * 序列化算法：消息正文到底采用哪种序列化反序列化方式，可以由此扩展，例如 json、protobuf、hessian、jdk 等
	 * 指令类型：是登陆、注册、单聊、群聊...跟业务相关
	 * 请求序号：为了双工通信，提供异步能力
	 * 正文长度：
	 * 消息正文：
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		// 1. 魔数 4字节
		out.writeBytes(new byte[]{'d', 'd', 'w', 'x'});

		// 2. 版本 1 字节
		out.writeByte(1);

		// 3. 序列化算法 1 字节 如 jdk 0
		out.writeByte(0);

		// 4. 指令类型 1 字节：是登陆、注册、单聊、群聊...跟业务相关
		out.writeByte(msg.getMessageType());
		out.writeByte(0xff); // 无意义，纯粹是为了对齐

		// 5. 请求序号 4 字节
		out.writeInt(msg.getSequenceId());

		// 6. 获取内容的字节数组
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(msg);
		byte[] contentBytes = bos.toByteArray();

		// 7. 长度
		out.writeInt(contentBytes.length);

		// 8. 写入内容
		out.writeBytes(contentBytes);
	}

	/**
	 * 解码 > 进站
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// 按照上面编码的逻辑解码
		int magicNum = in.readInt();
		byte version = in.readByte();
		byte serializerType = in.readByte();
		byte messageType = in.readByte();
		in.readByte(); // 读取填充字节
		int sequenceId = in.readInt();
		int length = in.readInt();
		byte[] contentBytes = new byte[length];
		in.readBytes(contentBytes);

		if (serializerType == 0) {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(contentBytes));
			Message message = (Message) ois.readObject();

			log.info("{}, {}, {}, {}, {}, {}", magicNum, version, serializerType, messageType, sequenceId, length);
			log.info("{}", message);

			out.add(message);
		}
	}
}
