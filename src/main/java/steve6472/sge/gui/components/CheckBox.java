package steve6472.sge.gui.components;

import steve6472.sge.gfx.font.CustomChar;
import steve6472.sge.gfx.font.Font;

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
			Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, getFontSize(), getScheme().enabled, selectedChar);

		if (!enabled)
			Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, getFontSize(), getScheme().disabled, selectedChar);

		if (enabled && hovered)
			Font.renderCustom(x + width / 2 - fontWidth, y + height / 2 - fontHeight, getFontSize(), getScheme().hovered, selectedChar);
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
