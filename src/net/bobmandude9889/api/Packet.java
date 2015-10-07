package net.bobmandude9889.api;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Packet {

	private String name;
	private HashMap<String,Object> values;

	public Packet(String name) {
		this.name = name;
		this.values = new HashMap<String,Object>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue(String key) {
		return values.get(key);
	}

	public Packet put(String key, Object value) {
		values.put(key, value);
		return this;
	}

	public HashMap<String,Object> getValues(){
		return values;
	}

	@SuppressWarnings("unchecked")
	public JSONObject serialize() {
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		for(String key : values.keySet()){
			obj.put(key, values.get(key));
		}
		return obj;
	}

	public static Packet buildPacket(String rawPacket) throws PacketException {
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(rawPacket);
			String name = (String) obj.get("name");
			Packet packet = new Packet(name);
			for(Object key : obj.keySet()){
				packet.put((String) key, obj.get(key));
			}
			return packet;
		} catch (ParseException e) {
			throw new PacketException(rawPacket);
		}
	}

}
