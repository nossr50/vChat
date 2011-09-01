package com.gmail.nossr50.vChat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.command.ColouredConsoleSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.getspout.spoutapi.SpoutManager;

import com.gmail.nossr50.vChat.vChat;
import com.gmail.nossr50.vChat.datatypes.PlayerData;
import com.gmail.nossr50.vChat.spout.vSpout;
import com.gmail.nossr50.vChat.util.ChatFormatter;
import com.gmail.nossr50.vChat.util.WordWrap;

public class playerListener extends PlayerListener
{
	vChat plugin = null;
	String specialChar = "^";
	
	public playerListener(vChat pluginx)
	{
		plugin = pluginx;
	}
	
	public void onPlayerChat(PlayerChatEvent event) 
	{
		if(event.isCancelled())
			return;
		
		event.setCancelled(true);
		
		Player player = event.getPlayer();
		PlayerData PD = plugin.playerData.get(player);
		String msg = event.getMessage();
		
		Player[] recipients = Bukkit.getServer().getOnlinePlayers();
		
		//TODO: This should probably only happen when changes are made to display name
		player.setDisplayName(PD.getPrefix()+ChatColor.WHITE+""+PD.getNickname()+ChatColor.WHITE+""+PD.getSuffix());
			
		if(msg.length() >= 1 && msg.startsWith(">"))
			msg=ChatColor.GREEN+msg;
		
		if(msg.contains(ChatFormatter.getSpecialChar()));
			msg = ChatFormatter.parseColors(msg);
			
		String formatted = "<" + player.getDisplayName() + ChatColor.WHITE + "> " + PD.getDefaultColor() + msg;
		
		//WordWrap
		String wrapped[] = WordWrap.wordWrapText(formatted);
		
		for(Player x : recipients)
		{
			for(String y : wrapped)
			{
				x.sendMessage(y);
			}
		}
		//Log stuff
		if(Bukkit.getServer() instanceof CraftServer)
		{
			final ColouredConsoleSender ccs = new ColouredConsoleSender((CraftServer)Bukkit.getServer());
			ccs.sendMessage(formatted); //Colors, woot!
		}
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) 
	{
		Player player = event.getPlayer();
		PlayerData PD = new PlayerData(player);
		
		plugin.playerData.put(player, PD);
		player.setDisplayName(PD.getPrefix()+ChatColor.WHITE+""+PD.getNickname()+ChatColor.WHITE+""+PD.getSuffix());
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) 
	{
		Player player = event.getPlayer();
		if(plugin.playerData.containsKey(event.getPlayer()))
			plugin.playerData.remove(player);
		
		if(plugin.spoutEnabled)
		{
			if(vSpout.playerScreens.containsKey(SpoutManager.getPlayer(player)))
				vSpout.playerScreens.remove(SpoutManager.getPlayer(player));
		}
	}
	
	
	
}