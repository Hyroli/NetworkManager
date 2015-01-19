package com.vivi7865.Network.Manager.commands;

import com.vivi7865.Network.Manager.NetworkManager;

public class TestCommand extends Command {
	
	public TestCommand(String com) {
		super(com);
	}

	@Override
	public void exec(String[] args) {
		NetworkManager manager = NetworkManager.getManager();
		switch (args[0]) {
			case "test":
				manager.getLogger().info("" + manager.getMongoConfig().test().one());
			break;
			case "test2":
			break;
		}
	}

}
