package com.gmail.nossr50.vChat;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.nossr50.vChat.datatypes.PlayerData;
import com.gmail.nossr50.vChat.spout.vSpout;
import com.gmail.nossr50.vChat.spout.runnables.UpdatePreviews;

public class vChat extends JavaPlugin
{
	final playerListener pl = new playerListener(this);
	public HashMap<Player, PlayerData> playerData = new HashMap<Player, PlayerData>();
	
	Boolean spoutEnabled = false;
	vSpout spout = null;
	
	@Override
	public void onDisable() {
		Bukkit.getServer().getScheduler().cancelTasks(this);
	}

	@Override
	public void onEnable() 
	{
		PluginManager PM = Bukkit.getServer().getPluginManager();
		
		PM.registerEvent(Event.Type.PLAYER_CHAT, pl, Priority.Lowest, this);
		PM.registerEvent(Type.PLAYER_QUIT, pl, Priority.Normal, this);
		PM.registerEvent(Type.PLAYER_JOIN, pl, Priority.Normal, this);
		
		if(PM.getPlugin("Spout") != null)
		{
			spoutEnabled = true;
		}
		if(spoutEnabled)
		{
			spout = new vSpout(this);
			spout.initialize();
		}
		
		Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new UpdatePreviews(this), 0, 2);
	}
}