package net.bobmandude9889.api;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionHandler {

	private List<Connection> connections;

	protected ConnectionHandler() {
		connections = new ArrayList<Connection>();
	}

	public int openConnection(String ip, int port, PacketHandler handler) throws UnknownHostException, IOException {
		Connection connection = new Connection(ip, port);
		connections.add(connection);
		if (handler != null) {
			handler.addConnection(connection);
		}
		return connections.size() - 1;
	}

	public int addConnection(Socket socket, PacketHandler handler) throws IOException {
		Connection connection = new Connection(socket);
		connections.add(connection);
		if (handler != null) {
			handler.addConnection(connection);
		}
		return connections.size() - 1;
	}

	public Connection getConnection(int i) {
		return connections.get(i);
	}

	public int getConnectionID(Connection connection) {
		if (connections.contains(connection))
			return connections.indexOf(connection);
		return -1;
	}

	public void close() throws IOException {
		for (Connection connection : connections) {
			connection.close();
		}
	}

}
