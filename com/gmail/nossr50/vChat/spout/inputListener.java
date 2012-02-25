package com.gmail.nossr50.vChat.spout;

import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.gmail.nossr50.vChat.vChat;
import com.gmail.nossr50.vChat.datatypes.PlayerData;
import com.gmail.nossr50.vChat.spout.screens.CustomizationScreen;
import com.gmail.nossr50.vChat.util.Users;

public class inputListener implements Listener
{
	vChat plugin = null;
	
	public inputListener(vChat pluginx)
	{
		plugin = pluginx;
	}
	
	public void onKeyPressedEvent(KeyPressedEvent event) 
	{
		if(event.getScreenType() != ScreenType.GAME_SCREEN)
			return;
		
		SpoutPlayer sPlayer = event.getPlayer();
		
		if(sPlayer.isSpoutCraftEnabled() && event.getKey() == Keyboard.KEY_C)
		{
			PlayerData PD = Users.getPlayerData(sPlayer);
			CustomizationScreen screen = new CustomizationScreen(PD, plugin);
				
			vSpout.playerScreens.put(sPlayer, screen);
			sPlayer.getMainScreen().attachPopupScreen(vSpout.playerScreens.get(sPlayer));
			sPlayer.getMainScreen().setDirty(true);
		}
	}
}