package com.gmail.nossr50.vChat.spout.buttons;


import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.GenericButton;

public class ColorButton extends GenericButton
{
	ChatColor selectedColor = ChatColor.WHITE;
	
	public ColorButton(ChatColor x)
	{
		selectedColor = x;
		this.setHeight(20).setWidth(75);
		this.setText(selectedColor+selectedColor.name());
		this.setDirty(true);
	}
	
	public ChatColor getChatColor()
	{
		return selectedColor;
	}
	
	public void reset()
	{
		this.setText(selectedColor+selectedColor.name());
		this.setDirty(true);
	}
}