/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 15. 4. 2018
* Project: MultiplayerTest
*
***********************/

package com.steve6472.sge.gfx.animations;

public abstract class Animation implements Cloneable
{
	protected long time = 0;
	
	public abstract void render();
	
	public abstract boolean hasEnded();
	
	public void tick()
	{
		time++;
	}
	
	public abstract int getId();
	
	@Override
	public Animation clone()
	{
		try
		{
			return (Animation) super.clone();
		} catch (CloneNotSupportedException ex)
		{
			ex.printStackTrace();
			System.exit(-2);
			return null;
		}
	}
}
