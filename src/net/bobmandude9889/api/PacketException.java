package net.bobmandude9889.api;

public class PacketException extends Exception{

	private String rawPacket;
	
	public PacketException(String rawPacket){
		this.rawPacket = rawPacket;
	}
	
	public String getRawPacket(){
		return rawPacket;
	}
	
}
