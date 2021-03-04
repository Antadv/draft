package com.somelogs.javase.socket.udp;

import lombok.SneakyThrows;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 描述
 *
 * @author L- 3/4/21
 */
public class UDPServer extends Thread {

	private DatagramSocket socket;
	private boolean running;
	private byte[] buf = new byte[256];

	public UDPServer() {
		try {
			socket = new DatagramSocket(4445);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@SneakyThrows
	public void run() {
		running = true;
		while (running) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			// This method blocks until a datagram is received
			socket.receive(packet);

			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			packet = new DatagramPacket(buf, buf.length, address, port);
			String received = new String(packet.getData(), 0, packet.getLength());

			if (received.equals("end")) {
				running = false;
				continue;
			}
			socket.send(packet);
		}
		socket.close();
	}
}
