package com.gmail.nossr50.vChat.util;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.TextWrapper;

public class WordWrap extends TextWrapper
{
	//The values regarding the length of the chat window and how big each character was is shamelessly borrowed from CraftBukkit
	//Using their values they worked out is the best way to implement an accurate and robust wordwrap 
	//And unfortunately there was no way to grab these values through the Bukkit API as of the time of this writing
	//I only took values I needed to code a proper word wrap, wish I could've grabbed them from the API honestly
	
	private static final int[] characterWidths = new int[] {
        1, 9, 9, 8, 8, 8, 8, 7, 9, 8, 9, 9, 8, 9, 9, 9,
        8, 8, 8, 8, 9, 9, 8, 9, 8, 8, 8, 8, 8, 9, 9, 9,
        4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6, 2, 6, 2, 6,
        6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6,
        7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6,
        6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4, 6, 6,
        3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6,
        6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6,
        6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 3, 6, 6,
        6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6,
        6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6,
        8, 9, 9, 6, 6, 6, 8, 8, 6, 8, 8, 8, 8, 8, 6, 6,
        9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
        9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9, 9, 5, 9, 9,
        8, 7, 7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7,
        7, 7, 7, 7, 9, 6, 7, 8, 7, 6, 6, 9, 7, 6, 7, 1
    };
	
    private static final char COLOR_CHAR = '\u00A7';
    private static ChatColor lastcolor = ChatColor.WHITE;
    private static final String allowedChars = net.minecraft.server.FontAllowedCharacters.allowedCharacters;
    
	public static String[] wordWrapText(String format)
	{
		boolean shouldSkip = false;
		char space = ' ';
		String builtString = "";
		int currentSize = 0;
		int pos = 0;
		int maxWidth = 317;
		
		for(char x : format.toCharArray())
		{
			builtString+=x;
			
			if(x == space)
			{
				if(currentSize + getCharWidth(x) >= maxWidth)
				{
					//If the first char on the next line is a space we want to forget about the size of the built string so far
					//since the MC vanilla wordwrap kicks in
					currentSize-=getSize(builtString);
					builtString = builtString.substring(0, builtString.length()-1); //Should stop the second line from starting with a space
					//currentSize+=getCharWidth(x);
					continue;
				}
				//lastKnownSpacePos=pos;
				String charsUntilSpace = "";
				int charPos = pos+1;
				
				while(charPos+1 <= format.length() && format.charAt(charPos) != space)
				{
					charsUntilSpace+=format.charAt(charPos);
					charPos++;
				}
				
				if(getSize(charsUntilSpace)+currentSize >= maxWidth)
				{
					currentSize-=getSize(builtString);
					builtString+="\n"+lastcolor;
				}
			}
			
			if(shouldSkip)
			{
				shouldSkip = false;
				pos++;
				continue;
			}
			
			if(x == COLOR_CHAR && format.length() >= pos+2 && (ChatFormatter.isColorCode(String.valueOf(format.charAt(pos+1))) || format.charAt(pos+1) == COLOR_CHAR))
			{
				if(ChatFormatter.isColorCode(String.valueOf(format.charAt(pos+1))));
				lastcolor = getColorCode(String.valueOf(format.charAt(pos+1)));
				shouldSkip = true;
				pos++;
				continue;
			}
			
			int index = allowedChars.indexOf(x);
	        	if (index == -1) {
	                // Invalid character .. skip it.
	                continue;
	            } else {
	                // Sadly needed as the allowedChars string misses the first
	                index += 32;
	            }
				
			currentSize+=characterWidths[index]; //Add size
			pos++;
		}
		
		return builtString.split("\n");
	}
	
	private static int getCharWidth(char x)
	{
		int index = allowedChars.indexOf(x);
    	if (index == -1) {
    		return 0;
        } else {
            // Sadly needed as the allowedChars string misses the first
            index += 32;
        }
		
    	return characterWidths[index];
	}
	
	private static int getSize(String str)
	{
		boolean shouldSkip = false;
		int currentSize = 0;
		int pos = 0;
		for(char x : str.toCharArray())
		{
			if(shouldSkip)
			{
				shouldSkip = false;
				pos++;
				continue;
			}
			
			if(x == COLOR_CHAR && str.length() >= pos+2 && (ChatFormatter.isColorCode(String.valueOf(str.charAt(pos+1))) || str.charAt(pos+1) == COLOR_CHAR))
			{
				lastcolor = getColorCode(String.valueOf(str.charAt(pos+1)));
				shouldSkip = true;
				pos++;
				continue;
			}
			
			int index = allowedChars.indexOf(x);
	        	if (index == -1) {
	                // Invalid character .. skip it.
	                continue;
	            } else {
	                // Sadly needed as the allowedChars string misses the first
	                index += 32;
	            }
				
	        if(x != ' ')
			 currentSize+=characterWidths[index]; //Add size
			pos++;
		}
		return currentSize;
	}
	
	private static ChatColor getColorCode(String colorCode)
	{
		if(colorCode.equals("a"))
		{
			return ChatColor.GREEN;
		}
		else if(colorCode.equals("b")) 
		{
			return ChatColor.AQUA;
		}
		else if (colorCode.equals("c"))
		{
			return ChatColor.RED;
		}
		else if (colorCode.equals("d"))
		{
			return ChatColor.LIGHT_PURPLE;
		}
		else if (colorCode.equals("e"))
		{
			return ChatColor.YELLOW;
		}
		else if (colorCode.equals("f"))
		{
			return ChatColor.WHITE;
		}
		else if (colorCode.equals("0"))
		{
			return ChatColor.BLACK;
		}
		else if (colorCode.equals("1"))
		{
			return ChatColor.DARK_BLUE;
		}
		else if (colorCode.equals("2"))
		{
			return ChatColor.DARK_GREEN;
		}
		else if (colorCode.equals("3"))
		{
			return ChatColor.DARK_AQUA;
		}
		else if (colorCode.equals("4"))
		{
			return ChatColor.DARK_RED;
		}
		else if (colorCode.equals("5"))
		{
			return ChatColor.DARK_PURPLE;
		}
		else if (colorCode.equals("6"))
		{
			return ChatColor.GOLD;
		}
		else if (colorCode.equals("7"))
		{
			return ChatColor.GRAY;
		}
		else if (colorCode.equals("8"))
		{
			return ChatColor.DARK_GRAY;
		}
		else
		{
			return ChatColor.BLUE;
		}
	}
}
