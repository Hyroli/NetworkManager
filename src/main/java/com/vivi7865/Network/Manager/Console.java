package com.vivi7865.Network.Manager;

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import jline.TerminalFactory;
import jline.console.ConsoleReader;

import org.fusesource.jansi.AnsiConsole;

import com.vivi7865.Network.Manager.log.LoggingOutputStream;
import com.vivi7865.Network.Manager.log.NMLogger;

public class Console extends Thread {
	
	NetworkManager manager;

	public Console(NetworkManager manager) {
		this.manager = manager;
	}
	
	public void run() {
		try {
			System.setProperty( "library.jansi.version", "NetManager" );
			manager.setConsoleReader(new ConsoleReader());
			AnsiConsole.systemInstall();
            manager.getConsoleReader().setExpandEvents( false );
            
            manager.setLogger(new NMLogger(manager));
            System.setErr(new PrintStream(new LoggingOutputStream(manager.getLogger(), Level.SEVERE), false ));
            System.setOut(new PrintStream(new LoggingOutputStream(manager.getLogger(), Level.INFO), false ));
            
            manager.getConsoleReader().setPrompt(">");
            
            String line = null;
            Logger log = manager.getLogger();
            while ((line = manager.getConsoleReader().readLine()) != null) {
            	String[] args = line.split(" ");
            	if (manager.getCommandMap().containsKey(args[0].toLowerCase())) { //TODO temporary code! create a real command handler
            		manager.onCommand(manager.getCommandMap().get(args[0].toLowerCase()), args);
            		
            		/*log.info("Stopping...");
            		log.info("Goodbye!");
            		for ( Handler handler : manager.getLogger().getHandlers() ) {handler.close();} //closing loggers handlers
            		System.exit(0);*/
            	} else {
            		log.info("Unknown Command: " + line);
            	}
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
           try {
                TerminalFactory.get().restore();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
	}
	
}
