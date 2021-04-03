package steve6472.sge.gfx.font;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class ColoredTextBuilder
{
	private static final ColoredText NEW_LINE = new ColoredText().setText("\n");

	private List<ColoredText> text;
	private int size;
	private ColoredText lastColor;

	private boolean shade;
	private float offsetX, offsetY;

	private ColoredTextBuilder()
	{
		this.text = new ArrayList<>();
		lastColor = new ColoredText();
		shade = true;
	}

	public static ColoredTextBuilder create()
	{
		return new ColoredTextBuilder();
	}

	public ColoredTextBuilder add(ColoredText text)
	{
		addInternal(text);
		end(text.isString() ? text.text.length() : 1, text.shade);
		return this;
	}

	public ColoredTextBuilder addText(String text)
	{
		addInternal(new ColoredText().setText(text).setColor(lastColor).setShade(shade).setOffset(offsetX, offsetY));
		end(text.length(), shade);
		return this;
	}

	public ColoredTextBuilder addCustomChar(ICustomChar customChar)
	{
		addInternal(new ColoredText().setCustomChar(customChar).setColor(lastColor).setShade(shade).setOffset(offsetX, offsetY));
		end(1, shade);
		return this;
	}

	public ColoredTextBuilder setColor(float r, float g, float b)
	{
		lastColor.setColor(r, g, b);
		return this;
	}

	public ColoredTextBuilder addOffset(float offsetX, float offsetY)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		return this;
	}

	public ColoredTextBuilder setShade(boolean shade)
	{
		this.shade = shade;
		return this;
	}

	public ColoredTextBuilder newLine()
	{
		addInternal(NEW_LINE);
		return this;
	}

	/**
	 * Sets shade only the text
	 */
	public ColoredTextBuilder addText(String text, boolean shade)
	{
		addInternal(new ColoredText().setText(text).setColor(lastColor).setShade(shade).setOffset(offsetX, offsetY));
		end(text.length(), shade);
		return this;
	}

	/**
	 * Sets shade only the text
	 */
	public ColoredTextBuilder addCustomCharacter(ICustomChar customChar, boolean shade)
	{
		addInternal(new ColoredText().setCustomChar(customChar).setColor(lastColor).setShade(shade).setOffset(offsetX, offsetY));
		end(1, shade);
		return this;
	}

	/**
	 * Colors only the character, then returns to previous color
	 */
	public ColoredTextBuilder addColoredCustomChar(ICustomChar customChar, float r, float g, float b)
	{
		addInternal(new ColoredText().setCustomChar(customChar).setColor(r, g, b).setShade(shade).setOffset(offsetX, offsetY));
		end(1, shade);
		return this;
	}

	/**
	 * Colors only the text, then returns to previous color
	 */
	public ColoredTextBuilder addColoredText(String text, float r, float g, float b)
	{
		addInternal(new ColoredText().setText(text).setColor(r, g, b).setShade(shade).setOffset(offsetX, offsetY));
		end(text.length(), shade);
		return this;
	}

	/*
	 * Util
	 */

	public ColoredText get(int index)
	{
		return text.get(index);
	}

	List<ColoredText> getText()
	{
		return text;
	}

	int getSize()
	{
		return size;
	}

	private void end(int size, boolean shade)
	{
		this.offsetX = 0;
		this.offsetY = 0;
		this.size += size * (shade ? 2 : 1);
	}

	private void addInternal(ColoredText text)
	{
		if (text == null)
			return;

		this.text.add(text);
	}

	@Override
	public String toString()
	{
		return "ColoredTextBuilder{" + "text=" + text + ", size=" + size + ", shade=" + shade + '}';
	}
}