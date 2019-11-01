package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.SpriteRender;
import com.steve6472.sge.gfx.font.CustomChar;
import com.steve6472.sge.gfx.font.Font;

public class NamedCheckBox extends ToggleButton
{
	private static final long serialVersionUID = 7880397321121670923L;
	private Object selectedChar;
	private String text;
	private boolean shadow;
	private int paddingRight;
	private int paddingUp;
	private int boxWidth;
	private int boxHeight;

	public NamedCheckBox()
	{
		selectedChar = CustomChar.CROSS;
	}

	@Override
	public void render()
	{
		if (text != null)
		{
			int tw = Font.getTextWidth(text, 1);
			SpriteRender.renderSingleBorder(x, y, width, height, 0.6f, 0.6f, 0.6f, 1, 0, 0.2f, 0.3f, 0.2f);
			Font.render(text, x + (width - 22) / 2 - tw / 2, y + height / 2 - getFontSize() * 4);
		}

		int tx = x, ty = y, tw = width, th = height;

		x = x + width - boxWidth - paddingRight;
		y = y + paddingUp;
		width = boxWidth;
		height = boxHeight;

		if (enabled && !hovered)
			SpriteRender.renderSingleBorder(x, y, width, height, getScheme().enabledOutsideBorder, getScheme().enabledFill);

		if (!enabled)
			SpriteRender.renderSingleBorder(x, y, width, height, getScheme().disabledOutsideBorder, getScheme().disabledFill);

		if (enabled && hovered)
			SpriteRender.renderSingleBorder(x, y, width, height, getScheme().hoveredOutsideBorder, getScheme().hoveredFill);

		renderText();

		x = tx; y = ty; width = tw; height = th;
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
			Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, getFontSize(), shadow(), getScheme().enabled, selectedChar);

		if (!enabled)
			Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, getFontSize(), shadow(), getScheme().disabled, selectedChar);

		if (enabled && hovered)
			Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, getFontSize(), shadow(), getScheme().hovered, selectedChar);
	}

	private String shadow()
	{
		if (isShadow())
			return "[s1]";
		else
			return "[s0]";
	}

	public void setSelectedChar(char c)
	{
		this.selectedChar = c;
	}

	public void setSelectedChar(CustomChar c)
	{
		this.selectedChar = c;
	}

	@Override
	public String getText()
	{
		return text;
	}

	@Override
	public void setText(String text)
	{
		this.text = text;
	}

	public boolean isShadow()
	{
		return shadow;
	}

	public void setShadow(boolean shadow)
	{
		this.shadow = shadow;
	}

	public void setBoxPadding(int paddingRight, int paddingUp)
	{
		this.paddingRight = paddingRight;
		this.paddingUp = paddingUp;
	}

	public void setBoxSize(int boxWidth, int boxHeight)
	{
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
	}
}
