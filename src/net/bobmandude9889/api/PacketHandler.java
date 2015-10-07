package net.bobmandude9889.api;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PacketHandler implements Runnable {

	private List<Connection> connections;
	private HashMap<String, PacketListener> listeners;
	private Thread listenerThread;

	public PacketHandler() {
		listeners = new HashMap<String, PacketListener>();
		connections = new CopyOnWriteArrayList<Connection>();
		listenerThread = new Thread(this);
		listenerThread.start();
	}

	public int addListener(String name, PacketListener listener) {
		listeners.put(name, listener);
		return listeners.size() - 1;
	}

	protected int addConnection(Connection connection) {
		connections.add(connection);
		return connections.size() - 1;
	}

	@Override
	public void run() {
		while (true) {
			for (Connection connection : connections) {
				if (connection.hasWaitingPacket()) {
					try {
						Packet packet = connection.nextPacket();
						String name = packet.getName();
						PacketListener listener;
						if (listeners.containsKey(name)) {
							listener = listeners.get(name);
							listener.packetReceived(packet, connection);
						} else
							System.out.println("Received unhandled packet: " + packet.serialize());

					} catch (PacketException e) {
						System.out.println("There was an error parsing the packet: " + e.getRawPacket());
					}
				}
			}
		}
	}

}
