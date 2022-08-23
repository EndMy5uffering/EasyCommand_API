package com.easycommands.commands;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.easycommands.CMDListener;
import com.easycommands.commands.PermissionGroup.Type;

import net.md_5.bungee.api.ChatColor;

public class CMDManager implements TabExecutor{
	
	private Plugin plugin = null;
	private boolean suggestWildcardNames = false;

	private Map<String, CMDStruct> rootsToCMDS = new HashMap<>();
	private Map<String, String> aliasesToRoots = new HashMap<>();
	
	private String firstLabel = "[/]+[a-zA-Z0-9]*";
	private MissingPermissionHandle missingPermsHandle = (err) -> { 
		err.getPlayer().sendMessage(err.getMessage());
	};
	
	public CMDManager(Plugin p) {
		this.plugin = p;
	}

	public CMDManager(){}
	
	private String[] preParse(String cmd) {
		String[] parts = cmd.split(" ");
		parts[0] = preParseLabel(parts[0]);
		if(this.plugin != null && !this.rootsToCMDS.containsKey(parts[0])){
			PluginCommand command = ((JavaPlugin)plugin).getCommand(parts[0].substring(1, parts[0].length()));
			if(command != null)
				command.setExecutor(this);
			else
				plugin.getLogger().log(Level.WARNING, "Could not register executor for: " + cmd);
		}
		if(!rootsToCMDS.containsKey(parts[0])) {
			rootsToCMDS.put(parts[0], new CMDStruct(parts[0], null));
		}
		return parts;
	}
	
	private String preParseLabel(String label) {
		return Pattern.matches(firstLabel, label) ? label : "/" + label;
	}
	
	public boolean register(String cmd, CMDFunction func) {
		String[] parts = preParse(cmd);
		try {
			rootsToCMDS.get(parts[0]).addCMD(parts, new CMDMethodCollection(func));
		} catch (CMDCommandException e) {
			if(this.plugin != null) plugin.getLogger().log(java.util.logging.Level.WARNING, e.getMessage());
			return false;
		}
		return true;
	}

	public boolean register(CMDListener listener){

		Method[] methods = listener.getClass().getMethods();

		for(Method m : methods){
			if(m.isAnnotationPresent(CMDCommand.class)){
				CMDCommand commandAnnotation = m.getAnnotation(CMDCommand.class);

				Annotation[] annotations = m.getAnnotations();
				String command = commandAnnotation.cmd();
				String[] parts = preParse(command);

				try {
					rootsToCMDS.get(parts[0]).addCMD(parts, new CMDMethodCollection(listener, m));
				} catch (CMDCommandException e) {
					if(this.plugin != null) plugin.getLogger().log(java.util.logging.Level.WARNING, e.getMessage());
					return false;
				}

				if(m.isAnnotationPresent(Permission.class)){
					List<String[]> permsList = new ArrayList<>();
					for(Annotation a : annotations){
						if(a instanceof Permission perms){
							permsList.add(perms.Permissions());
						}
					}

					String[][] permArray = new String[permsList.size()][];

					for(int i = 0; i < permsList.size(); ++i){
						permArray[i] = permsList.get(i);
					}

					PermissionGroup PermGroup = new PermissionGroup(Type.CONJUNCTIVE, permArray);
					registerPermissionCheck(command, (player) -> {
						System.out.println("Checking perms: " + PermGroup.hasPermission(player));
						return PermGroup.hasPermission(player);
					});

				}



			}
		}
		return true;
	}

	public void registerAliase(String cmdRoot, String aliases){
		this.aliasesToRoots.put(preParseLabel(aliases), preParseLabel(cmdRoot));
	}
	
	public boolean registerTabLookup(String cmd, CMDTabLookup lookup) {
		String[] parts = preParse(cmd);
		try {
			rootsToCMDS.get(parts[0]).addCMDLookup(parts, lookup);
			return true;
		} catch (CMDCommandException e) {
			if(this.plugin != null) plugin.getLogger().log(Level.WARNING, e.getMessage());
		}
		return false;
	}
	
	public boolean registerPermissionCheck(String cmd, PermissionCheck check) {
		return registerPermissionCheck(cmd, check, null);
	}
	
	public boolean registerPermissionCheck(String cmd, PermissionCheck check, MissingPermissionHandle handle) {
		String[] parts = preParse(cmd);
		try {
			rootsToCMDS.get(parts[0]).addPermissionCheck(parts, check, handle);
			return true;
		} catch (CMDCommandException e) {
			if(this.plugin != null) plugin.getLogger().log(Level.WARNING, e.getMessage());
		}
		return false;
	}

	public void setMissingPermissionHandle(MissingPermissionHandle mph){
		this.missingPermsHandle = mph;
	}
	
	private boolean call(CommandSender sender, Command cmd, String label, String[] args) throws MissingPermissionsException, CMDCommandException {
		label = preParseLabel(label);
		String[] tempArr = new String[args.length + 1];
	    tempArr[0] = label;
	    System.arraycopy(args, 0, tempArr, 1, args.length);

		if(rootsToCMDS.containsKey(label) || aliasesToRoots.containsKey(label)) {
	    	try {
				CMDStruct root = rootsToCMDS.get(label);
				if(root == null) {
					root = rootsToCMDS.get(aliasesToRoots.get(label));
					tempArr[0] = aliasesToRoots.get(label);
				}
				if(root == null) return true;
				CMDPair<CMDStruct, Map<String, String>> pair = root.search(tempArr);

				CMDStruct struct = pair.getFirst();
				Map<String, String> wildCards = pair.getSecound();
				
				if(struct != null && struct.getFunc() != null) {
					if(sender instanceof Player){
						CMDStruct faildStruct = root.checkPermission(tempArr, (Player)sender);
						if(faildStruct != null)
							throw new MissingPermissionsException((Player)sender, faildStruct.getMissingPermissinHandle(), ChatColor.RED + "Missing Permissions", label, args);
					}
					return struct.getFunc().call(new CMDArgs(sender, cmd, label, args, wildCards));
				}else{
					sender.sendMessage(ChatColor.RED + "Command: " + ChatColor.AQUA + String.join(" ", tempArr) + ChatColor.RED + " not found!");
				}
			} catch (CMDCommandException e) {
				if(e.getErrorReason().equals(CMDCommandException.ErrorReason.COMMAND_NOT_FOUND)){
					sender.sendMessage(ChatColor.RED + "Command: " + ChatColor.AQUA + String.join(" ", tempArr) + ChatColor.RED + " not found!");
					return true;
				}
				throw e;
			}
	    }
	    return true;
	}
	
	private List<String> tabComplete(CommandSender sender, Command cmd, String label, String[] args){
		label = preParseLabel(label);
		String[] tempArr = new String[args.length];
	    tempArr[0] = label;
	    System.arraycopy(args, 0, tempArr, 1, args.length-1);
		if(rootsToCMDS.containsKey(label)) {
			return rootsToCMDS.get(label).getTabList(tempArr, args[args.length-1], sender, cmd, label, args, this.suggestWildcardNames);
		}else {
			return new ArrayList<>();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			return call(sender, cmd, label, args);
		} catch (MissingPermissionsException e) {
			if(e.getHandle() != null) {
				e.getHandle().handleMissingPermission(e);
			}else if(this.missingPermsHandle != null) {
				this.missingPermsHandle.handleMissingPermission(e);
			}
		} catch (CMDCommandException e) {
			sender.sendMessage(ChatColor.RED + "Exception: " + e.getMessage());
		} 
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return tabComplete(sender, cmd, label, args);
	}

	public void suggestWildcardNames(boolean suggest){
		this.suggestWildcardNames = suggest;
	}
	
}
