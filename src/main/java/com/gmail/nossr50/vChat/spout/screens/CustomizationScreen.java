package com.gmail.nossr50.vChat.spout.screens;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.TextField;

import com.gmail.nossr50.vChat.vChat;
import com.gmail.nossr50.vChat.datatypes.PlayerData;
import com.gmail.nossr50.vChat.spout.buttons.ColorButton;
import com.gmail.nossr50.vChat.spout.buttons.DefaultsButton;
import com.gmail.nossr50.vChat.spout.buttons.EasyColor;
import com.gmail.nossr50.vChat.spout.buttons.EasyDefault;
import com.gmail.nossr50.vChat.spout.buttons.EscapeButton;
import com.gmail.nossr50.vChat.spout.buttons.SetButton;
import com.gmail.nossr50.vChat.spout.buttons.TextColorButton;
import com.gmail.nossr50.vChat.spout.textfields.ChatField;
import com.gmail.nossr50.vChat.spout.textfields.TextType;
import com.gmail.nossr50.vChat.util.Users;
import com.gmail.nossr50.vChat.util.WordWrap;

public class CustomizationScreen extends GenericPopup
{
	vChat plugin = null;
	
	int center_x = 427/2;
	int center_y = 240/2;
	int spacing = 10;
	int small_spacing = 1;
	int limit_prefix = 6;
	int limit_suffix = 6;
	int limit_nickname = 12;
	
	boolean previewLabelUpdated = true;
	
	ChatField prefixField = null;
	ChatField suffixField = null;
	ChatField nickNameField = null;
	
	EasyColor color_prefix = new EasyColor(TextType.PREFIX);
	EasyColor color_nickname = new EasyColor(TextType.NICKNAME);
	EasyColor color_suffix = new EasyColor(TextType.SUFFIX);

	EasyDefault default_prefix = new EasyDefault(TextType.PREFIX);
	EasyDefault default_nickname = new EasyDefault(TextType.NICKNAME);
	EasyDefault default_suffix = new EasyDefault(TextType.SUFFIX);
	
	ChatColor selectedColor = ChatColor.WHITE;
	
	GenericLabel label_prefix = new GenericLabel();
	GenericLabel label_nickname = new GenericLabel();
	GenericLabel label_suffix = new GenericLabel();
	GenericLabel label_menu = new GenericLabel();
	GenericLabel label_totalsize_prefix = new GenericLabel();
	GenericLabel label_totalsize_nickname = new GenericLabel();
	GenericLabel label_totalsize_suffix = new GenericLabel();
	
	GenericLabel label_preview_prefix = new GenericLabel();
	GenericLabel label_preview_nickname = new GenericLabel();
	GenericLabel label_preview_suffix = new GenericLabel();
	
	ArrayList<ColorButton> colorButtons = new ArrayList<ColorButton>();
	UUID lastClicked = null;
	
	SetButton setButton = new SetButton();
	EscapeButton escapeButton = new EscapeButton();
	TextColorButton textColorButton = null;
	DefaultsButton defaultsButton = new DefaultsButton();
	
	public CustomizationScreen(PlayerData PD, vChat pluginx)
	{
		plugin = pluginx;
		textColorButton = new TextColorButton(PD);
		
		for(ChatColor x : ChatColor.values())
		{
		    if(x.equals(ChatColor.ITALIC) || x.equals(ChatColor.RESET) || x.equals(ChatColor.STRIKETHROUGH) || x.equals(ChatColor.BOLD) || x.equals(ChatColor.MAGIC) || x.equals(ChatColor.UNDERLINE))
		        continue;
			ColorButton y = new ColorButton(x);
			y.setDirty(true);
			colorButtons.add(y);
		}
		
		int offset = 26;
		int count = 0;
		int pos_x = offset, pos_y = 240-20;
		
		for(ColorButton x : colorButtons)
		{
			if(count == 15)
			{
				x.setX(176).setY(pos_y-x.getHeight());
			} 
			else 
			{
				if(pos_x+x.getWidth() < (center_x*2))
				{
					x.setX(pos_x).setY(pos_y);
				} else {
					pos_y -= x.getHeight();
					pos_x = offset;
					
					x.setX(pos_x).setY(pos_y);
				}
				
				pos_x+=x.getWidth();
			}
			
			count++;
			this.attachWidget(plugin, x);
		}
		
		prefixField = new ChatField(TextType.PREFIX);
		suffixField = new ChatField(TextType.SUFFIX);
		nickNameField = new ChatField(TextType.NICKNAME);
		
		label_menu.setText(ChatColor.GREEN+"vChat Name Customization Menu (ALPHA)").setX(0).setY(0).setDirty(true);
		
		//nickname = center
		default_nickname.setX(center_x-(nickNameField.getWidth()/2)-default_nickname.getWidth()-3).setY(center_y/2-default_nickname.getHeight()-small_spacing).setDirty(true);
		nickNameField.setX(center_x-(nickNameField.getWidth()/2)).setY(center_y/2).setDirty(true);
		label_preview_nickname.setX(nickNameField.getX()).setY(nickNameField.getY()+nickNameField.getHeight()+(spacing/2)).setDirty(true);
		label_nickname.setText(ChatColor.GOLD+"Nickname").setX(nickNameField.getX()).setY(nickNameField.getY()-spacing).setDirty(true);
		color_nickname.setX(center_x-(nickNameField.getWidth()/2)-color_nickname.getWidth()-3).setY(nickNameField.getY()+small_spacing).setDirty(true);
		label_totalsize_nickname.setText(ChatColor.GRAY+"0/"+limit_nickname).setPriority(RenderPriority.Lowest).setDirty(true);
		label_totalsize_nickname.setX((nickNameField.getX()+nickNameField.getWidth())-WordWrap.getStringSize(label_totalsize_nickname.getText())-1).setY(nickNameField.getY()+2).setDirty(true);
		
		//prefix = left
		prefixField.setX(default_nickname.getX()-prefixField.getWidth()-spacing).setY(center_y/2).setDirty(true);
		label_preview_prefix.setX(prefixField.getX()).setY(prefixField.getY()+prefixField.getHeight()+(spacing/2)).setDirty(true);
		label_prefix.setText(ChatColor.GOLD+"Prefix").setX(prefixField.getX()).setY(prefixField.getY()-spacing).setDirty(true);
		color_prefix.setX(prefixField.getX()-color_prefix.getWidth()-3).setY(prefixField.getY()+small_spacing).setDirty(true);
		default_prefix.setX(prefixField.getX()-default_prefix.getWidth()-3).setY(center_y/2-default_prefix.getHeight()-small_spacing).setDirty(true);
		label_totalsize_prefix.setText(ChatColor.GRAY+"0/"+limit_prefix).setPriority(RenderPriority.Lowest).setDirty(true);
		label_totalsize_prefix.setX((prefixField.getX()+prefixField.getWidth())-WordWrap.getStringSize(label_totalsize_prefix.getText())-1).setY(prefixField.getY()+2).setDirty(true);
		
		//suffix = right
		suffixField.setX(nickNameField.getX()+nickNameField.getWidth()+default_suffix.getWidth()+spacing).setY(center_y/2).setDirty(true);
		label_preview_suffix.setX(suffixField.getX()+3).setY(suffixField.getY()+suffixField.getHeight()+(spacing/2)).setDirty(true);
		label_suffix.setText(ChatColor.GOLD+"Suffix").setX(suffixField.getX()).setY(suffixField.getY()-spacing).setDirty(true);
		color_suffix.setX(suffixField.getX()-color_suffix.getWidth()-3).setY(suffixField.getY()+small_spacing).setDirty(true);
		default_suffix.setX(suffixField.getX()-default_suffix.getWidth()-3).setY(center_y/2-default_suffix.getHeight()-small_spacing).setDirty(true);
		label_totalsize_suffix.setText(ChatColor.GRAY+"0/"+limit_suffix).setPriority(RenderPriority.Lowest).setDirty(true);
		label_totalsize_suffix.setX((suffixField.getX()+suffixField.getWidth())-WordWrap.getStringSize(label_totalsize_suffix.getText())-1).setY(suffixField.getY()+2).setDirty(true);
		
		setButton.setX(center_x-(setButton.getWidth()/2)).setY(nickNameField.getY()+spacing+setButton.getHeight()).setDirty(true);		
		escapeButton.setX(setButton.getX()+escapeButton.getWidth()).setY(setButton.getY()).setDirty(true);
		defaultsButton.setX(setButton.getX()-defaultsButton.getWidth()).setY(setButton.getY()).setDirty(true);
		textColorButton.setX(defaultsButton.getX()).setY(defaultsButton.getY()+textColorButton.getHeight()).setDirty(true);
		
		this.attachWidget(plugin, prefixField);
		this.attachWidget(plugin, nickNameField);
		this.attachWidget(plugin, suffixField);
		this.attachWidget(plugin, setButton);
		this.attachWidget(plugin, label_menu);
		this.attachWidget(plugin, label_prefix);
		this.attachWidget(plugin, label_nickname);
		this.attachWidget(plugin, label_suffix);
		this.attachWidget(plugin, label_totalsize_nickname);
		this.attachWidget(plugin, label_totalsize_prefix);
		this.attachWidget(plugin, label_totalsize_suffix);
		this.attachWidget(plugin, label_preview_prefix);
		this.attachWidget(plugin, label_preview_nickname);
		this.attachWidget(plugin, label_preview_suffix);
		this.attachWidget(plugin, color_nickname);
		this.attachWidget(plugin, default_nickname);
		this.attachWidget(plugin, color_prefix);
		this.attachWidget(plugin, default_prefix);
		this.attachWidget(plugin, color_suffix);
		this.attachWidget(plugin, default_suffix);
		this.attachWidget(plugin, escapeButton);
		this.attachWidget(plugin, defaultsButton);
		this.attachWidget(plugin, textColorButton);
		
		this.setDirty(true);
	}
	public ChatColor getColor()
	{
		return selectedColor;
	}
	
	public void updatePreviewLabels(PlayerData PD)
	{
		label_preview_prefix.setText(PD.getBuiltPrefix()).setDirty(true);
		label_preview_nickname.setText(PD.getBuiltNickname()).setDirty(true);
		label_preview_suffix.setText(PD.getBuiltSuffix()).setDirty(true);
		
		ChatColor prefix = ChatColor.GRAY, nickname = ChatColor.GRAY, suffix = ChatColor.GRAY;
		
		if(prefixField.getText().length() > limit_prefix)
			prefix = ChatColor.DARK_RED;
		if(nickNameField.getText().length() > limit_nickname)
			nickname = ChatColor.DARK_RED;
		if(suffixField.getText().length() > limit_suffix)
			suffix = ChatColor.DARK_RED;
		
		label_totalsize_prefix.setText(prefix+String.valueOf(prefixField.getText().length())+"/"+limit_prefix).setDirty(true);
		label_totalsize_nickname.setText(nickname+String.valueOf(nickNameField.getText().length())+"/"+limit_nickname).setDirty(true);
		label_totalsize_suffix.setText(suffix+String.valueOf(suffixField.getText().length())+"/"+limit_suffix).setDirty(true);
		
		previewLabelUpdated = true;
	}
	
	public boolean hasPreviewLabelUpdated()
	{
		return previewLabelUpdated;
	}
	public void setPreviewLabelUpdated(boolean bool)
	{
		previewLabelUpdated = bool;
	}
	
	public String insertColor(TextType type, PlayerData PD, String str)
	{
		System.out.println("Its doing something");
		
		switch(type)
		{
		case PREFIX:
		{
			char[] split = str.toCharArray();
			str = "";
			int y = 0;
			for(char x : split)
			{
				if(y == prefixField.getCursorPosition())
				{
					str+=selectedColor;
				}
				str+=x;
				y++;
			}
			PD.setPrefix(str);
			return str;
		}
		case NICKNAME:
		{
			char[] split = str.toCharArray();
			str = "";
			int y = 0;
			for(char x : split)
			{
				if(y == nickNameField.getCursorPosition())
				{
					str+=selectedColor;
				}
				str+=x;
				y++;
			}
			PD.setNickname(str);
			return str;
		}
		case SUFFIX:
		{
			char[] split = str.toCharArray();
			str = "";
			int y = 0;
			for(char x : split)
			{
				if(y == suffixField.getCursorPosition())
				{
					str+=selectedColor;
				}
				str+=x;
				y++;
			}
			PD.setSuffix(str);
			return str;
		}
		default:
			return null;
		}
	}
	public void setSelectedColor(ChatColor color)
	{
		selectedColor = color;
	}
	public void updatePlayerData(Player player)
	{
		String format = ChatColor.GOLD+"[vChat] "+ChatColor.RED;
		PlayerData PD = Users.getPlayerData(player);
		
		if(PD.getBuiltNickname().length() >= 3 )
		{
			if(player.isOp() && nickNameField.getText().length() <= limit_nickname)
			{
				PD.setNickname(PD.getBuiltNickname().replace(":", "."));
				PD.clearBuiltNickname();
				nickNameField.setText("").setDirty(true);
			} else if (!player.isOp())
			{
				player.sendMessage(format+"Changing the nickname field is currently restricted to Ops");
			} else {
				player.sendMessage(format+"nicknames can only consist of "+limit_nickname+" or less characters");
			}
		}
		
		if(PD.getBuiltPrefix().length() >= 3)
		{
			if(prefixField.getText().length() <= limit_prefix)
			{
				PD.setPrefix(PD.getBuiltPrefix().replace(":", "."));
				PD.clearBuiltPrefix();
				prefixField.setText("").setDirty(true);
			} else if (prefixField.getText().length() > limit_prefix){
				player.sendMessage(format+"prefixes can only consist of "+limit_prefix+" or less characters");
			}
		}
		
		if(PD.getBuiltSuffix().length() >= 3)
		{
			if(suffixField.getText().length() <= limit_suffix)
			{
				PD.setSuffix(PD.getBuiltSuffix().replace(":", "."));
				PD.clearBuiltSuffix();
				suffixField.setText("").setDirty(true);
			} else if (suffixField.getText().length() <= limit_suffix){
				player.sendMessage(format+"suffixes can only consist of "+limit_suffix+" or less characters");
			}
		}
		
		PD.setDefaultColor(textColorButton.getSelectedColor());
		this.setDirty(true);
	}
	public TextField getTextField(TextType type)
	{
		switch(type)
		{
		case PREFIX:
			return prefixField;
		case NICKNAME:
			return nickNameField;
		case SUFFIX:
			return suffixField;
		default:
			return null;
		}
	}
	public void setColorButtonLastClicked(ColorButton cb)
	{
		lastClicked = cb.getId();
	}
	public UUID getColorButtonLastClicked()
	{
		return lastClicked;
	}
	public void changeColorButtonDisplay(ColorButton cb)
	{
		if(lastClicked != null)
		{
			for(ColorButton x : colorButtons)
			{
				if(x.getId() == getColorButtonLastClicked())
				{
					x.reset();
				}
			}
		}
		
		cb.setText(">"+cb.getText()+ChatColor.WHITE+"<").setDirty(true);
	}
}