package com.gmail.nossr50.vChat.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.getspout.spoutapi.SpoutManager;

import com.gmail.nossr50.vChat.vChat;
import com.gmail.nossr50.vChat.channels.ChannelManager;
import com.gmail.nossr50.vChat.channels.ChatChannel;
import com.gmail.nossr50.vChat.datatypes.PlayerData;
import com.gmail.nossr50.vChat.spout.vSpout;
import com.gmail.nossr50.vChat.util.ChatFormatter;
import com.gmail.nossr50.vChat.util.Users;
import com.gmail.nossr50.vChat.util.WordWrap;

public class PlayerListener implements Listener
{
	
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerChat(PlayerChatEvent event) 
    {
        if(event.isCancelled())
            return;
        
        event.setCancelled(true);
        
        Player player = event.getPlayer();
        PlayerData PD = Users.getPlayerData(player);
        String msg = event.getMessage();
        
        //TODO: This should probably only happen when changes are made to display name
        player.setDisplayName(PD.getPrefix()+ChatColor.WHITE+""+PD.getNickname()+ChatColor.WHITE+""+PD.getSuffix());
            
        if(msg.length() >= 1 && msg.startsWith(">"))
            msg=ChatColor.GREEN+msg;
        
        if(msg.contains(ChatFormatter.getSpecialChar()));
            msg = ChatFormatter.parseColors(msg);
            
        //Chat Channel Stuff
        for(ChatChannel cc : ChannelManager.chatChannels.values())
        {
            if(cc.inChannel(player))
            {
                //Get the format
                String formatted = cc.getFormat(player, PD, msg);
                //toLog = formatted;
                
                //TODO: Make a new wordwrap and fix the functionality of this plugin
                
                //WordWrap
                
                String wrapped[] = WordWrap.wordWrapText(formatted);
                
                for(Player x : cc.getPlayers())
                {
                    for(String y : wrapped)
                    {
                        x.sendMessage(y);
                    }
                }
                
            }
        }
        
        //TODO: Add logging
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) 
    {
        Player player = event.getPlayer();
        
        ChannelManager.assignPlayersToChannels("Global", player);
        
        Users.addPlayerData(player);
        PlayerData PD = Users.getPlayerData(player);
        player.setDisplayName(PD.getPrefix()+ChatColor.WHITE+""+PD.getNickname()+ChatColor.WHITE+""+PD.getSuffix());
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) 
    {
        Player player = event.getPlayer();
        
        Users.removePlayerData(player); //Garbage collection
        
        if(vChat.spoutEnabled)
        {
            if(vSpout.playerScreens.containsKey(SpoutManager.getPlayer(player)))
                vSpout.playerScreens.remove(SpoutManager.getPlayer(player));
            if(vSpout.spoutPlayerData.containsKey(SpoutManager.getPlayer(player)))
                vSpout.spoutPlayerData.remove(SpoutManager.getPlayer(player));
        }
    }
	
	
}