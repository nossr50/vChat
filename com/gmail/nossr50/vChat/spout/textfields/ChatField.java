package com.gmail.nossr50.vChat.spout.textfields;

import org.getspout.spoutapi.gui.GenericTextField;

import com.gmail.nossr50.vChat.spout.buttons.ResetButton;

public class ChatField extends GenericTextField
{
	ResetButton resetButton = null;
	TextType type = null;
	
	public ChatField(TextType typex)
	{
		type = typex;
		this.setWidth(90);
		this.setHeight(12);
		
		resetButton = new ResetButton();
		this.setDirty(true);
	}
	
	public ResetButton getResetButton()
	{
		return resetButton;
	}
	
	public TextType getTextType()
	{
		return type;
	}
}
