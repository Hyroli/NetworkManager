package com.vivi7865.Network.Manager.config;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.vivi7865.Network.Manager.NetworkManager;

public class MongoConfiguration {

	private NetworkManager manager;
	
	private ConfigYAML conf;
	
	//private Map<String, Object> softwares;
	
	private DBCursor softwares;
	
	private String host;
	
	private int port;
	
	private String user;
	
	private String pass;
	
	public MongoConfiguration (NetworkManager manager, String host, int port, String user, String pass) {
		this.manager = manager;
		this.conf = manager.getConf();
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}
	
	public boolean refresh() {
		try {			
			MongoClient m = new MongoClient(host, port);
			DB db = m.getDB("NetManager");
						
			softwares = db.getCollection("softwares").find();
			
			manager.getLogger().info("Connected to mongoDB");
		} catch (Exception e) {
			manager.getLogger().severe("Could not connect to mongoDB! Mongo error: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean load() {
		return refresh();
	}
	
	public DBCursor test() {
		
		return softwares;
		
	}
	
	/*
	 * Dev
	 */
	public <T> T get(T defaultValue) {
		
		return defaultValue;
		
	}
	
}
