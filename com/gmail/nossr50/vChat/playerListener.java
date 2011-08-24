package com.gmail.nossr50.vChat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.getspout.spoutapi.SpoutManager;

import com.gmail.nossr50.vChat.datatypes.PlayerData;
import com.gmail.nossr50.vChat.spout.vSpout;

public class playerListener extends PlayerListener
{
	vChat plugin = null;
	
	public playerListener(vChat pluginx)
	{
		plugin = pluginx;
	}
	
	public void onPlayerChat(PlayerChatEvent event) 
	{
		Player player = event.getPlayer();
		PlayerData PD = plugin.playerData.get(player);
		String msg = event.getMessage();
		
		if(PD != null)
		{
			player.setDisplayName(PD.getPrefix()+ChatColor.WHITE+""+PD.getNickname()+ChatColor.WHITE+""+PD.getSuffix());
			
			if(msg.length() >= 1 && msg.startsWith(">"))
				msg=ChatColor.GREEN+msg;
			
			event.setFormat("<" + player.getDisplayName() + ChatColor.WHITE + "> " + PD.getDefaultColor() + msg);
		}
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) 
	{
		Player player = event.getPlayer();
		PlayerData PD = new PlayerData(player);
		
		plugin.playerData.put(player, PD);
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