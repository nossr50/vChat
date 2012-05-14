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
    private static final String allowedChars = net.minecraft.server.SharedConstants.allowedCharacters;
    private static final int CHAT_WINDOW_WIDTH = 320;
    
    public static String[] wordWrapText(String input) {
        String builtString = "", currentWord = "", currentLine = "";
        int arrayPos = 0, spaceSize = getCharWidth(' ');
        ChatColor lastColor = ChatColor.WHITE;
        boolean skipNextTurn = false;
        
        for(char x : input.toCharArray()) {
            //If the character is a color code we need to account for it since it doesn't contribute to the actual length of the screen
            if(x == COLOR_CHAR) {
                lastColor = getColorCode(String.valueOf(input.charAt(arrayPos+1)).toLowerCase());
                currentWord+=lastColor; //Not sure about this, but it *should* work.
                skipNextTurn = true; //We want to skip the next character since it is a color code
                arrayPos++;
                continue;
            } else if (skipNextTurn) {
                skipNextTurn = false;
                arrayPos++;
                continue;
            }
            
            if(arrayPos+1 == input.toCharArray().length) {
                if(x != ' ')
                    currentWord+=x;
                
                //We've reached the end of the array and need to make sure to insert the currentWord/currentLine
                    
                    if(builtString.length() >= 1) {
                        if(getSize(currentLine)+spaceSize+getSize(currentWord) < CHAT_WINDOW_WIDTH) {
                            builtString+="\n "+lastColor+currentLine+" "+currentWord;
                        } else {
                            builtString+="\n "+lastColor+currentLine+"\n "+currentWord;
                        }
                    } else {
                        if(getSize(currentLine)+spaceSize+getSize(currentWord) < CHAT_WINDOW_WIDTH) {
                            builtString+=currentLine+" "+lastColor+currentWord;
                        } else {
                            builtString+=currentLine+"\n "+lastColor+currentWord;
                        }
                    }
                    
                    break;
            }
            
            if(x != ' ') {
                //If the character is not a space we want to continue to add to the currentWord
                currentWord+=x;
                
                if(getSize(currentLine)+getSize(currentWord)+spaceSize > CHAT_WINDOW_WIDTH) {
                    //If the size of the current line exceeds the width of the screen we want to insert a linebreak
                    if(builtString.length() >= 1) {
                        builtString+="\n"+lastColor+currentLine;
                    } else {
                        builtString+=currentLine;
                    }
                    
                    currentLine = ""; //Reset the currentLine since it is now a new line
                }
            } else {
                //If the character is a space we want to insert the currentWord into the currentLine
                if(getSize(currentLine)+spaceSize+getSize(currentWord) < CHAT_WINDOW_WIDTH) {
                    currentLine+=" "+currentWord;
                    currentWord = "";
                } else {
                    if(builtString.length() >= 1) {
                        builtString+="\n"+lastColor+currentLine;
                    } else {
                        builtString+=currentLine;
                    }
                    currentLine = " "+currentWord;
                    currentWord = "";
                }
                
                if(getSize(currentLine) == CHAT_WINDOW_WIDTH) {
                    //If the current line is now equal to the maximum width of the screen we want to insert a new line
                    if(builtString.length() >= 1) {
                        builtString+="\n"+lastColor+currentLine;
                    } else {
                        builtString+=currentLine;
                    }
                    
                    currentLine = "";
                }
            }
            
            arrayPos++;
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
    
    public static int getStringSize(String str)
    {
        int size = 0;
        
        for(char x : str.toCharArray())
        {
            size+=getCharWidth(x);
        }
        
        return size;
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
                //Do I need this? lastcolor = getColorCode(String.valueOf(str.charAt(pos+1)));
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
                
            //Why did I have this? if(x != ' ')
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