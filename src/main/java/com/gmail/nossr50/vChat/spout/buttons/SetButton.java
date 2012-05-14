package com.gmail.nossr50.vChat.spout.buttons;

import org.getspout.spoutapi.gui.GenericButton;

public class SetButton extends GenericButton 
{
	public SetButton()
	{
		this.setHeight(20).setWidth(40);
		this.setText("SAVE");
		this.setDirty(true);
	}
}