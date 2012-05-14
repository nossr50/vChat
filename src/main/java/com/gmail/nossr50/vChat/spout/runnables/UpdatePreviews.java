package com.gmail.nossr50.vChat.spout.runnables;

import org.getspout.spoutapi.player.SpoutPlayer;
import com.gmail.nossr50.vChat.vChat;
import com.gmail.nossr50.vChat.datatypes.PlayerData;
import com.gmail.nossr50.vChat.spout.vSpout;
import com.gmail.nossr50.vChat.spout.screens.CustomizationScreen;
import com.gmail.nossr50.vChat.util.Users;

public class UpdatePreviews implements Runnable {

	vChat plugin = null;
	
	public UpdatePreviews(vChat pluginx)
	{
		plugin = pluginx;
	}
	
	public void run()
	{
		for(SpoutPlayer sPlayer : vSpout.playerScreens.keySet())
		{
			if(sPlayer.isSpoutCraftEnabled() && sPlayer.getMainScreen().getActivePopup() != null && sPlayer.getMainScreen().getActivePopup() instanceof CustomizationScreen)
			{
				CustomizationScreen screen = (CustomizationScreen)sPlayer.getMainScreen().getActivePopup();
				PlayerData PD = Users.getPlayerData(sPlayer);
				
				if(!screen.hasPreviewLabelUpdated() && System.currentTimeMillis() - 500 >= PD.getLastTypedTime())
				{
					screen.updatePreviewLabels(PD);
				}
			}
		}
	}

}
