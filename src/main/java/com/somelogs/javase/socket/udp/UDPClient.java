package com.somelogs.javase.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 描述
 *
 * @author LBG - 3/4/21
 */
public class UDPClient extends Thread {

	private DatagramSocket socket;
	private InetAddress address;

	private byte[] buf;

	public UDPClient() {
		try {
			// Constructs a datagram socket and binds it to any available port on the local host machine
			socket = new DatagramSocket();
			address = InetAddress.getByName("localhost");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String sendEcho(String msg) throws IOException {
		buf = msg.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
		socket.send(packet);
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);
		return new String(packet.getData(), 0, packet.getLength());
	}

	public void close() {
		socket.close();
	}
}
