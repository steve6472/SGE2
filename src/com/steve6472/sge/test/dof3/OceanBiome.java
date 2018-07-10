/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 5. 10. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.test.dof3;


public class OceanBiome extends Biome
{

	public OceanBiome()
	{
	}

	@Override
	public int getTopBlockId(double noise)
	{
//		if (noise < 60 && noise > 50)
//			return Tile.stone.id;
		
//		if (noise > 70)
//			return Tile.grass.id;
//		
//		if (noise > 64)
//			return Tile.sand.id;
//		
//		if (noise <= 64)
//			return Tile.water.id;
//		
//		return Tile.dirt.id;
		return 1;
	}

	@Override
	public NoiseParam getNoiseParam()
	{
		return new NoiseParam(7, 10, 55, -8, 0.5);
//		return new NoiseParam(8, 10, 55, 0, 1.5);
	}
	
	@Override
	public boolean getBiome(double value)
	{
		return value > 120;
	}
	
	@Override
	public int getBiomeColor()
	{
		return 0xff0059be;
	}

}
