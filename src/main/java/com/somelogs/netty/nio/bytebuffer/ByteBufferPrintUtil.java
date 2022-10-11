package com.somelogs.netty.nio.bytebuffer;

import java.nio.ByteBuffer;

/**
 * 描述
 *
 * @author LBG - 2022/10/11
 */
public class ByteBufferPrintUtil {

	public static void printTitle(String msg) {
		System.out.println();
		System.out.println(msg);
	}

	public static void printPositionAndLimit(ByteBuffer buffer) {
		System.out.println("position: " + buffer.position());
		System.out.println("limit: " + buffer.limit());
	}
}
