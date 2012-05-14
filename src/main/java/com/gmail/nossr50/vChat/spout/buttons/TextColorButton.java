package com.gmail.nossr50.vChat.spout.buttons;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.GenericButton;

import com.gmail.nossr50.vChat.datatypes.PlayerData;

public class TextColorButton extends GenericButton
{
	ChatColor selectedColor = ChatColor.WHITE;
	public TextColorButton(PlayerData PD)
	{
		selectedColor = PD.getDefaultColor();
		this.setWidth(120);
		this.setHeight(20);
		this.setText(selectedColor+"CHAT MESSAGE COLOR");
	}
	public ChatColor getSelectedColor()
	{
		return selectedColor;
	}
	public void SetSelectedColor(ChatColor type)
	{
		selectedColor = type;
		this.setText(type+this.getText().substring(2)).setDirty(true);
	}
}
