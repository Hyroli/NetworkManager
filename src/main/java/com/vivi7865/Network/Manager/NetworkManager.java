package com.vivi7865.Network.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;
import com.mongodb.MongoClient;
import com.vivi7865.Network.Manager.commands.CloseCommand;
import com.vivi7865.Network.Manager.commands.Command;
import com.vivi7865.Network.Manager.commands.CreateCommand;
import com.vivi7865.Network.Manager.commands.TestCommand;
import com.vivi7865.Network.Manager.config.ConfigYAML;
import com.vivi7865.Network.Manager.config.MongoConfiguration;

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

	@Getter
	@Setter
	private boolean isRunning = false;

	@Getter
	@Setter
	private Logger logger;
	
	@Getter
	private static NetworkManager manager;

	@Getter
	@Setter
	private Console console;

	@Getter
	private final Map<String, Command> commandMap = new HashMap<>();

	@Getter
	@Setter
	private String VERSION = "0.1";

	@Getter
	private ConfigYAML conf;

	@Getter
	@Setter
	private MongoClient mongo;

	@Getter
	private MongoConfiguration mongoConfig;

	public NetworkManager(String version) {
		this.VERSION = version;
	}

	public void starting() {
		
		logger.info("Starting NetworkManager v" + VERSION + "...");
		
		registerCommand(new CloseCommand());
		registerCommand(new CreateCommand());
		
		registerCommand(new TestCommand("test"));//temporary
		registerCommand(new TestCommand("test2"));//temporary
		
	}
	
	public void run() {
		
		conf = new ConfigYAML(this);
		conf.load();
		
		mongoConfig = new MongoConfiguration(this, conf.getString("mongodb.host", "localhost"), conf.getInt("mongodb.port", 27017), conf.getString("mongodb.user", "mongo"), conf.getString("mongodb.pass", "pass"));
		mongoConfig.load();

		logger.info("NetworkManager started");
		
	}
	
	public void registerCommand(Command command) {
        commandMap.put(command.getName().toLowerCase(), command);
        for (String alias : command.getAliases()) {
            commandMap.put(alias.toLowerCase(), command);
        }
    }
	
	public boolean dispatchCommand(String command, String[] args) {
		if (commandMap.containsKey(command)) {
			try {
				commandMap.get(command).exec(args);
			} catch (Exception e) {
				getLogger().severe("Could not execute command: " + command);
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	public boolean dispatchCommand(String command) {
		String[] args = {command};
		return dispatchCommand(command, args);
	}
	
	public static void setManager(NetworkManager manager) {
        Preconditions.checkNotNull(manager, "Manager could not be null");
        NetworkManager.manager = manager;
    }
	
	public void shutdown() {
		setRunning(false);
		getLogger().info("Stopping...");
		getLogger().info("Goodbye!");
		for ( Handler handler : getLogger().getHandlers() ) handler.close(); //closing logger handlers
		System.exit(0);
	}
	
	/*public void onCommand(Command command, String[] args) {
		switch (command.getName()) {
			case "close":
				
			break;
			case "create":
				if (args.length < 4) {
					logger.info("Usage : create server [bukkit, bungeecord, spigot] {name} <version>");
					return;
				}
				
				if (args[1].equalsIgnoreCase("server")) {
					if (args[2]) {
						
						
						
					}
				}
			break;
			case "test":
				//logger.info("" + mongoConfig.test().one());
			break;
			case "test2":
			break;
			default:
				getLogger().warning(ChatColor.RED + "Error! Command handler not found!!");
				getLogger().warning(ChatColor.RED + "This issue is non crucial but your should refer it to the author");
		}
	}*/
	
}
