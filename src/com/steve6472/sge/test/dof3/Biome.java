/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 5. 10. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.test.dof3;

public abstract class Biome
{
	public Biome()
	{
		
	}
	
	protected boolean isInRange(double max, double min, double i)
	{
		return i >= max && i <= min;
	}
	
	public abstract int getTopBlockId(double noise);
	
	public abstract boolean getBiome(double value);
	
	public abstract NoiseParam getNoiseParam();
	
	public int getBiomeColor()
	{
		return 0xffff00ff;
	}

}
