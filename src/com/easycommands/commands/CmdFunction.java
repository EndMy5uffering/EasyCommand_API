package com.easycommands.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CmdFunction {

	public boolean execute(CommandSender sender, Command cmd, String arg, String[] args);
	
}
