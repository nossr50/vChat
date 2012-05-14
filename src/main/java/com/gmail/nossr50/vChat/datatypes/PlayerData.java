package com.gmail.nossr50.vChat.datatypes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.TextField;

import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.gmail.nossr50.vChat.vChat;
import com.gmail.nossr50.vChat.spout.vSpout;
import com.gmail.nossr50.vChat.spout.textfields.ChatField;
import com.gmail.nossr50.vChat.spout.textfields.TextType;

public class PlayerData 
{
	String prefix, suffix, nickname, str_prefix, str_nickname, str_suffix, playerName;

	boolean pexPrefix, pexSuffix;
	
	String vChatDir = "plugins" + File.separator + "vChat";
	String activeChannel = "";
	String vChatUserFile = vChatDir + File.separator + "vChat.users";
	
	ChatColor defaultColor = ChatColor.WHITE;
	long lastTypedTime;
	
	public PlayerData(Player player)
	{
		playerName = player.getName();
		nickname = player.getDisplayName();
		prefix = "";
		suffix = "";
		
		str_prefix="";
		str_nickname="";
		str_suffix="";
		
		if(!load(player))
			addPlayer(player);
		
		//TODO: This is for testing purposes, going to make it functional later..
		activeChannel = "Global";
		
		if(vChat.spoutEnabled)
		{
			vSpout.createSpoutPlayerData(player);
		}
	}
	
	private void addPlayer(Player player)
	{
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(vChatUserFile, true));
			bw.append(player.getName()+":");
			bw.append(""+":");
			bw.append(player.getName()+":");
			bw.append(""+":");
			bw.append(""+":");
			bw.newLine(); //New line for the next guy
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean load(Player player)
	{
		File users = new File(vChatUserFile);
		try {
			//Setup the write/reader stuff
			BufferedReader reader = new BufferedReader(new FileReader(users));
			String line = "";
			//Actually do stuff when reading from it
			while((line = reader.readLine()) != null)
			{
				//PlayerName:Prefix:Nickname:Suffix:DefaultColor
				
				String split[] = line.split(":");
				
				if(!split[0].equals(player.getName()))
					continue;
				
				if(split.length >= 2 && split[1].length() >= 1)
					prefix = split[1];
				if(split.length >= 3 && split[2].length() >= 1)
					nickname = split[2];
				if(split.length >= 4 && split[3].length() >= 1)
					suffix = split[3];
				if(split.length >= 5 && split[4].length() >= 1)
					for(ChatColor x : ChatColor.values())
					{
						if(x.name().equals(split[4]))
						{
							defaultColor = x;
							break;
						}
					}
				reader.close(); //Close
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		if(Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
			if(prefix.equals("")) {
				prefix = (PermissionsEx.getPermissionManager().getUser(player).getPrefix() + " ").replaceAll("&([a-z0-9])", "\u00A7$1");
				pexPrefix = true;
			}
			if(suffix.equals("")) {
				suffix = PermissionsEx.getPermissionManager().getUser(player).getSuffix().replaceAll("&([a-z0-9])", "\u00A7$1");
				pexSuffix = true;
			}
			return true;
		} else {
			return true;
		}
	}
	
	public void save()
	{
		File users = new File(vChatUserFile);
		try {
			//Setup the write/reader stuff
			BufferedReader reader = new BufferedReader(new FileReader(users));
			StringBuilder writer = new StringBuilder();
			
			//Actually do stuff when reading from it
			String line = "";
			while((line = reader.readLine()) != null)
			{
				//PlayerName:Prefix:Nickname:Suffix:DefaultColor
				String split[] = line.split(":");
				if(!split[0].equals(playerName))
				{
					writer.append(line).append("\r\n");
				} else {
				String x = ":";
				prefix = (pexPrefix) ? "" : prefix;
				suffix = (pexSuffix) ? "" : suffix;
				String replacement = playerName+x+prefix+x+nickname+x+suffix+x+defaultColor.name()+x;
				writer.append(replacement).append("\r\n");
				}
			}
			reader.close(); //Close
			FileWriter out = new FileWriter(vChatUserFile);
			out.write(writer.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getActiveChatChannel()
	{
		return activeChannel;
	}
	
	public void setActiveChatChannel(String str)
	{
		activeChannel = str;
	}
	
	public long getLastTypedTime()
	{
		return lastTypedTime;
	}
	
	public void setLastTypedTime(long x)
	{
		lastTypedTime = x;
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
		    System.out.println(str_prefix.length());
			str_prefix = trimString(str_prefix);
			System.out.println(str_prefix.length());
		}
		break;
		case NICKNAME:
		{
			str_nickname = trimString(str_nickname);
		}
		break;
		case SUFFIX:
		{
			str_suffix = trimString(str_suffix);
		}
		break;
		}
	}
	
	public String trimString(String string) {
	    String newString = "";
	    char COLOR_CHAR = '\u00A7';
	    int pos = 0;
	    for(char x : string.toCharArray()) {
	        System.out.println("Char: "+x);
	        if(x == COLOR_CHAR && pos+2 == string.toCharArray().length) {
	            break;
	        } else if (pos+1 == string.toCharArray().length)
	            break;
	        
	        newString+=x;
	        pos++;
	    }
	    return newString;
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
	public void easyColor(TextType type, TextField field, ChatColor color)
	{
		String newText = "";
		switch(type)
		{
		case PREFIX:
			for(char x : field.getText().toCharArray())
			{
				newText+=color+String.valueOf(x);
			}
			str_prefix = newText;
			break;
		case NICKNAME:
			for(char x : field.getText().toCharArray())
			{
				newText+=color+String.valueOf(x);
			}
			str_nickname = newText;
			break;
		case SUFFIX:
			for(char x : field.getText().toCharArray())
			{
				newText+=color+String.valueOf(x);
			}
			str_suffix = newText;
			break;
		}
	}
}