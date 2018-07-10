/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 4. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gui.components;

import static org.lwjgl.glfw.GLFW.*;

import com.steve6472.sge.gfx.Char;
import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.RenderHelper;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public class TextField extends Component
{
	private static final long serialVersionUID = 1235988307289728893L;
	
	String text = "";
	int fontSize = 1;
	int carretPosition = 0;
	private double carretTick = 0;
	private boolean showCarret = false;
	boolean isFocused = false;
	public boolean isLeft = false;
	public boolean onlyNumbers = false;
	
	char[] numbers = "0123456789.-".toCharArray();
	
	public TextField()
	{
	}

	@Override
	public void init(MainApplication game)
	{
		getKeyHandler().addKeyCallback((key, scancode, action, mods) ->
		{
			if (!isFocused)
				return;
			
			if (key == GLFW_KEY_BACKSPACE && (action == GLFW_PRESS || action == GLFW_REPEAT) && text.length() >= 1)
			{
				text = text.substring(0, text.length() - 1);
//				location = Font.stringCenter(new AABB(0, 0, width, height), text, fontSize);
				updateLocation();
			}
			if (key == GLFW_KEY_LEFT && (action == GLFW_PRESS || action == GLFW_REPEAT))
				moveCarretLeft();
			if (key == GLFW_KEY_RIGHT && (action == GLFW_PRESS || action == GLFW_REPEAT))
				moveCarretRight();
		});
		
		getKeyHandler().addCharCallback(codePoint -> 
		{
			if (!isFocused)
				return;
			
			if (onlyNumbers)
			{
				char c = Character.toChars(codePoint)[0];
				
				boolean hasDot = false;
				boolean canAdd = false;
				boolean stop = false;
				
				if (c == '-')
				{
					if (text.isEmpty())
					{
						text += "-";
					}
					stop = true;
				}
				
				if (!stop) for (char t : text.toCharArray())
				{
					if (t == '.')
					{
						hasDot = true;
						break;
					}
				}

				if (!stop) for (char n : numbers)
				{
					if (c == n)
					{
						canAdd = true;
						break;
					}
				}

				if (!stop) if (canAdd)
				{
					if (c == '.')
					{
						if (!hasDot)
						{
							text += c;
						}
					} else
					{
						text += c;
					}
				}
			} else
			{
				text += Character.toChars(codePoint)[0];
			}

			updateLocation();
		});
		updateLocation();
	}
	
	public float toFloat()
	{
		String t = text.isEmpty() ? "0" : text;
		if (t.equals("-"))
			t = "-0";
		return Float.parseFloat(t);
	}
	
	Vec2 location;

	@Override
	public void render(Screen screen)
	{
		RenderHelper.renderSingleBorder(getX(), getY(), getWidth(), getHeight(), 0xff020202, 0xff414041);
		
		Font.render(text, getX() + location.getIX(), getY() + location.getIY() + 1, fontSize);
		
		if (showCarret && isFocused)
			Screen.fillRect(Font.getTextWidth(text, 1) + x + 5, y + 13, 8, 2, 0xffa9a8aa);
//			Font.render("_", getX() + location.getIntX() + getTextWidth() - fontSize, getY() + location.getIntY() + 1, fontSize);
	}
	
	public int getTextWidth()
	{
		int lx = 0;
		
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			Char ch = Font.characters.get(c);
			if (ch == null)
				continue;
			lx += ch.getWidth() * fontSize;
		}
		return lx;
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
//		location = Font.stringCenter(new AABB(0, 0, width, height), text, fontSize);
		updateLocation();
	}

	@Override
	public void tick()
	{
		carretTick += 1;
		if (carretTick >= 60)
		{
			carretTick = 0;
			showCarret = !showCarret;
		}
		
		if (isCursorInComponent() && getMouseHandler().isMouseHolded())
			isFocused = true;
		
		if (!isCursorInComponent() && getMouseHandler().isMouseHolded())
			isFocused = false;
	}
	
	public int getFontSize()
	{
		return fontSize;
	}
	
	public void setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
//		if (!isLeft)
//			location = Font.stringCenter(new AABB(0, 0, width, height), text, fontSize);
//		else
//			location = new Vec2(text.length() * 8 + 4, getY() + (height / 2) - (4 * fontSize));
		updateLocation();
	}
	
	public void updateLocation()
	{
		if (!isLeft)
			location = Font.stringCenter(new AABB(0, 0, width, height), text, fontSize);
		else
			location = Font.stringCenter(new AABB(0, 0, width, height), text, fontSize).setX(getHeight() / 5);
	}
	
	public void moveCarretLeft() { carretPosition = Math.max(0, carretPosition - 1); carretTick = 0; showCarret = true; };
	
	public void moveCarretRight() { carretPosition = Math.min(text.length(), carretPosition + 1); carretTick = 0; showCarret = true; };

}
