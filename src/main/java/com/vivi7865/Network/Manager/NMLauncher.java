package com.vivi7865.Network.Manager;

public class NMLauncher {	
	
	public static void main(String[] args) throws Exception {
		NetworkManager manager = new NetworkManager();
		
		manager.setConsole(new Console(manager));
		manager.getConsole().start();
		
		manager.start();
	}
}
