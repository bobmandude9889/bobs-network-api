package net.bobmandude9889.api;

public class NetworkAPI {

	private static ConnectionHandler connectionHandler;
	
	public static ConnectionHandler getConnectionHandler(){
		if(connectionHandler == null)
			connectionHandler = new ConnectionHandler();
		return connectionHandler;
	}
	
}
