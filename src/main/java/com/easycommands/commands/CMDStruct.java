package com.easycommands.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDStruct {

	private final String wildCardPattern = "<([a-zA-Z0-9]+|\\.\\.\\.)>";
	private final String varargPattern = "<(\\.\\.\\.)>";
	
	private final String part;
	private final boolean isWildCard, isVarArg;
	private CMDFunction func;
	private Map<String, CMDStruct> next = new HashMap<>();
	private CMDTabLookup lookup;
	private PermissionCheck permissionCheck;
	private MissingPermissionHandle missingPermissinHandle = null;
	private CMDStruct nextWildCard = null;
		
	public CMDStruct(String part) {
		this(part, null);
	}
	
	public CMDStruct(String part, CMDFunction func) {
		this.isWildCard = Pattern.matches(wildCardPattern, part);
		this.isVarArg = Pattern.matches(varargPattern, part);
		this.part = part;
		this.func = func;
	}
	
	public CMDPair<CMDStruct, Map<String, String>> search(String[] cmd) throws EasyCommandError {
		return this.search(cmd, new HashMap<>(), 0);
	}
	
	private CMDPair<CMDStruct, Map<String, String>> search(String[] parts, Map<String,String> wildCards, int c) throws EasyCommandError {
		if(this.isVarArg){
			return new CMDPair<CMDStruct,Map<String,String>>(this, wildCards);
		}
		
		if(!this.isWildCard && !parts[c].equalsIgnoreCase(this.part)) 
			throw new EasyCommandError("Pattern mismatch!\nExpected: " + this.part + " got " + parts[c]);

		if(this.isWildCard)
			wildCards.put(this.part.replace("<", "").replace(">", ""), parts[c]);
		
		if(c < parts.length-1) {
			if(hasNext(parts[c+1])) {
				return getNext(parts[c+1]).search(parts, wildCards, ++c);
			}else if(!hasNext(parts[c+1]) && this.nextWildCard != null){
				return this.nextWildCard.search(parts, wildCards, ++c);
			}else{
				throw new EasyCommandError("Pattern mismatch! No sub function found for: "+ parts[c+1]);
			}
		}
		return new CMDPair<CMDStruct,Map<String,String>>(this, wildCards);
	}
	
	public ArrayList<String> getTabList(String[] parts, CommandSender sender, Command cmd, String str, String[] args){
		try {
			CMDPair<CMDStruct, Map<String, String>> res = search(parts);
			CMDStruct struct = res.getFirst();
			Map<String, String> wildCards = res.getSecound();
			if(struct != null) {
				ArrayList<String> output = new ArrayList<>();
				for(CMDStruct element : struct.next.values()) {
					output.add(element.part);
				}
				if(this.nextWildCard != null && this.nextWildCard.lookup != null) {
					for(String item : this.nextWildCard.lookup.get(sender, cmd, str, args, wildCards)) {
						output.add(item);
					}
				}
				return output;
			}
		} catch (EasyCommandError e) {
			return new ArrayList<>();
		}
		return new ArrayList<>();
	}
	
	public CMDStruct createPathSingle(String[] parts) throws EasyCommandError{
		if(!parts[0].equalsIgnoreCase(this.part)) throw new EasyCommandError("Root was not the same: " + parts[0] + " != " + this.part);
		return createPathSingle(parts, 1);
	}

	private CMDStruct createPathSingle(String[] parts, int c) throws EasyCommandError{
		if(this.isVarArg && c <= parts.length -1){
			throw new EasyCommandError("Can not get path after var arg argument!");
		}
		if(c > parts.length - 1) {
			return this;
		}
		boolean isNextWildCard = Pattern.matches(wildCardPattern, parts[c]);
		if(isNextWildCard && this.nextWildCard != null && !this.nextWildCard.part.equals(parts[c])){
			throw new EasyCommandError("Can not get path with more then one wildcard in a branch!");
		}else if(isNextWildCard && this.nextWildCard != null && this.nextWildCard.part.equals(parts[c])){
			return this.nextWildCard.createPathSingle(parts, ++c);
		}else if(isNextWildCard && this.nextWildCard == null){
			this.nextWildCard = new CMDStruct(parts[c], null);
			return this.nextWildCard.createPathSingle(parts, ++c);
		}
		if(!this.hasNext(parts[c])) {
			this.next.put(parts[c], new CMDStruct(parts[c], null));
		}
		return this.getNext(parts[c]).createPathSingle(parts, ++c);
	}

	public List<CMDStruct> createPath(String[] parts) throws EasyCommandError{
		if(!parts[0].equalsIgnoreCase(this.part)) throw new EasyCommandError("Root was not the same: " + parts[0] + " != " + this.part);
		return createPath(parts, 1, new ArrayList<CMDStruct>());
	}

	private List<CMDStruct> createPath(String[] parts, int c, List<CMDStruct> list) throws EasyCommandError{
		if(this.isVarArg && c <= parts.length -1){
			throw new EasyCommandError("Can not get path after var arg argument!");
		}
		list.add(this);
		if(c > parts.length - 1) {
			return list;
		}

		boolean isNextWildCard = Pattern.matches(wildCardPattern, parts[c]);
		if(isNextWildCard && this.nextWildCard != null && !this.nextWildCard.part.equals(parts[c])){
			throw new EasyCommandError("Can not get path with more then one wildcard in a branch!");
		}else if(isNextWildCard && this.nextWildCard != null && this.nextWildCard.part.equals(parts[c])){
			return this.nextWildCard.createPath(parts, ++c, list);
		}else if(isNextWildCard && this.nextWildCard == null){
			this.nextWildCard = new CMDStruct(parts[c], null);
			return this.nextWildCard.createPath(parts, ++c, list);
		}
		if(!this.hasNext(parts[c])) {
			this.next.put(parts[c], new CMDStruct(parts[c], null));
		}
		return this.getNext(parts[c]).createPath(parts, ++c, list);
	}

	public List<CMDStruct> getPath(String[] parts) throws EasyCommandError{
		if(!parts[0].equalsIgnoreCase(this.part)) throw new EasyCommandError("Root was not the same: " + parts[0] + " != " + this.part);
		return getPath(parts, 1, new ArrayList<CMDStruct>());
	}

	private List<CMDStruct> getPath(String[] parts, int c, List<CMDStruct> list) throws EasyCommandError{
		if(this.isVarArg && c <= parts.length -1){
			throw new EasyCommandError("Can not get path after var arg argument!");
		}
		list.add(this);
		if(c > parts.length - 1) {
			return list;
		}

		if(!this.hasNext(parts[c]) && this.nextWildCard == null)
			throw new EasyCommandError("Pattern mismatch! No sub function found for: "+ parts[c]);
		else if(!this.hasNext(parts[c]) && this.nextWildCard != null)
			return this.nextWildCard.getPath(parts, ++c, list);
		if(!this.hasNext(parts[c]))
			throw new EasyCommandError("Pattern mismatch! No sub function found for: "+ parts[c]);
		else
			return this.getNext(parts[c]).getPath(parts, ++c, list);
	}
 

	public void addCMD(String[] parts, CMDFunction func) throws EasyCommandError {
		createPathSingle(parts).setFunc(func);
	}

	public void addCMDLookup(String[] parts, CMDTabLookup tablookup) throws EasyCommandError {
		createPathSingle(parts).setLookup(tablookup);
	}
	
	public void addPermissionCheck(String[] parts, PermissionCheck permissionCheck, MissingPermissionHandle handle) throws EasyCommandError {
		CMDStruct cstruct = createPathSingle(parts);
		cstruct.setPermissionCheck(permissionCheck);
		cstruct.setMissingPermissinHandle(handle);
	}

	public CMDStruct checkPermission(String[] parts, Player p) throws EasyCommandError{
		if(!parts[0].equalsIgnoreCase(this.part)) throw new EasyCommandError("Root was not the same: " + parts[0] + " != " + this.part);
		List<CMDStruct> path = getPath(parts);
		boolean hasPermission = true;
		for(CMDStruct s : path){
			hasPermission &= s.checkPermission(p);
			if(!hasPermission) return s;
		}
		return null;
	}

	public boolean checkPermission(Player p){
		return hasPermissionCheck() ? this.permissionCheck.check(p) : true;
	}
	
	public String getPart() {
		return this.part;
	}
	
	public boolean hasFunc() {
		return func != null;
	}
	
	public boolean hasPermissionCheck() {
		return this.permissionCheck != null;
	}
	
	public boolean hasNext(String part) {
		return next.containsKey(part);
	}
	
	public CMDStruct getNext(String part) {
		return next.get(part);
	}
	
	public CMDFunction getFunc() {
		return this.func;
	}

	public PermissionCheck getPermissionCheck() {
		return permissionCheck;
	}

	public void setPermissionCheck(PermissionCheck permissionCheck) {
		this.permissionCheck = permissionCheck;
	}

	public MissingPermissionHandle getMissingPermissinHandle() {
		return missingPermissinHandle;
	}

	public void setMissingPermissinHandle(MissingPermissionHandle missingPermissinHandle) {
		this.missingPermissinHandle = missingPermissinHandle;
	}

	public CMDStruct getNextWildCard() {
		return nextWildCard;
	}

	public void setNextWildCard(CMDStruct nextWildCard) {
		this.nextWildCard = nextWildCard;
	}

	private void setFunc(CMDFunction func) {
		this.func = func;
	}

	private void setLookup(CMDTabLookup lookup) {
		this.lookup = lookup;
	}

}
