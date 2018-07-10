package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.RenderHelper;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.KeyList;
import com.steve6472.sge.main.MainApplication;
import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public class NumberSelector2 extends Component implements KeyList
{
	private static final long serialVersionUID = 6097537167658644896L;
	double leftValue, rightValue, rightMax = 16, leftMax = 16, rightMin = -16, leftMin = -16;
	private List<ChangeEvent> changeEvents = new ArrayList<ChangeEvent>();
	private boolean enabled = true;
	
	private boolean addRightHovered, removeRightHovered, round, addLeftHovered, removeLeftHovered;
	private Vec2 addCenter = new Vec2(), removeCenter = new Vec2();
	
	@Override
	public void init(MainApplication game)
	{
		getMouseHandler().addMouseButtonCallback((x, y, button, action, mods) ->
		{
			if (enabled && action == PRESS)
			{
				double modifyValue = 1;
				if (button == 0)
				{
					if (isKeyPressed(L_SHIFT)) 		modifyValue = 10;
					if (isKeyPressed(L_CONTROL)) 	modifyValue = 100;
					if (isKeyPressed(L_ALT)) 		modifyValue = 1000;
				}
				if (button == 1)
				{
					modifyValue = 0.1;
					if (isKeyPressed(L_SHIFT)) 		modifyValue = 0.01;
					if (isKeyPressed(L_CONTROL)) 	modifyValue = 0.001;
					if (isKeyPressed(L_ALT)) 		modifyValue = 0.0001;
				}
				
				if (addLeftHovered)
					addLeftValue(modifyValue);
				if (removeLeftHovered)
					removeLeftValue(modifyValue);
				if (addRightHovered)
					addRightValue(modifyValue);
				if (removeRightHovered)
					removeRightValue(modifyValue);
				
				if (addLeftHovered || removeLeftHovered || addRightHovered || removeRightHovered)
				{
					changeEvents.forEach((c) -> c.change());
				}
			}
		});
	}
	
	@Override
	public void tick()
	{
		addRightHovered = isCursorInComponent(x + (int) ((double) width / 4d * 3d), y, (int) ((double) width / 4d), height / 2 - 1);
		removeRightHovered = isCursorInComponent(x + (int) ((double) width / 4d * 3d), y + height / 2, (int) ((double) width / 4d), height / 2 - 1);

		addLeftHovered = isCursorInComponent(x, y, (int) ((double) width / 4d), height / 2 - 1);
		removeLeftHovered = isCursorInComponent(x, y + height / 2, (int) ((double) width / 4d), height / 2 - 1);
	}

	@Override
	public void render(Screen screen)
	{
		RenderHelper.renderSingleBorder(x + width / 4, y, width / 2, height, 0xff7f7f7f, 0xff000000);

		RenderHelper.renderButton(x, y, width / 4, height / 2 + 1, enabled, addLeftHovered);
		RenderHelper.renderButton(x, y + height / 2 - 1, width / 4, height / 2 + 1, enabled, removeLeftHovered);
		Font.render("\u0000", removeCenter.getIX(), removeCenter.getIY() - 9);
		Font.render("\u0001", removeCenter.getIX(), removeCenter.getIY() + 9);

		RenderHelper.renderButton(x + (int) ((double) width / 4d * 3d), y, width / 4, height / 2 + 1, enabled, addRightHovered);
		RenderHelper.renderButton(x + (int) ((double) width / 4d * 3d), y + height / 2 - 1, width / 4, height / 2 + 1, enabled, removeRightHovered);
		Font.render("\u0000", addCenter.getIX(), addCenter.getIY() - 9);
		Font.render("\u0001", addCenter.getIX(), addCenter.getIY() + 9);
		
		Screen.fillRect(x + width / 4, y + 19, width / 2, 2, 0xff7f7f7f);
		
		Font.render(String.format(Locale.US, "%.4f", leftValue), getX() + getWidth() / 2 - String.format(Locale.US, "%.4f", leftValue).length() * 4, getY() + getHeight () / 2 - 4 - 8);
		Font.render(String.format(Locale.US, "%.4f", rightValue), getX() + getWidth() / 2 - String.format(Locale.US, "%.4f", rightValue).length() * 4, getY() + getHeight () / 2 - 4 + 9);
	}

	/*
	 * Operators
	 */

	public void addLeftValue(double i)
	{
		leftValue += i;
		if (leftValue > rightMax)
			if (round)
				leftValue = leftMin;
			else
				leftValue = leftMax;
	}

	public void removeLeftValue(double i)
	{
		leftValue -= i;
		if (leftValue < leftMin)
			if (round)
				leftValue = leftMax;
			else
				leftValue = leftMin;
	}

	public void addRightValue(double i)
	{
		rightValue += i;
		if (rightValue > rightMax)
			if (round)
				rightValue = rightMin;
			else
				rightValue = rightMax;
	}

	public void removeRightValue(double i)
	{
		rightValue -= i;
		if (rightValue < rightMin)
			if (round)
				rightValue = rightMax;
			else
				rightValue = rightMin;
	}
	
	public void addChangeEvent(ChangeEvent ce)
	{
		changeEvents.add(ce);
	}

	public void addLeftValue()
	{
		addLeftValue(1);
	}

	public void removeLeftValue()
	{
		removeLeftValue(1);
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

	public void setLeftValue(double value)
	{
		this.leftValue = value;
	}

	public void setRightValue(double value)
	{
		this.rightValue = value;
	}
	
	public void setLeftMax(double leftMax)
	{
		this.leftMax = leftMax;
	}
	
	public void setRightMax(double rightMax)
	{
		this.rightMax = rightMax;
	}
	
	public void setLeftMin(double leftMin)
	{
		this.leftMin = leftMin;
	}
	
	public void setRightMin(double rightMin)
	{
		this.rightMin = rightMin;
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
	
	public double getLeftMax()
	{
		return leftMax;
	}
	
	public double getLeftMin()
	{
		return leftMin;
	}
	
	public double getLeftValue()
	{
		return leftValue;
	}
	
	public double getRightMax()
	{
		return rightMax;
	}
	
	public double getRightMin()
	{
		return rightMin;
	}
	
	public double getRightValue()
	{
		return rightValue;
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
