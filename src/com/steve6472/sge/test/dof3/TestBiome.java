package com.steve6472.sge.test.dof3;

public class TestBiome extends Biome
{

	public TestBiome()
	{
	}

	@Override
	public int getTopBlockId(double noise)
	{
//		if (noise >= 85)
//		{
//			return Tile.stone.id;
//		}
//		if (noise > 64 && noise < 66)
//		{
//			return Tile.sand.id;
//		} else if (isInRange(60, 64, noise))
//		{
//			return Tile.water.id;
//		} else if (noise < 60 && noise > 58)
//		{
//			return Tile.sand.id;
//		} else if (noise >= 70)
//		{
//			return Tile.darkGrass.id;
//		} else
//		{
//			return Tile.grass.id;
//		}
		return 1;
	}
	
	@Override
	public NoiseParam getNoiseParam()
	{
		return new NoiseParam(2, 32, 64, 2, 0.25);
	}
	
	@Override
	public boolean getBiome(double value)
	{
		return value > 0;
	}
	
	@Override
	public int getBiomeColor()
	{
		return 0xff00ff00;
	}

}
