package com.somelogs.javase.socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 描述
 *
 * @author LBG - 3/4/21
 */
public class TCPServer {

	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public void start(int port) throws IOException {
		// Creates a server socket, bound to the specified port.
		// A port number of {@code 0} means that the port number is automatically allocated
		serverSocket = new ServerSocket(port);

		// Listens for a connection to be made to this socket and accepts it.
		// The method blocks until a connection is made
		clientSocket = serverSocket.accept();
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String greeting = in.readLine();
		if ("hello server".equals(greeting)) {
			out.println("hello client");
		} else {
			out.println("unrecognised greeting");
		}
	}

	public void stop() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
		serverSocket.close();
	}
	public static void main(String[] args) throws IOException {
		TCPServer server = new TCPServer();
		server.start(6666);
	}
}
