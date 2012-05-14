package com.gmail.nossr50.vChat.util;

import org.bukkit.ChatColor;

public class ChatFormatter {
	static String specialChar = "^";
	
	public static String getSpecialChar()
	{
		return specialChar;
	}
	
	public static String parseColors(String arg)
	{
		int pos = 0;
		boolean shouldSkip = false;
		String newString = "";
		for(Character x : arg.toCharArray())
		{
			String target = String.valueOf(x);
			
			if(shouldSkip)
			{
				shouldSkip = false;
				pos++;
				continue;
			}
			
			if(arg.length() >= pos+2 && target.equals(specialChar) && isColorCode(String.valueOf(arg.charAt(pos+1)))) //Find the special character
			{
				String colorCode = String.valueOf(arg.charAt(pos+1)).toLowerCase();
				if(colorCode.equals("a"))
				{
					newString+=ChatColor.GREEN;
				}
				else if(colorCode.equals("b")) 
				{
					newString+=ChatColor.AQUA;
				}
				else if (colorCode.equals("c"))
				{
					newString+=ChatColor.RED;
				}
				else if (colorCode.equals("d"))
				{
					newString+=ChatColor.LIGHT_PURPLE;
				}
				else if (colorCode.equals("e"))
				{
					newString+=ChatColor.YELLOW;
				}
				else if (colorCode.equals("f"))
				{
					newString+=ChatColor.WHITE;
				}
				else if (colorCode.equals("0"))
				{
					newString+=ChatColor.BLACK;
				}
				else if (colorCode.equals("1"))
				{
					newString+=ChatColor.DARK_BLUE;
				}
				else if (colorCode.equals("2"))
				{
					newString+=ChatColor.DARK_GREEN;
				}
				else if (colorCode.equals("3"))
				{
					newString+=ChatColor.DARK_AQUA;
				}
				else if (colorCode.equals("4"))
				{
					newString+=ChatColor.DARK_RED;
				}
				else if (colorCode.equals("5"))
				{
					newString+=ChatColor.DARK_PURPLE;
				}
				else if (colorCode.equals("6"))
				{
					newString+=ChatColor.GOLD;
				}
				else if (colorCode.equals("7"))
				{
					newString+=ChatColor.GRAY;
				}
				else if (colorCode.equals("8"))
				{
					newString+=ChatColor.DARK_GRAY;
				}
				else if (colorCode.equals("9"))
				{
					newString+=ChatColor.BLUE;
				}
				shouldSkip = true;
			} else {
				newString+=x;
			}
			pos++;
		}
		//Player: Hi ^aApple
		//newstring: Hi &a
		
		return newString;
	}
	public static boolean isColorCode(String a)
	{
		return a.equals("a") || a.equals("b") || a.equals("c") || a.equals("d") || a.equals("e") || a.equals("f")
		|| a.equals("0") || a.equals("1") || a.equals("2") || a.equals("3") || a.equals("4") || a.equals("5") || a.equals("6")
		|| a.equals("7") || a.equals("8") || a.equals("9");
	}
}
