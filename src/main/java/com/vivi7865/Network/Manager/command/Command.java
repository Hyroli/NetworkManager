package com.vivi7865.Network.Manager.command;

import com.google.common.base.Preconditions;

import lombok.Data;

@Data
public class Command {

	private String name;
    private String permission;
    private String[] aliases;
    
    public Command(String name) {
        this(name.toLowerCase(), null);
    }
    
    public Command(String name, String permission, String... aliases) {
    	Preconditions.checkNotNull(name, "Name cannot be null");
        this.name = name.toLowerCase();
        this.permission = permission;
        this.aliases = aliases;
    }	
}
