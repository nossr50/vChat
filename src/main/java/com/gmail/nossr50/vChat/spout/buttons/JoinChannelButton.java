package com.gmail.nossr50.vChat.spout.buttons;

import org.getspout.spoutapi.gui.GenericButton;

public class JoinChannelButton extends GenericButton{
	public JoinChannelButton()
	{
		this.setWidth(20).setHeight(20).setTooltip("Join this channel");
		this.setText("JOIN");
		this.setDirty(true);
	}
}
