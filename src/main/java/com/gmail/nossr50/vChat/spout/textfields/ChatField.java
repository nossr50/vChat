package com.gmail.nossr50.vChat.spout.textfields;

import org.getspout.spoutapi.gui.GenericTextField;

public class ChatField extends GenericTextField
{
	TextType type = null;
	
	public ChatField(TextType typex)
	{
		type = typex;
		this.setWidth(110);
		this.setHeight(12);
		
		this.setDirty(true);
	}
	
	public TextType getTextType()
	{
		return type;
	}
}
