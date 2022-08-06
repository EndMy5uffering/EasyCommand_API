package com.easycommands.commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CMDArgs {
    
    private CommandSender sender;
    private Command cmd;
    private String str;
    private String[] args;
    private Map<String, String> wildCards;


    public CMDArgs(CommandSender sender, Command cmd, String str, String[] args, Map<String,String> wildCards) {
        this.sender = sender;
        this.cmd = cmd;
        this.str = str;
        this.args = args;
        this.wildCards = wildCards;
    }

    public CommandSender getSender() {
        return this.sender;
    }

    public Command getCmd() {
        return this.cmd;
    }

    public String getStr() {
        return this.str;
    }

    public String[] getArgs() {
        return this.args;
    }

    public Map<String,String> getWildCards() {
        return this.wildCards;
    }

}
