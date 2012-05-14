package com.gmail.nossr50.vChat.spout.buttons;

import org.getspout.spoutapi.gui.GenericButton;

import com.gmail.nossr50.vChat.spout.textfields.TextType;

public class EasyDefault extends GenericButton
{
	TextType type;
	public EasyDefault(TextType x)
	{
		type = x;
		this.setText("D");
		this.setTooltip("Default");
		this.setWidth(14);
		this.setHeight(11);
		this.setDirty(true);
	}
	public TextType getTextType()
	{
		return type;
	}
}