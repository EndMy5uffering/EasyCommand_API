package com.easycommands.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
	
	public ArrayList<String> getTabList(String[] parts){
		try {
			CMDStruct struct = search(parts).getFirst();
			if(struct != null) {
				ArrayList<String> output = new ArrayList<>();
				for(CMDStruct element : struct.next.values()) {
					output.add(element.part);
				}
				if(this.nextWildCard != null && this.nextWildCard.lookup != null) {
					for(String item : this.nextWildCard.lookup.get()) {
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
	
	public CMDStruct getPath(String[] parts) throws EasyCommandError{
		if(!parts[0].equalsIgnoreCase(this.part)) throw new EasyCommandError("Root was not the same: " + parts[0] + " != " + this.part);
		return getPath(parts, 1);
	}

	public CMDStruct getPath(String[] parts, int c) throws EasyCommandError{
		if(this.isVarArg && c <= parts.length -1){
			throw new EasyCommandError("Can not add function to var arg command!");
		}
		if(c > parts.length - 1) {
			return this;
		}

		boolean isNextWildCard = Pattern.matches(wildCardPattern, parts[c]);
		if(isNextWildCard && this.nextWildCard != null && !this.nextWildCard.part.equals(parts[c])){
			throw new EasyCommandError("Can not add more then one wildcard in one branch!");
		}else if(isNextWildCard && this.nextWildCard != null && this.nextWildCard.part.equals(parts[c])){
			return this.nextWildCard.getPath(parts, ++c);
		}else if(isNextWildCard && this.nextWildCard == null){
			this.nextWildCard = new CMDStruct(parts[c], null);
			return this.nextWildCard.getPath(parts, ++c);
		}
		if(!this.hasNext(parts[c])) {
			this.next.put(parts[c], new CMDStruct(parts[c], null));
		}
		return this.getNext(parts[c]).getPath(parts, ++c);
	}

	public void addCMD(String[] parts, CMDFunction func) throws EasyCommandError {
		getPath(parts).setFunc(func);
	}
	
	public void addCMDLookup(String[] parts, CMDTabLookup tablookup) throws EasyCommandError {
		getPath(parts).setLookup(tablookup);
	}
	
	public void addPermissionCheck(String[] parts, PermissionCheck permissionCheck, MissingPermissionHandle handle) throws EasyCommandError {
		getPath(parts).setPermissionCheck(permissionCheck);
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
