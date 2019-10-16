/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 9. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.events;

public class WindowSizeEvent extends AbstractEvent
{
	private final int width, height;

	public WindowSizeEvent(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

}
