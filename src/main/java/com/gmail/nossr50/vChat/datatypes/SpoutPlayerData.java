package com.gmail.nossr50.vChat.datatypes;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SpoutPlayerData {
	GenericLabel activeChannel = new GenericLabel(), channels = new GenericLabel();
	public SpoutPlayerData(SpoutPlayer sPlayer)
	{
		//TODO: make this functional, tis a mere placeholder atm
		activeChannel.setText(ChatColor.DARK_AQUA+"Active Channel:"+ChatColor.GOLD+" Global");
		activeChannel.setX(2).setY(208).setDirty(true);
		channels.setText(ChatColor.DARK_AQUA+"Member Of:"+ChatColor.GOLD+" Global");
		channels.setX(2).setY(216).setDirty(true);
	}
	public GenericLabel getActiveChannelLabel()
	{
		return activeChannel;
	}
	public GenericLabel getChannelsLabel()
	{
		return channels;
	}

}