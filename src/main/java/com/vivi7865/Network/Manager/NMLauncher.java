package com.vivi7865.Network.Manager;

import java.util.Arrays;
import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class NMLauncher {	
	
	public static void main(String[] args) throws Exception {
		
		OptionParser parser = new OptionParser();
        parser.allowsUnrecognizedOptions();
        parser.acceptsAll(list("v", "version"));
        parser.acceptsAll(list("c", "command"));
        
        OptionSet options = parser.parse( args );
        
        if (options.has( "version" ))
        {
            System.out.println(NetworkManager.class.getPackage().getImplementationVersion());
            return;
        }
        
		NetworkManager manager = new NetworkManager((NetworkManager.class.getPackage().getImplementationVersion() == null) ? "unknown" : NetworkManager.class.getPackage().getImplementationVersion());
		
		manager.setConsole(new Console(manager));
		
		if (manager.getConsole().defineLogger()) {
			manager.getLogger().info("Logger defined");
		} else {
			System.exit(87);
		}
        
		NetworkManager.setManager(manager);
		
        manager.starting();
		
        manager.setRunning(true);
        
        manager.start();
        manager.getConsole().start();
                
        if (options.has("command") && options.valueOf("command") != null) {
        	
        	manager.dispatchCommand( (String) options.valueOf("command"));
        	manager.dispatchCommand("close");
        	
        }
        
	}
	
	private static List<String> list(String... params) {
        return Arrays.asList( params );
    }
}
