package com.easycommands.commands;

public class CMDCommandException extends Exception{

	public enum ErrorReason{
		COMMAND_NOT_FOUND {
			@Override
			public String toString() {
				return "Command not found";
			}
		},
		ROOT_MISSMATCH {
			@Override
			public String toString() {
				return "Command root missmatch";
			}
		},
		CMD_MISSMATCH {
			@Override
			public String toString() {
				return "Command missmatch";
			}
		},
		OTHER {
			@Override
			public String toString() {
				return "Other";
			}
		},
		FUNCTION_WAS_NULL {
			@Override
			public String toString() {
				return "Command function was null";
			}
		},
		COMMAND_FUNCTION_INVOCATION_EXCEPTION {
			@Override
			public String toString() {
				return "Command function could not be invoked";
			}
		};

	}

	private final ErrorReason reason;
	
	public CMDCommandException(ErrorReason reason) {
		this("[" + reason.toString() + "] No details given.", reason);
	}
	
	public CMDCommandException(String message, ErrorReason reason) {
		super(message);
		this.reason = reason;
	}

	public CMDCommandException(String message) {
		super(message);
		this.reason = ErrorReason.OTHER;
	}

	public ErrorReason getErrorReason(){
		return this.reason;
	}
	
}
