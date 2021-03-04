package com.somelogs.javase.socket.tcp;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * 描述
 *
 * @author LBG - 3/4/21
 */
public class TCPTest {

	@Test
	public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
		TCPClient client = new TCPClient();
		client.startConnection("127.0.0.1", 6666);
		String response = client.sendMessage("hello server");
		assertEquals("hello client", response);
	}
}
