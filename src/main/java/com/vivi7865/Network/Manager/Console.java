package com.vivi7865.Network.Manager;

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.fusesource.jansi.AnsiConsole;

import com.vivi7865.Network.Manager.log.ChatColor;
import com.vivi7865.Network.Manager.log.LoggingOutputStream;
import com.vivi7865.Network.Manager.log.NMLogger;

import jline.TerminalFactory;
import jline.console.ConsoleReader;

public class Console extends Thread {
	
	NetworkManager manager;

	public Console(NetworkManager manager) {
		this.manager = manager;
	}
	
	public boolean defineLogger() {
		try {
			System.setProperty("library.jansi.version", "NetManager");
			manager.setConsoleReader(new ConsoleReader());
			AnsiConsole.systemInstall();
			manager.getConsoleReader().setExpandEvents( false );
        
			manager.setLogger(new NMLogger(manager));
			System.setErr(new PrintStream(new LoggingOutputStream(manager.getLogger(), Level.SEVERE), true ));
			System.setOut(new PrintStream(new LoggingOutputStream(manager.getLogger(), Level.INFO), true ));
        
			manager.getConsoleReader().setPrompt(">");
			return true;
		} catch (IOException e) {
			System.err.println("Could not register logger: " + e.getMessage());
			return false;
		}
	}
	
	public void run() {
		try {
            
            String line = null;
            Logger log = manager.getLogger();
            while (manager.isRunning() && (line = manager.getConsoleReader().readLine()) != null) {
            	String[] args = line.split(" ");
            	if (!manager.dispatchCommand(args[0].toLowerCase(), args)) { //TODO temporary code! create a real command handler
            		log.info(ChatColor.RED + "Unknown Command");
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
