package com.gmail.nossr50.vChat.channels;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class ChannelManager 
{
	public static HashMap<String, ChatChannel> chatChannels = new HashMap<String, ChatChannel>();
	
	public static void createDefaultChannels()
	{
		//Make global
		ChatChannel global = new ChatChannel(ChannelType.GLOBAL);
		chatChannels.put("Global", global);
	}
	public static void assignPlayersToChannels(String channelName, Player player)
	{
		if(chatChannels.containsKey(channelName))
			chatChannels.get(channelName).addPlayer(player);
	}
}