package com.gmail.nossr50.vChat.spout;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.gmail.nossr50.vChat.vChat;
import com.gmail.nossr50.vChat.datatypes.PlayerData;
import com.gmail.nossr50.vChat.spout.buttons.ColorButton;
import com.gmail.nossr50.vChat.spout.buttons.SetButton;
import com.gmail.nossr50.vChat.spout.screens.CustomizationScreen;
import com.gmail.nossr50.vChat.spout.textfields.ChatField;
import com.gmail.nossr50.vChat.util.Misc;

public class screenListener extends ScreenListener
{
	vChat plugin = null;
	public screenListener(vChat pluginx) {
		plugin = pluginx;
	}
	
	public void onButtonClick(ButtonClickEvent event) 
	{
		SpoutPlayer sPlayer = event.getPlayer();
		PlayerData PD = plugin.playerData.get(sPlayer);
		
		if(vSpout.playerScreens.get(sPlayer) != null)
		{
			CustomizationScreen screen = vSpout.playerScreens.get(sPlayer);
			
			if(event.getButton() instanceof SetButton)
			{
				screen.updatePlayerData(sPlayer);
				sPlayer.getMainScreen().closePopup();
				sPlayer.sendMessage(ChatColor.GOLD+"[vChat]"+ChatColor.GREEN+" Display name set to "+PD.getPrefix()+ChatColor.WHITE+PD.getNickname()+ChatColor.WHITE+PD.getSuffix());
				String broadcast = ChatColor.GOLD+"[vChat] "+ChatColor.RED+sPlayer.getName()+ChatColor.GREEN+" has changed their display name to "+PD.getPrefix()+ChatColor.WHITE+PD.getNickname()+ChatColor.WHITE+PD.getSuffix();
				Misc.broadcast(broadcast, sPlayer);
			} else if (event.getButton() instanceof ColorButton)
			{
				ColorButton cb = (ColorButton)event.getButton();
				screen.setSelectedColor(cb.getChatColor());
			}
		}
	}
	public void onScreenClose(ScreenCloseEvent event) 
	{
		if(event.getScreen() instanceof CustomizationScreen)
		{
			PlayerData PD = plugin.playerData.get(event.getPlayer());
			PD.clearBuiltStrings();
			
			vSpout.playerScreens.remove(event.getPlayer());
		}
	}
	public void buildString(String str, ChatField cf, SpoutPlayer sPlayer, CustomizationScreen screen)
	{
		PlayerData PD = plugin.playerData.get(sPlayer);
		switch(cf.getTextType())
		{
		case PREFIX:
		{
			PD.addToPrefix(screen.getColor()+str);
		}
		break;
		case NICKNAME:
		{
			PD.addToNickname(screen.getColor()+str);
		}
		break;
		case SUFFIX:
		{
			PD.addToSuffix(screen.getColor()+str);
		}
		break;
		}
	}
	public void onTextFieldChange(TextFieldChangeEvent event) 
	{
		SpoutPlayer sPlayer = event.getPlayer();
		
		if(event.getTextField() instanceof ChatField)
		{
			ChatField cf = (ChatField) event.getTextField();
			CustomizationScreen screen = vSpout.playerScreens.get(sPlayer);
			PlayerData PD = plugin.playerData.get(sPlayer);
			
			if(event.getNewText().length() > event.getOldText().length())
			{
				if(vSpout.playerScreens.get(sPlayer) != null)
				{
					String newtext = event.getNewText();
					
					String newChars =  newtext.substring(event.getOldText().length());
					
					buildString(newChars, cf, sPlayer, screen);
				}
			}
			else 
			{
				if(PD.getBuiltStringSize(cf.getTextType()) > 3)
				{
					PD.trimBuiltString(cf.getTextType(), 0, cf);
				} 
				else
				{
					PD.clearBuiltString(cf.getTextType());
				}
			}
			
			PD.setLastTypedTime(System.currentTimeMillis());
			screen.setPreviewLabelUpdated(false);
			sPlayer.getMainScreen().setDirty(true);
		}
	}
}