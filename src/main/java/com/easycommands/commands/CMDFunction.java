package com.easycommands.commands;

public interface CMDFunction {

	//CommandSender sender, Command cmd, String str, String[] args, Map<String, String> wildCards
	public boolean func(CMDArgs args);
    
}
