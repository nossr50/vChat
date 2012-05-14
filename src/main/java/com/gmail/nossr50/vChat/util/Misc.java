package com.gmail.nossr50.vChat.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Misc {

	public static void broadcast(String str, Player player)
	{
		for(Player x : Bukkit.getServer().getOnlinePlayers())
		{
			if(x.getName().equals(player.getName()))
				continue;
			x.sendMessage(str);
		}
	}
}
