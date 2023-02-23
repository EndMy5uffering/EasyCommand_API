package com.endmysuffering.easycommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CMDArgs {
    
    private CommandSender sender;
    private Command cmd;
    private String str;
    private String[] args;
    private Map<String, String> wildCards;
    private interface AlertFunc{ public void call(String wildCard); }

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

    public String getWildCard(String name) {
        return this.wildCards.get(name);
    }

    public boolean hasWildCard(String name){
        return this.wildCards.get(name) != null && this.wildCards.get(name) != "";
    }

    /**
     * Function to check for multiple wildcards at once.
     * @param names names of wildcards
     * @return true if all wildcards in names are present false if one or more are missing
     */
    public boolean hasWildCards(String... names){
        if(names.length == 0) return false;
        boolean exists = true;
        for(String name : names){
            exists &= hasWildCard(name);
        }
        return exists;
    }

    /**
     * Checks if all wildcards are present and if one or more are missing calls the alert function.
     * @param func Alert function that is called for each missing wildcard.
     * @param names names of wildcards.
     * @return true if all wildcards in names are present false if one or more are missing
    */
    public boolean checkWildCardsAndAlert(AlertFunc func, String... names){
        if(names.length == 0) return false;
        boolean exists = true;
        for(String name : names){
            boolean res = hasWildCard(name);
            if(!res) func.call(name);
            exists &= res;
        }
        return exists;
    }

    /**
     * Checks if all wildcards are present and if one or more are missing calls the alert function.
     * @param names names of wildcards.
     * @return true if all wildcards in names are present false if one or more are missing
    */
    public String[] checkWildCardsAndCollect(String... names){
        List<String> res = new ArrayList<String>();
        for(String name : names){
            if(!hasWildCard(name)) res.add(name);
        }
        return res.toArray(new String[0]);
    }

    public int getWildCardInt(String name){
        return Integer.parseInt(this.wildCards.get(name));
    }

    public double getWildCardDouble(String name){
        return Double.parseDouble(this.wildCards.get(name));
    }

    public boolean getWildCardBoolean(String name){
        return Boolean.parseBoolean(this.getWildCard(name));
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
        String value = this.wildCards.get(name).toLowerCase();
        return value.equals("true") || value.equals("false");
    }

}
