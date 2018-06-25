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
			
			text += Character.toChars(codePoint)[0];
//			location = Font.stringCenter(new AABB(0, 0, width, height), text, fontSize);
			updateLocation();
		});
	}
	
	Vec2 location;

	@Override
	public void render(Screen screen)
	{
		RenderHelper.renderSingleBorder(getX(), getY(), getWidth(), getHeight(), 0xff7f7f7f, 0xff000000);
		
		getFont().render(text, getX() + location.getIntX(), getY() + location.getIntY() + 1, fontSize);
		
		if (showCarret && isFocused)
			getFont().render("|", getX() + location.getIntX() + getTextWidth() - fontSize, getY() + location.getIntY() + 1, fontSize);
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
