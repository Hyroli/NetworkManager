package com.vivi7865.Network.Manager;

import lombok.Getter;
import lombok.Setter;

public class Server {

	@Getter
	private int id;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	private String directory;
	
	
	
	public Server(int id, String name) {
		
		this.id = id;
		this.name = name;
		
	}
	
}
