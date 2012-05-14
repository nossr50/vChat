package com.gmail.nossr50.vChat.spout.buttons;

import org.getspout.spoutapi.gui.GenericButton;

import com.gmail.nossr50.vChat.spout.textfields.TextType;

public class EasyColor extends GenericButton
{
	TextType type;
	public EasyColor(TextType x)
	{
		type = x;
		this.setText("C");
		this.setTooltip("Color");
		this.setWidth(14);
		this.setHeight(11);
		this.setDirty(true);
	}
	public TextType getTextType()
	{
		return type;
	}
}