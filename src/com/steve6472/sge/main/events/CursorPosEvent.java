/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 9. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.events;

public class CursorPosEvent extends AbstractEvent
{
	private final double x, y;

	public CursorPosEvent (double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}

}
