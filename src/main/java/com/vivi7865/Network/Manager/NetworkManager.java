package com.vivi7865.Network.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Logger;

import com.vivi7865.Network.Manager.command.Command;
import com.vivi7865.Network.Manager.log.ChatColor;

import jline.console.ConsoleReader;
import lombok.Getter;
import lombok.Setter;

public class NetworkManager extends Thread {

	@Getter
	@Setter
	private boolean debug;
		
	@Getter
	@Setter
	private ConsoleReader consoleReader;
	
	static @Getter
	@Setter
	private boolean isRunning = false;
	
	@Getter
	@Setter
	private Logger logger;
	
	@Getter
	@Setter
	private Console console;
	
	@Getter
	private final Map<String, Command> commandMap = new HashMap<>();
	
	public NetworkManager() {
		
		registerCommand(new Command("close"));
		registerCommand(new Command("create"));
		
	}
	
	public void run() {
		
		
		
	}
	
	public void registerCommand(Command command) {
        commandMap.put(command.getName().toLowerCase(), command);
        for (String alias : command.getAliases()) {
            commandMap.put(alias.toLowerCase(), command);
        }
    }
	
	public void onCommand(Command command, String[] args) {
		switch (command.getName()) {
			case "close":
				getLogger().info("Stopping...");
				getLogger().info("Goodbye!");
    			for ( Handler handler : getLogger().getHandlers() ) {handler.close();} //closing loggers handlers
    			System.exit(0);
			break;
			case "create":
				if (args.length < 4) {logger.info("Usage : create server [bukkit, bungeecord, spigot] {name} <version>");}
				if (args[1].equalsIgnoreCase("server")) {
					
				}
			break;
			default:
				getLogger().warning(ChatColor.RED + "Error! Command handler not found!!");
				getLogger().warning(ChatColor.RED + "This issue is non crucial but your should refer it to the author");
		}
	}
	
}
