package com.gmail.nossr50.vChat.spout;

import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.gmail.nossr50.vChat.vChat;
import com.gmail.nossr50.vChat.datatypes.PlayerData;
import com.gmail.nossr50.vChat.spout.screens.CustomizationScreen;

public class inputListener extends InputListener
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
			if(plugin.playerData.containsKey(sPlayer))
			{
				PlayerData PD = plugin.playerData.get(sPlayer);
				CustomizationScreen screen = new CustomizationScreen(PD, plugin);
				
				vSpout.playerScreens.put(sPlayer, screen);
				sPlayer.getMainScreen().attachPopupScreen(vSpout.playerScreens.get(sPlayer));
				sPlayer.getMainScreen().setDirty(true);
			}
		}
	}
}