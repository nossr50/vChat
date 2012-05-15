package com.gmail.nossr50.vChat.spout.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.event.screen.ScreenOpenEvent;
import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.gmail.nossr50.vChat.vChat;
import com.gmail.nossr50.vChat.datatypes.PlayerData;
import com.gmail.nossr50.vChat.datatypes.SpoutPlayerData;
import com.gmail.nossr50.vChat.spout.vSpout;
import com.gmail.nossr50.vChat.spout.buttons.ColorButton;
import com.gmail.nossr50.vChat.spout.buttons.DefaultsButton;
import com.gmail.nossr50.vChat.spout.buttons.EasyColor;
import com.gmail.nossr50.vChat.spout.buttons.EasyDefault;
import com.gmail.nossr50.vChat.spout.buttons.EscapeButton;
import com.gmail.nossr50.vChat.spout.buttons.SetButton;
import com.gmail.nossr50.vChat.spout.buttons.TextColorButton;
import com.gmail.nossr50.vChat.spout.screens.CustomizationScreen;
import com.gmail.nossr50.vChat.spout.textfields.ChatField;
import com.gmail.nossr50.vChat.spout.textfields.TextType;
import com.gmail.nossr50.vChat.util.Misc;
import com.gmail.nossr50.vChat.util.Users;

public class ScreenListener implements Listener
{
	vChat plugin = null;
	public ScreenListener(vChat pluginx) {
		plugin = pluginx;
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onButtonClick(ButtonClickEvent event) 
	{
		SpoutPlayer sPlayer = event.getPlayer();
		PlayerData PD = Users.getPlayerData(sPlayer);
		String format = ChatColor.GOLD+"[vChat] "+ChatColor.GREEN;
		
		if(vSpout.playerScreens.get(sPlayer) != null)
		{
			CustomizationScreen screen = vSpout.playerScreens.get(sPlayer);
			Button button = event.getButton();
			if(button instanceof SetButton)
			{
				String before[] = {PD.getPrefix(), PD.getNickname(), PD.getSuffix(), PD.getDefaultColor().name()};
				
				screen.updatePlayerData(sPlayer);
				sPlayer.getMainScreen().closePopup();
				
				if(!before[0].equals(PD.getPrefix()) || !before[1].equals(PD.getNickname()) || !before[2].equals(PD.getSuffix()))
				{
					sPlayer.sendMessage(format+ChatColor.GREEN+"Display name set to "+PD.getPrefix()+ChatColor.WHITE+PD.getNickname()+ChatColor.WHITE+PD.getSuffix());
					String broadcast = format+ChatColor.RED+sPlayer.getName()+ChatColor.GREEN+" has changed their display name to "+PD.getPrefix()+ChatColor.WHITE+PD.getNickname()+ChatColor.WHITE+PD.getSuffix();
					Misc.broadcast(broadcast, sPlayer);
				}
				if (!before[3].equals(PD.getDefaultColor().name()))
				{
					sPlayer.sendMessage(format+"default chat color set to "+PD.getDefaultColor()+PD.getDefaultColor().name());
				}
				
				PD.save(); //Save player info to file
			} else if (button instanceof ColorButton)
			{
				ColorButton cb = (ColorButton)event.getButton();
				screen.changeColorButtonDisplay(cb);
				screen.setColorButtonLastClicked(cb);
				screen.setSelectedColor(cb.getChatColor());
			} else if (button instanceof EasyColor)
			{
				EasyColor easyColor = (EasyColor)button;
				PD.easyColor(easyColor.getTextType(), screen.getTextField(easyColor.getTextType()), screen.getColor());
				screen.setPreviewLabelUpdated(false);
			} else if (button instanceof EasyDefault)
			{
				EasyDefault easyDefault = (EasyDefault)button;
				PD.clearBuiltString(easyDefault.getTextType());
				screen.getTextField(easyDefault.getTextType()).setText("").setDirty(true);
				screen.setPreviewLabelUpdated(false);
			} else if (button instanceof DefaultsButton)
			{
				PD.setDefaultColor(ChatColor.WHITE);
				PD.clearBuiltNickname();
				PD.clearBuiltPrefix();
				PD.clearBuiltSuffix();
				PD.setPrefix("");
				PD.setSuffix("");
				PD.setNickname(sPlayer.getName());
				screen.getTextField(TextType.PREFIX).setText("").setDirty(true);
				screen.getTextField(TextType.NICKNAME).setText("").setDirty(true);
				screen.getTextField(TextType.SUFFIX).setText("").setDirty(true);
				sPlayer.getMainScreen().closePopup();
				sPlayer.sendMessage(format+ChatColor.RED+"all data has been wiped, you horrible person!");
			} else if (button instanceof TextColorButton)
			{
				TextColorButton textColorButton = (TextColorButton)button;
				textColorButton.SetSelectedColor(screen.getColor());
				screen.setPreviewLabelUpdated(false);
			} else if (button instanceof EscapeButton)
			{
				sPlayer.getMainScreen().closePopup();
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onScreenClose(ScreenCloseEvent event) 
	{
		if(event.getScreen() instanceof CustomizationScreen)
		{
			PlayerData PD = Users.getPlayerData(event.getPlayer());
			PD.clearBuiltStrings();
			
			vSpout.playerScreens.remove(event.getPlayer());
		}
		else if(event.getScreenType() == ScreenType.CHAT_SCREEN)
		{
			SpoutPlayerData SPD = vSpout.spoutPlayerData.get(event.getPlayer());
			event.getPlayer().getMainScreen().removeWidget(SPD.getActiveChannelLabel());
			event.getPlayer().getMainScreen().removeWidget(SPD.getChannelsLabel());
		}
	}
	
	public void buildString(String str, ChatField cf, SpoutPlayer sPlayer, CustomizationScreen screen)
	{
		PlayerData PD = Users.getPlayerData(sPlayer);
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
	
	@EventHandler(ignoreCancelled = true)
	public void onTextFieldChange(TextFieldChangeEvent event) 
	{
		SpoutPlayer sPlayer = event.getPlayer();
		
		if(event.getTextField() instanceof ChatField)
		{
			ChatField cf = (ChatField) event.getTextField();
			CustomizationScreen screen = vSpout.playerScreens.get(sPlayer);
			PlayerData PD = Users.getPlayerData(sPlayer);
			
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
	
	@EventHandler(ignoreCancelled = true)
	public void onScreenOpen(final ScreenOpenEvent event) {
		SpoutPlayer sPlayer = event.getPlayer();
		
		if(event.getScreenType() == ScreenType.CHAT_SCREEN)
		{
			SpoutPlayerData SPD = vSpout.spoutPlayerData.get(sPlayer);
			sPlayer.getMainScreen().attachWidget(plugin, SPD.getActiveChannelLabel());
			sPlayer.getMainScreen().attachWidget(plugin, SPD.getChannelsLabel());
		}
	}
	
}