package com.steve6472.sge.gui.components;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.RenderHelper;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public class NumberSelector extends Component
{
	private static final long serialVersionUID = 6097537167658644896L;
	int value = 0, max = 16, min = -16;
	private List<ChangeEvent> changeEvents = new ArrayList<ChangeEvent>();
	private boolean enabled = true;
	
	private boolean addHovered = false, removeHovered = false, round = false;
	private Vec2 addCenter = new Vec2(), removeCenter = new Vec2();
	
	@Override
	public void init(MainApplication game)
	{
//		setSize(150, 40);
		
		getMouseHandler().addMouseButtonCallback((x, y, button, action, mods) ->
		{
			if (enabled && action == GLFW_PRESS)
			{
				if (removeHovered)
				{
					if (getKeyHandler().isKeyPressed(GLFW_KEY_LEFT_ALT))
					{
						if (getMouseHandler().getButton() == 3)
							removeValue(1000);
						else
							removeValue(100);
					} else if (getKeyHandler().isKeyPressed(GLFW_KEY_LEFT_SHIFT))
					{
						removeValue(10);
					} else if (getKeyHandler().isKeyPressed(GLFW_KEY_LEFT_CONTROL))
					{
						removeValue(5);
					} else
					{
						removeValue();
					}
					for (ChangeEvent ce : changeEvents)
					{
						ce.change();
					}
				}
				
				if (addHovered)
				{
					if (getKeyHandler().isKeyPressed(GLFW_KEY_LEFT_ALT))
					{
						if (getMouseHandler().getButton() == 3)
							addValue(1000);
						else
							addValue(100);
					} else if (getKeyHandler().isKeyPressed(GLFW_KEY_LEFT_SHIFT))
					{
						addValue(10);
					} else if (getKeyHandler().isKeyPressed(GLFW_KEY_LEFT_CONTROL))
					{
						addValue(5);
					} else
					{
						addValue();
					}
					for (ChangeEvent ce : changeEvents)
					{
						ce.change();
					}
				}
			}
		});
	}

	@Override
	public void tick()
	{
		removeHovered = isCursorInComponent(getX(), getY(), getWidth() / 4, getHeight());

		addHovered = isCursorInComponent(getX() + getWidth() / 4 * 3, getY(), getWidth() / 4, getHeight());
	}

	@Override
	public void render(Screen screen)
	{
		Screen.fillRect(getX(), getY(), getWidth(), getHeight(), 0xffff00ff);
		
		RenderHelper.renderSingleBorder(getX() + getWidth() / 4, getY(), getWidth() / 2, getHeight(), 0xff7f7f7f, 0xff000000);
		
		RenderHelper.renderButton(getX(), getY(), getWidth() / 4, getHeight(), enabled, removeHovered);
		Font.render("-", removeCenter.getIX(), removeCenter.getIY());
		
		RenderHelper.renderButton(getX() + getWidth() / 4 * 3, getY(), getWidth() / 4, getHeight(), enabled, addHovered);
		Font.render("+", addCenter.getIX(), addCenter.getIY());
		
		Font.render("" + value, getX() + getWidth() / 2 - ("" + value).length() * 4, getY() + getHeight () / 2 - 4);
	}

	/*
	 * Operators
	 */

	public void addValue(int i)
	{
		value += i;
		if (value > max)
			if (round)
				value = min;
			else
				value = max;
	}
	
	public void addChangeEvent(ChangeEvent ce)
	{
		changeEvents.add(ce);
	}

	public void removeValue(int i)
	{
		value -= i;
		if (value < min)
			if (round)
				value = max;
			else
				value = min;
	}

	public void addValue()
	{
		addValue(1);
	}

	public void removeValue()
	{
		removeValue(1);
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		removeCenter = Font.stringCenter(new AABB(new Vec2(getX(), getY()), getWidth() / 4, getHeight()), " ", 1);
		addCenter = Font.stringCenter(new AABB(new Vec2(getX() + getWidth() / 4 * 3, getY()), getWidth() / 4, getHeight()), " ", 1);
	}
	
	/*
	 * Setters
	 */

	public void setValue(int value)
	{
		this.value = value;
	}
	
	public void setMaxValue(int max)
	{
		this.max = max;
	}
	
	public void setMinValue(int min)
	{
		this.min = min;
	}

	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public void setRound(boolean round)
	{
		this.round = round;
	}
	
	/*
	 * Getters
	 */

	public int getValue()
	{
		return value;
	}
	
	public int getMaxValue()
	{
		return max;
	}
	
	public int getMinValue()
	{
		return min;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public boolean isRound()
	{
		return round;
	}
}
