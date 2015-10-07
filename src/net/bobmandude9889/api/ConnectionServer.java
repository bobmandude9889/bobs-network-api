package net.bobmandude9889.api;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectionServer {

	List<Connection> connections;
	PacketHandler packetHandler;
	Thread thread;
	ServerSocket serverSocket;
	
	public ConnectionServer(int port, final PacketHandler packetHandler) throws IOException{
		connections = new CopyOnWriteArrayList<Connection>();
		this.packetHandler = packetHandler;
		serverSocket = new ServerSocket(port);
		thread = new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					Socket socket = null;
					try {
						socket = serverSocket.accept();
					} catch (IOException e) {
						e.printStackTrace();
					}
					int i = -1;
					try {
						i = NetworkAPI.getConnectionHandler().addConnection(socket, packetHandler);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(i != -1)
						connections.add(NetworkAPI.getConnectionHandler().getConnection(i));
				}
			}
		});
		thread.start();
	}
	
	public List<Connection> getConnections(){
		return connections;
	}
	
	public void broadcast(Packet packet){
		for(Connection connection : connections){
			connection.sendPacket(packet);
		}
	}
	
}