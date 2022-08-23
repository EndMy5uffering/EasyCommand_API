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

    public boolean hasWildCard(String name){
        return this.wildCards.get(name) != null && this.wildCards.get(name) != "";
    }

    public int getWildCardInt(String name){
        return Integer.parseInt(this.wildCards.get(name));
    }

    public double getWildCardDouble(String name){
        return Double.parseDouble(this.wildCards.get(name));
    }

    public boolean getWildCardBoolean(String name){
        return Boolean.parseBoolean(name);
    }

    public boolean isWildCardNumber(String name){
        try {
            Double.parseDouble(this.wildCards.get(name));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isWildCardBoolean(String name){
        try {
            Boolean.parseBoolean(this.wildCards.get(name));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
