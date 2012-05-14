package com.gmail.nossr50.vChat.spout;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.gmail.nossr50.vChat.datatypes.SpoutPlayerData;
import com.gmail.nossr50.vChat.spout.screens.CustomizationScreen;

public class vSpout 
{
	public static HashMap<SpoutPlayer, CustomizationScreen> playerScreens = new HashMap<SpoutPlayer, CustomizationScreen>();
	public static HashMap<SpoutPlayer, SpoutPlayerData> spoutPlayerData = new HashMap<SpoutPlayer, SpoutPlayerData>();
	
	public static void createSpoutPlayerData(Player player)
	{
		SpoutPlayer sPlayer = SpoutManager.getPlayer(player);
		spoutPlayerData.put(sPlayer, new SpoutPlayerData(sPlayer));
	}
}
