package com.easycommands.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CMDManager implements TabExecutor{
	
	private Map<String, CMDStruct> rootsToCMDS = new HashMap<>();
	private Map<String, String> aliasesToRoots = new HashMap<>();
	
	private String firstLabel = "[/]*[a-zA-Z0-9]*";
	private MissingPermissionHandle missingPermsHandle = (err) -> { 
		err.getPlayer().sendMessage(err.getMessage());
		return err;
	};
	
	public CMDManager() {
	}
	
	private String[] preParse(String cmd) {
		String[] parts = cmd.split(" ");
		parts[0] = preParseLabel(parts[0]);
		return parts;
	}
	
	private String preParseLabel(String label) {
		return Pattern.matches(firstLabel, label) ? label : "/" + label;
	}
	
	public boolean register(String cmd, CMDFunction func) {
		String[] parts = preParse(cmd);
		if(!rootsToCMDS.containsKey(parts[0])) {
			rootsToCMDS.put(parts[0], new CMDStruct(parts[0], null));
		}
		try {
			rootsToCMDS.get(parts[0]).addCMD(parts, func);
		} catch (EasyCommandError e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void registerAliase(String cmdRoot, String aliases){
		this.aliasesToRoots.put(preParseLabel(aliases), preParseLabel(cmdRoot));
	}
	
	public void addTabLookup(String cmd, CMDTabLookup lookup) {
		String[] parts = preParse(cmd);
		if(!rootsToCMDS.containsKey(parts[0])) {
			rootsToCMDS.put(parts[0], new CMDStruct(parts[0], null));
		}
		try {
			rootsToCMDS.get(parts[0]).addCMDLookup(parts, lookup);
		} catch (EasyCommandError e) {
			e.printStackTrace();
		}
	}
	
	public void addPermissionCheck(String cmd, PermissionCheck check) {
		addPermissionCheck(cmd, check, null);
	}
	
	public void addPermissionCheck(String cmd, PermissionCheck check, MissingPermissionHandle handle) {
		String[] parts = preParse(cmd);
		if(!rootsToCMDS.containsKey(parts[0])) {
			rootsToCMDS.put(parts[0], new CMDStruct(parts[0], null));
		}
		try {
			rootsToCMDS.get(parts[0]).addPermissionCheck(parts, check, handle);
		} catch (EasyCommandError e) {
			e.printStackTrace();
		}
	}

	public void setMissingPermissionHandle(MissingPermissionHandle mph){
		this.missingPermsHandle = mph;
	}
	
	public boolean call(CommandSender sender, Command cmd, String label, String[] args) throws MissingPermissions {
		label = preParseLabel(label);
		String[] tempArr = new String[args.length + 1];
	    tempArr[0] = label;
	    System.arraycopy(args, 0, tempArr, 1, args.length);
	    
	    if(rootsToCMDS.containsKey(label) || aliasesToRoots.containsKey(label)) {
	    	try {
				CMDStruct root = rootsToCMDS.get(label);
				if(root == null) root = rootsToCMDS.get(aliasesToRoots.get(label));
				if(root == null) return false;
				CMDPair<CMDStruct, Map<String, String>> pair = root.search(tempArr);

				CMDStruct struct = pair.getFirst();
				Map<String, String> wildCards = pair.getSecound();
				
				if(struct != null && struct.getFunc() != null) {
					if(sender instanceof Player){
						CMDStruct faildStruct = root.checkPermission(tempArr, (Player)sender);
						if(faildStruct != null)
							throw new MissingPermissions((Player)sender, faildStruct.getMissingPermissinHandle(), ChatColor.RED + "Missing Permissions", label, args);
					}
					return struct.getFunc().func(sender, cmd, label, args, wildCards);
				}
				return false;
			} catch (EasyCommandError e) {
				sender.sendMessage(ChatColor.RED + "An error occured please check the error message for details.");
				e.printStackTrace();
				return true;
			}
	    }
	    return false;
	}
	
	public ArrayList<String> tabComplete(CommandSender sender, Command cmd, String label, String[] args){
		label = preParseLabel(label);
		String[] tempArr = new String[args.length];
	    tempArr[0] = label;
	    System.arraycopy(args, 0, tempArr, 1, args.length-1);
		if(rootsToCMDS.containsKey(label)) {
			return rootsToCMDS.get(label).getTabList(tempArr);
		}else {
			return null;
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			return call(sender, cmd, label, args);
		} catch (MissingPermissions e) {
			MissingPermissions err = null;
			if(e.getHandle() != null) {
				err = e.getHandle().handleMissingPermission(e);
			}else if(this.missingPermsHandle != null) {
				err = this.missingPermsHandle.handleMissingPermission(e);
			}
			if(err != null) return err.returnStatus();
			return false;
		}
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return tabComplete(sender, cmd, label, args);
	}
	
}
