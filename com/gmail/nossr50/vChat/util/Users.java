package com.gmail.nossr50.vChat.util;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.gmail.nossr50.vChat.datatypes.PlayerData;

public class Users {
    public static HashMap<Player, PlayerData> playerData = new HashMap<Player, PlayerData>();
    
    public static PlayerData getPlayerData(Player player)
    {
        if(playerData.containsKey(player))
        {
            return playerData.get(player);
        } else {
            PlayerData PD = new PlayerData(player);
            playerData.put(player, PD);
            return playerData.get(player);
        }
    }
    
    public static void addPlayerData(Player player)
    {
        if(!playerData.containsKey(player))
        {
            PlayerData PD = new PlayerData(player);
            playerData.put(player, PD);
        }
    }
    
    public static void removePlayerData(Player player)
    {
        if(playerData.containsKey(player))
            playerData.remove(player);
    }
}
