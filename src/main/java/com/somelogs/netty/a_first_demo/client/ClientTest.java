package com.somelogs.netty.a_first_demo.client;

/**
 * 描述
 *
 * @author LBG - 2022/9/29
 */
public class ClientTest {

	public static void main(String[] args) throws Exception {
		new EchoClient(8899, "127.0.0.1").start();
	}
}
