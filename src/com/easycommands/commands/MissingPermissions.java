package com.easycommands.commands;

public class MissingPermissions extends Exception{

	private final String label;
	private final String[] args;
	
	public MissingPermissions(String message, String label, String[] args) {
		super(message);
		this.label = label;
		this.args = args;
	}

	public String getLabel() {
		return label;
	}

	public String[] getArgs() {
		return args;
	}
	
}
