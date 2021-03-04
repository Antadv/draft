package com.somelogs.javase.socket.udp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 描述
 *
 * @author LBG - 3/4/21
 */
public class UDPTest {

	UDPClient client;

	@Before
	public void setup() {
		new UDPServer().start();
		client = new UDPClient();
	}

	@Test
	public void whenCanSendAndReceivePacket_thenCorrect() throws IOException {
		String echo = client.sendEcho("hello server");
		assertEquals("hello server", echo);
		echo = client.sendEcho("server is working");
		assertFalse(echo.equals("hello server"));
	}

	@After
	public void tearDown() throws IOException {
		client.sendEcho("end");
		client.close();
	}
}
