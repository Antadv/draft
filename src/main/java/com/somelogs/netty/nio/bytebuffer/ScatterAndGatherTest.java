package com.somelogs.netty.nio.bytebuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * channel 的集中分散操作。
 *
 * 场景：
 * 消息一般法分为 header 和 body，如果想把他们分别存放在不同的 buffer 中，
 * 那么用 scatter 就很合适。
 *
 *
 * @author LBG - 2022/10/11
 */
public class ScatterAndGatherTest {

	public static void main(String[] args) {
		//scatter();
		gather();
	}

	/**
	 * scatter 分散
	 */
	private static void scatter() {
		URL resource = ByteBufferTest.class.getClassLoader().getResource("nio/bytebuffer/scatter.txt");
		try (FileChannel channel = new FileInputStream(new File(resource.toURI())).getChannel()) {
			ByteBuffer buffer1 = ByteBuffer.allocate(4);
			ByteBuffer buffer2 = ByteBuffer.allocate(4);
			ByteBuffer buffer3 = ByteBuffer.allocate(6);
			channel.read(new ByteBuffer[]{buffer1, buffer2, buffer3});

			// 下面要读数据，需要转换的到读模式
			buffer1.flip();
			buffer2.flip();
			buffer3.flip();

			System.out.println(StandardCharsets.UTF_8.decode(buffer1));
			System.out.println(StandardCharsets.UTF_8.decode(buffer2));
			System.out.println(StandardCharsets.UTF_8.decode(buffer3));
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}
	}

	/**
	 * gather 集中
	 */
	private static void gather() {
		ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
		ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("world");
		ByteBuffer buffer3 = StandardCharsets.UTF_8.encode("你好");

		try (FileChannel channel = new RandomAccessFile("src/main/resources/nio/bytebuffer/gather.txt", "rw").getChannel()) {
			channel.write(new ByteBuffer[]{buffer1, buffer2, buffer3});
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}
	}
}
