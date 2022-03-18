package com.easycommands.commands;

public class EasyCommandError extends Exception{

	public EasyCommandError(String message) {
		super(message);
	}

	public EasyCommandError() {
		super("No details given.");
	}
	
}
