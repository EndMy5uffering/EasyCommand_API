package com.easycommands.commands;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CMDEventText {
    public TextComponent getInteractCommand(Player p, String displayText, String value, ClickEvent.Action type, ChatColor c, boolean bold, String font) {
		TextComponent message = new TextComponent(displayText);
		message.setClickEvent(new ClickEvent(type, value));
		message.setColor(c);
		message.setBold(bold);
		if(font != null) message.setFont(font);
		//p.spigot().sendMessage(message);
		return message;
	}

	public TextComponent getInteractCommand(Player p, String displayText, String value, ClickEvent.Action type, ChatColor c) {
		return getInteractCommand(p, displayText, value, type, c, false, null);
	}

	public TextComponent getInteractCommand(Player p, String displayText, String value, ClickEvent.Action type) {
		return getInteractCommand(p, displayText, value,type, ChatColor.WHITE, false, null);
	}
}
