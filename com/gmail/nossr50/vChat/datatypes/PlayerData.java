package com.gmail.nossr50.vChat.datatypes;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.nossr50.vChat.spout.textfields.ChatField;
import com.gmail.nossr50.vChat.spout.textfields.TextType;

public class PlayerData 
{
	String prefix, suffix, nickname, str_prefix, str_nickname, str_suffix;
	ChatColor defaultColor = ChatColor.WHITE;
	long lastTypedTime;
	boolean isChanging = false;
	
	public PlayerData(Player player)
	{
		nickname = player.getDisplayName();
		prefix = "";
		suffix = "";
		
		str_prefix="";
		str_nickname="";
		str_suffix="";
	}
	
	public long getLastTypedTime()
	{
		return lastTypedTime;
	}
	
	public void setLastTypedTime(long x)
	{
		lastTypedTime = x;
	}
	
	public boolean isChanging()
	{
		return isChanging;
	}
	
	public void toggleIsChanging()
	{
		isChanging = !isChanging;
	}
	
	public void clearBuiltPrefix()
	{
		str_prefix="";
	}
	
	public void clearBuiltNickname()
	{
		str_nickname="";
	}
	
	public void clearBuiltSuffix()
	{
		str_suffix="";
	}
	
	public void addToPrefix(String add)
	{
		str_prefix+=add;
	}
	
	public void addToNickname(String add)
	{
		str_nickname+=add;
	}
	
	public void addToSuffix(String add)
	{
		str_suffix+=add;
	}
	
	public ChatColor getDefaultColor()
	{
		return defaultColor;
	}
	
	public String getBuiltPrefix()
	{
		return str_prefix;
	}
	
	public String getBuiltNickname()
	{
		return str_nickname;
	}
	
	public String getBuiltSuffix()
	{
		return str_suffix;
	}
	
	public void setDefaultColor(ChatColor color)
	{
		defaultColor = color;
	}
	public void setBuiltString(TextType type, String str)
	{
		switch(type)
		{
		case PREFIX:
			str_prefix=str;
			break;
		case NICKNAME:
			str_nickname=str;
			break;
		case SUFFIX:
			str_suffix=str;
			break;
		}
	}
	
	public void setPrefix(String str)
	{
		prefix = str;
	}
	
	public void setSuffix(String str)
	{
		suffix = str;
	}
	
	public void setNickname(String str)
	{
		nickname = str;
	}
	
	public String getPrefix()
	{
		return prefix;
	}
	
	public String getSuffix()
	{
		return suffix;
	}
	
	public String getNickname()
	{
		return nickname;
	}
	
	public void clearBuiltStrings()
	{
		clearBuiltNickname();
		clearBuiltPrefix();
		clearBuiltSuffix();
	}
	public void trimBuiltString(TextType type, int start, ChatField cf)
	{
		int maxsize = (cf.getText().length()*3)-3;
		
		switch(type)
		{
		case PREFIX:
		{
			str_prefix = str_prefix.substring(start, maxsize);
		}
		break;
		case NICKNAME:
		{
			str_nickname = str_nickname.substring(start, maxsize);
		}
		break;
		case SUFFIX:
		{
			str_suffix = str_suffix.substring(start, maxsize);
		}
		break;
		}
	}
	public int getBuiltStringSize(TextType type)
	{
		switch(type)
		{
		case PREFIX:
			return str_prefix.length();
		case NICKNAME:
			return str_nickname.length();
		case SUFFIX:
			return str_suffix.length();
		default:
			return 0;
		}
	}
	public void clearBuiltString(TextType type)
	{
		switch(type)
		{
		case PREFIX:
			str_prefix = "";
			break;
		case NICKNAME:
			str_nickname = "";
			break;
		case SUFFIX:
			str_suffix = "";
			break;
		}
	}
}