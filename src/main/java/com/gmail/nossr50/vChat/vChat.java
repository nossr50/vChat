package com.gmail.nossr50.vChat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.nossr50.vChat.channels.ChannelManager;
import com.gmail.nossr50.vChat.listeners.PlayerListener;
import com.gmail.nossr50.vChat.spout.vSpout;
import com.gmail.nossr50.vChat.spout.listeners.InputListener;
import com.gmail.nossr50.vChat.spout.listeners.ScreenListener;
import com.gmail.nossr50.vChat.spout.runnables.UpdatePreviews;
import com.gmail.nossr50.vChat.util.Users;

public class vChat extends JavaPlugin
{
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
	    //Setup variables
		PluginManager PM = Bukkit.getServer().getPluginManager();
		PlayerListener playerListener = new PlayerListener();
		
		//Register Events
		PM.registerEvents(playerListener, this);
		
		//Create default channels
		ChannelManager.createDefaultChannels();
		
		if(PM.getPlugin("Spout") != null)
		{
			spoutEnabled = true;
			PM.registerEvents(new InputListener(this), this);
			PM.registerEvents(new ScreenListener(this), this);
		}
		
		//For reloading
		for(Player x : Bukkit.getServer().getOnlinePlayers())
		{
			Users.addPlayerData(x);
		}
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new UpdatePreviews(this), 0, 2);
		
		createFlatFiles();
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