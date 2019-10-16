package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.font.CustomChar;
import com.steve6472.sge.gfx.font.Font;

public class CheckBox extends ToggleButton
{
	private static final long serialVersionUID = 7880397321121670923L;
	private Object selectedChar;

	public CheckBox()
	{
		selectedChar = CustomChar.CROSS;
	}

	@Override
	public void renderText()
	{
		if (!isToggled())
			return;

		int fontWidth;
			fontWidth = Font.getTextWidth("" + selectedChar, getFontSize()) / 2;
//		else
		int fontHeight = ((8 * getFontSize())) / 2;

		if (enabled && !hovered)
			Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, getFontSize(), getColor(enabledColor), selectedChar);

		if (!enabled)
			Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, getFontSize(), getColor(disabledColor), selectedChar);

		if (enabled && hovered)
			Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, getFontSize(), getColor(hoveredColor), selectedChar);
	}

	public void setSelectedChar(char c)
	{
		this.selectedChar = c;
	}

	public void setSelectedChar(CustomChar c)
	{
		this.selectedChar = c;
	}
}
