package com.gmail.nossr50.vChat.spout;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.gmail.nossr50.vChat.vChat;
import com.gmail.nossr50.vChat.datatypes.SpoutPlayerData;
import com.gmail.nossr50.vChat.spout.listeners.inputListener;
import com.gmail.nossr50.vChat.spout.listeners.screenListener;
import com.gmail.nossr50.vChat.spout.screens.CustomizationScreen;

public class vSpout 
{
	vChat plugin = null;
	inputListener il = null;
	screenListener sl = null;
	public static HashMap<SpoutPlayer, CustomizationScreen> playerScreens = new HashMap<SpoutPlayer, CustomizationScreen>();
	public static HashMap<SpoutPlayer, SpoutPlayerData> spoutPlayerData = new HashMap<SpoutPlayer, SpoutPlayerData>();
	
	public vSpout(vChat pluginx)
	{
		plugin = pluginx;
	}
	
	public void initialize()
	{
		PluginManager PM = Bukkit.getServer().getPluginManager();
		il = new inputListener(plugin);
		sl = new screenListener(plugin);
		
		PM.registerEvent(Type.CUSTOM_EVENT, il, Priority.Normal, plugin);
		PM.registerEvent(Type.CUSTOM_EVENT, sl, Priority.Normal, plugin);
	}
	
	public static void createSpoutPlayerData(Player player)
	{
		SpoutPlayer sPlayer = SpoutManager.getPlayer(player);
		spoutPlayerData.put(sPlayer, new SpoutPlayerData(sPlayer));
	}
}
