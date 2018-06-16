/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 5. 10. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.test.dof3;

import com.steve6472.sge.main.Util;

public class Noise
{
	private final int seed;
	
	private int octaves;
	private int amplitude;
	private int smoothness;
	private int heightOffset;
	
	private double roughness;
	
	public Noise(int seed)
	{
		this.seed = seed;
	}
	
	public Noise()
	{
		this.seed = Util.getRandomInt(325322, 424);
	}
	
	public void setParameters(int octaves, int amplitude, int smoothness, int heightOffset, double roughness)
	{
		this.octaves = octaves;
		this.amplitude = amplitude;
		this.smoothness = smoothness;
		this.heightOffset = heightOffset;
		this.roughness = roughness;
	}
	
	public void setParameters(NoiseParam np)
	{
		this.octaves = np.octaves;
		this.amplitude = np.amplitude;
		this.smoothness = np.smoothness;
		this.heightOffset = np.heightOffset;
		this.roughness = np.roughness;
	}
	
	static int CHUNK_SIZE = 32;
	static int WATER_LEVEL = 64;
	
	public double getHeight(int x, int z, int chunkX, int chunkZ)
	{
		double newX = (x + chunkX * CHUNK_SIZE);
		double newZ = (z + chunkZ * CHUNK_SIZE);
		
		if (newX < 0 || newZ < 0)
		{
			return WATER_LEVEL -1;
		}
		
		double totalValue = 0;

		for (int i = 0; i < octaves - 1; i++)
		{
			double frequency = Math.pow(2.0, i);
			double amplitude = Math.pow(roughness, i);
			totalValue += noise(((double) newX) * frequency / smoothness, ((double) newZ) * frequency / smoothness) * amplitude;
		}

		double val = (((totalValue / 2.1) + 1.2) * amplitude) + heightOffset;

		return val > 0 ? val : 1;
	}

	private double getNoise(int n)
	{
		n += getSeed();
		n = (n << 13) ^ n;
		double newN = (n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff;

		return 1.0 - ((double) newN / 1073741824.0);
	}
	
	private double getNoise(double x, double z)
	{
		return getNoise((int) (x + z * 57));
	}

	private double lerp(double a, double b, double z)
	{
		double mu2 = (1 - Math.cos(z * 3.14d)) / 2;
		return (a * (1 - mu2) + b * mu2);
	}
	
	private double noise(double x, double z)
	{
		double floorX = (double) ((int) x);
		double floorZ = (double) ((int) z);

		double s = getNoise(floorX, 	floorZ);
		double t = getNoise(floorX + 1, floorZ);
		double u = getNoise(floorX, 	floorZ + 1);
		double v = getNoise(floorX + 1, floorZ + 1);
		
		double rec1 = lerp(s, t, x - floorX);
		double rec2 = lerp(u, v, x - floorX);
		double rec3 = lerp(rec1 ,rec2, z - floorZ);
		
		return rec3;
	}
	
	public int getSeed()
	{
		return seed;
	}
}
