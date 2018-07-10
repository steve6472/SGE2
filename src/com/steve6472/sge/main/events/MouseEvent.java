/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 9. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.events;

public class MouseEvent extends AbstractEvent
{
	private final int x, y, button, action, mods;

	public MouseEvent(int x, int y, int button, int action, int mods)
	{
		this.x = x;
		this.y = y;
		this.button = button;
		this.action = action;
		this.mods = mods;
	}

	public int getAction()
	{
		return action;
	}

	public int getButton()
	{
		return button;
	}

	public int getMods()
	{
		return mods;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

}
