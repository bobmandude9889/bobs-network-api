package net.bobmandude9889.api;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Connection {

	private Socket socket;
	private PrintStream out;
	private Scanner in;

	protected Connection(String ip, int port) throws UnknownHostException, IOException {
		socket = new Socket(ip, port);
		out = new PrintStream(socket.getOutputStream(), true);
		in = new Scanner(socket.getInputStream());
	}

	protected Connection(Socket socket) throws IOException{
		this.socket = socket;
		out = new PrintStream(socket.getOutputStream(), true);
		in = new Scanner(socket.getInputStream());
	}
	
	public boolean hasWaitingPacket() {
		return in.hasNextLine();
	}

	public Packet nextPacket() throws PacketException {
		return Packet.buildPacket(in.nextLine());
	}

	public void sendPacket(Packet packet) {
		String rawPacket = packet.serialize().toJSONString();
		out.println(rawPacket);
	}

	public void close() throws IOException {
		socket.close();
		out.close();
		in.close();
	}

	public Socket getTCPSocket() {
		return socket;
	}

}
