package net.bobmandude9889.api;

public interface PacketListener {

	public void packetReceived(Packet packet, Connection from);
	
}
