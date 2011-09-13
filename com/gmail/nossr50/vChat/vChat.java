package com.gmail.nossr50.vChat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.blockface.bukkitstats.CallHome;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.nossr50.vChat.channels.ChannelManager;
import com.gmail.nossr50.vChat.datatypes.PlayerData;
import com.gmail.nossr50.vChat.listeners.playerListener;
import com.gmail.nossr50.vChat.spout.vSpout;
import com.gmail.nossr50.vChat.spout.runnables.UpdatePreviews;

public class vChat extends JavaPlugin
{
	final playerListener pl = new playerListener(this);
	public HashMap<Player, PlayerData> playerData = new HashMap<Player, PlayerData>();
	
	public static Boolean spoutEnabled = false;
	vSpout spout = null;
	String vChatDir = "plugins" + File.separator + "vChat";
	
	@Override
	public void onDisable() {
		Bukkit.getServer().getScheduler().cancelTasks(this);
	}

	@Override
	public void onEnable() 
	{
		PluginManager PM = Bukkit.getServer().getPluginManager();
		
		PM.registerEvent(Event.Type.PLAYER_CHAT, pl, Priority.Monitor, this);
		PM.registerEvent(Type.PLAYER_QUIT, pl, Priority.Normal, this);
		PM.registerEvent(Type.PLAYER_JOIN, pl, Priority.Normal, this);
		
		//Create default channels
		ChannelManager.createDefaultChannels();
		
		if(PM.getPlugin("Spout") != null)
		{
			spoutEnabled = true;
		}
		
		//For Spout
		if(spoutEnabled)
		{
			spout = new vSpout(this);
			spout.initialize();
		}
		
		//For reloading
		for(Player x : Bukkit.getServer().getOnlinePlayers())
		{
			playerData.put(x, new PlayerData(x, this));
		}
		
		Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new UpdatePreviews(this), 0, 2);
		
		createFlatFiles();
		
		//Usage tracking stuffs
		CallHome.load(this);
	}
	
	private void createFlatFiles()
	{
		new File(vChatDir).mkdir(); //Make directory
		File users = new File(vChatDir+File.separator+"vChat.users");
		if(!users.exists())
		{
			try {
				FileWriter writer = new FileWriter(users);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}