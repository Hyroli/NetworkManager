package com.vivi7865.Network.Manager.commands;

public class CreateCommand extends Command {

	public CreateCommand() {
		super("create");
	}

	@Override
	public void exec(String[] args) {
		
		if (args[1] == "server") {
			
			System.out.println("test");
			
		}
		
	}

	
}
