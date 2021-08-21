package com.easycommands.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class CommandManager implements TabExecutor {

	private SearchTree<BaseCmd> tree = new SearchTree<>();
	
	public CommandManager(){
		
	}
	
	public void registerCommand(String path, CmdFunction f) {
		tree.addElement(path, new BaseCmd(f));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		BaseCmd c = tree.getElement(arg, args);
		
		if(c != null) return c.execute(sender, cmd, arg, args);

		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String arg, String[] args) {
		

		return null;
	}

}
