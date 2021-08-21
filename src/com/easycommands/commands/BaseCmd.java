package com.easycommands.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaseCmd {

	private String description;
	private final CmdFunction cmdFunction;
	private String[][] permissions;
	private PermissionCheck check;
	
	public BaseCmd(CmdFunction cmdFunction) {
		this.cmdFunction = cmdFunction;
	}
	
	public boolean execute(CommandSender sender, Command cmd, String arg, String[] args) {
		//permissions in KNF
		//{{a | b | c} & {a | b | c} & {a | b | c} & ...}
		if(sender instanceof Player) {
			boolean b = true;
			for(int i = 0; i < permissions.length; i++) {
				boolean a = false;
				for(int j = 0; j < permissions[i].length; j++) {
					a |= ((Player)sender).hasPermission(this.permissions[i][j]);
				}
				b &= a;
			}
			if(b && check != null && check.check((Player)sender)) {
				return cmdFunction.execute(sender, cmd, arg, args);
			}
		}
		return cmdFunction.execute(sender, cmd, arg, args);
	}

	public String[][] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[][] permissions) {
		this.permissions = permissions;
	}

	public PermissionCheck getCheck() {
		return check;
	}

	public void setCheck(PermissionCheck check) {
		this.check = check;
	}

	public CmdFunction getCmdFunction() {
		return cmdFunction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
