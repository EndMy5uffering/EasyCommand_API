package com.easycommands.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CMDManager {
	
	private Map<String, CMDStruct> rootsToCMDS = new HashMap<>();
	
	public void register(String cmd, CMDFunction func) {
		String[] parts = cmd.split(" ");
		if(!rootsToCMDS.containsKey(parts[0])) {
			rootsToCMDS.put(parts[0], new CMDStruct(parts[0], null));
		}
		try {
			rootsToCMDS.get(parts[0]).addCMD(parts, func);
		} catch (EasyCommandError e) {
			e.printStackTrace();
		}
	}
	
	public void addLookup(String cmd, CMDTabLookup lookup) {
		String[] parts = cmd.split(" ");
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
		String[] parts = cmd.split(" ");
		if(!rootsToCMDS.containsKey(parts[0])) {
			rootsToCMDS.put(parts[0], new CMDStruct(parts[0], null));
		}
		try {
			rootsToCMDS.get(parts[0]).addPermissionCheck(parts, check);
		} catch (EasyCommandError e) {
			e.printStackTrace();
		}
	}
	
	public boolean call(CommandSender sender, Command cmd, String label, String[] args) throws MissingPermissions {
		String[] tempArr = new String[args.length + 1];
	    tempArr[0] = label;
	    System.arraycopy(args, 0, tempArr, 1, args.length);
	    
	    if(rootsToCMDS.containsKey(label)) {
	    	try {
				CMDStruct struct = rootsToCMDS.get(label).search(tempArr);
				if(struct.hasPermissionCheck() && !struct.getPermissionCheck().check(sender)) {
					throw new MissingPermissions("Missing Permissions", label, args);
				}
				if(struct != null && struct.getFunc() != null) {
					return struct.getFunc().func(sender, cmd, label, args);
				}
				return false;
			} catch (EasyCommandError e) {
				e.printStackTrace();
				return false;
			}
	    }
	    return false;
	}
	
	public ArrayList<String> tabComplete(CommandSender sender, Command cmd, String str, String[] args){
		String[] tempArr = new String[args.length + 1];
	    tempArr[0] = str;
	    System.arraycopy(args, 0, tempArr, 1, args.length);
		if(rootsToCMDS.containsKey(str)) {
			return rootsToCMDS.get(str).getTabList(tempArr);
		}else {
			return null;
		}
	}
	
}
