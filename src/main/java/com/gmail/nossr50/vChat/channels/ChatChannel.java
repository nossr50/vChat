package com.gmail.nossr50.vChat.channels;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.nossr50.vChat.datatypes.PlayerData;

public class ChatChannel {
	ChannelType ct;
	ArrayList<Player> players = new ArrayList<Player>();
	boolean whitelist, passworded, hidden;
	String abbreviation, password, format;
	
	public ChatChannel(ChannelType ctt)
	{
		ct = ctt;
		abbreviation = "G";
	}
	
	public String getFormat(Player player, PlayerData PD, String msg)
	{
		return ChatColor.GREEN+"["+ChatColor.WHITE+abbreviation+ChatColor.GREEN+"] " +ChatColor.WHITE+ 
		"<" + player.getDisplayName() + ChatColor.WHITE + "> " + PD.getDefaultColor() + msg;
	}
	
	public String getAbrv()
	{
		return abbreviation;
	}
	
	public void addPlayer(Player player)
	{
		System.out.println("Added "+player.getName()+" to channel");
		players.add(player);
	}
	
	public void removePlayer(Player player)
	{
		players.remove(players.indexOf(player));
	}
	
	public boolean inChannel(Player player)
	{
		return players.contains(player);
	}
	
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
}
