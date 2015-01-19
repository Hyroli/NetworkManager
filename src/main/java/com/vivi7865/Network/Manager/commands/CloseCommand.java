package com.vivi7865.Network.Manager.commands;

import com.vivi7865.Network.Manager.NetworkManager;

public class CloseCommand extends Command {
		
	public CloseCommand() {
		super("close");
	}

	@Override
	public void exec(String[] args) {
		NetworkManager.getManager().shutdown();
	}

}
