/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 4. 7. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.test.dof3;

import java.util.HashMap;
import java.util.Iterator;

import org.joml.Matrix4f;

import com.steve6472.sge.gfx.PlayerLocation;
import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.main.Util;
import com.steve6472.sge.test.DynamicModel3D;

public class World3D
{
	public static int CHUNK_SIZE = 256;
	
	HashMap<Long, Chunk3D> chunks;
	float[] heightMap;
	Noise biomeNoise;
	
	static int SEED = Util.getRandomInt(Integer.MAX_VALUE - 1, 0);
//	static int SEED = 1346670638;

	public World3D()
	{
		SEED = Util.getRandomInt(Integer.MAX_VALUE - 1, 0);
		chunks = new HashMap<Long, Chunk3D>();
		biomeNoise = new Noise(SEED, CHUNK_SIZE);
		System.out.println("Seed: " + SEED);
		biomeNoise.setParameters(5, 128, 512, 0, 0.75);
		
//		for (int i = 0; i < CHUNK_COUNT; i++)
//		{
//			for (int j = 0; j < CHUNK_COUNT; j++)
//			{
//				generateChunk(i, j);
//			}
//		}
	}
	
	public Long getChunkKey(int x, int z)
	{
		return (long) x << 32 | (long) z;
	}
	
	public void render(Shader shader, PlayerLocation playerLocation)
	{
		Chunk3D.prepareRender();
		if (Test3D.distance == 0)
		{
			for (Iterator<Chunk3D> iter = chunks.values().iterator(); iter.hasNext();)
			{
				Chunk3D c = iter.next();

				shader.setUniformMat4f("transformation",
						new Matrix4f().translate(c.cx * (float) CHUNK_SIZE * Test3D.worldSize, 0, c.cz * (float) CHUNK_SIZE * Test3D.worldSize));
				c.render();
			}
		} else
		{
			if (Test3D.distance < 0)
				Test3D.distance = 0;
			int pcx = (int) (playerLocation.getX() / (World3D.CHUNK_SIZE * Test3D.worldSize));
			int pcz = (int) (playerLocation.getZ() / (World3D.CHUNK_SIZE * Test3D.worldSize));
			int d = Test3D.distance;
			int minX = pcx - d;
			int minZ = pcz - d;
			int maxX = pcx + d + 1;
			int maxZ = pcz + d + 1;
			for (int i = minX; i < maxX; i++)
			{
				for (int j = minZ; j < maxZ; j++)
				{
					Chunk3D c = chunks.get(getChunkKey(i, j));
					if (c == null)
					{
						generateChunk(i, j);
						continue;
					}
					shader.setUniformMat4f("transformation",
							new Matrix4f().translate(c.cx * (float) CHUNK_SIZE * Test3D.worldSize, 0, c.cz * (float) CHUNK_SIZE * Test3D.worldSize));
					c.render();
				}
			}
		}
		
		Chunk3D.stopRender();
		shader.setUniformMat4f("transformation", new Matrix4f());
	}
	
	public float[] getHeights(int xMin, int zMin, int xMax, int zMax)
	{
		float[] arr = new float[(xMax - xMin) * (zMax - zMin)];
		
		float f0 = getH(0, 0);
		float f1 = getH(0, CHUNK_SIZE);
		float f2 = getH(CHUNK_SIZE, 0);
		float f3 = getH(CHUNK_SIZE, CHUNK_SIZE);
		
		for (int x = xMin; x < xMax; ++x)
		{
			for (int z = zMin; z < zMax; ++z)
			{
				if (x == HEIGHT_MAP_SIZE)
					continue;
				if (z == HEIGHT_MAP_SIZE)
					continue;
				
				float h = SomeMath.smoothInterpolation(f0, f1, f2, f3, xMin, xMax, zMin, zMax, x, z);
				
				arr[(x - xMin) + (z - zMin) * (zMax - zMin)] = h;
			}
		}
		
		return arr;
	}
	
	public float getH(int x, int z)
	{
		double h = biomeNoise.getHeight(x, z, currentCx, currentCz);

		Biome b = Test3D.getBiome(h);
		noise.setParameters(b.getNoiseParam());

		float f = (float) noise.getHeight(x, z, currentCx, currentCz);
		return f;
	}
	
	public float getH(int x, int z, int cx, int cz)
	{
		double h = biomeNoise.getHeight(x, z, cx, cz);

		Biome b = Test3D.getBiome(h);
		noise.setParameters(b.getNoiseParam());

		float f = (float) noise.getHeight(x, z, cx, cz);
		return f;
	}

	static int HEIGHT_MAP_SIZE = CHUNK_SIZE + 1;
	
	Noise noise;
	int currentCx, currentCz;
	
	public void generateChunk(int cx, int cz)
	{
		noise = new Noise(SEED, CHUNK_SIZE);
		this.currentCx = cx;
		this.currentCz = cz;
		
		heightMap = getHeights(0, 0, CHUNK_SIZE + 1, CHUNK_SIZE + 1);
		
		if (cx > 0)
		{
			Chunk3D c = chunks.get(getChunkKey(cx - 1, cz));
			if (c != null)
			{
				for (int i = 0; i < CHUNK_SIZE + 1; i++)
				{
					heightMap[(-i - (i - 1) * CHUNK_SIZE) + (CHUNK_SIZE - 1) * CHUNK_SIZE + CHUNK_SIZE] = c.heightMap[(-i - (i - 1) * CHUNK_SIZE)
							+ (CHUNK_SIZE) * CHUNK_SIZE + CHUNK_SIZE];
				}
			}
		}

		if (cz > 0)
		{
			Chunk3D c = chunks.get(getChunkKey(cx, cz - 1));
			if (c != null)
			{
				for (int i = 0; i < CHUNK_SIZE + 1; i++)
				{
					heightMap[i] = c.heightMap[i + (CHUNK_SIZE + 1) * CHUNK_SIZE];
				}
			}
		}
		
		DynamicModel3D worldModel = new DynamicModel3D();

		int sizeX = CHUNK_SIZE;
		int sizeZ = CHUNK_SIZE + 1;
		
		float s = Test3D.worldSize;

		for (int x = 0; x < sizeX; x++)
		{
			for (int z = 0; z < sizeZ; z++)
			{
				if (z != 0)
					worldModel.add(x * s, getHeight(x, z) * s, z * s, getColor(getHeight(x, z - 1)));

				// Prevents stretching from last triangle
				if (z != sizeZ - 1)
				{
					worldModel.add((x + 1) * s, getHeight(x + 1, z) * s, z * s, getColor(getHeight(x, z)));
					worldModel.add(x * s, getHeight(x, z) * s, z * s, getColor(getHeight(x, z)));
				}
			}
		}

		for (int x = 1; x < sizeX + 1; x++)
		{
			for (int z = 0; z < sizeZ; z++)
			{
				if (z != 0)
					worldModel.add(x * s, getHeight(x, z) * s, z * s, getColor(getHeight(x - 1, z - 1)));

				if (z != sizeZ - 1)
				{
					worldModel.add(x * s, getHeight(x, z) * s, z * s, getColor(getHeight(x - 1, z)));
					worldModel.add((x - 1) * s, getHeight(x - 1, z + 1) * s, (z + 1) * s, getColor(getHeight(x - 1, z)));
				}
			}
		}
		worldModel.generate();
		
		Chunk3D chunk = new Chunk3D(worldModel, cx, cz);
		chunk.heightMap = heightMap;
		
		chunks.put(getChunkKey(cx, cz), chunk);
	}

	public float[] getColor(float height)
	{
		float m = Util.getRandomFloat(1f, 0.9f, (long) (height * 10000f));
		
		if (height < 30) // water
		{
			return new float[]
			{ 0f, 0f, Util.normalise(height, -20, 50) * m, 1f };
		}
		if (height > 30 && height < 70) // grass
		{
			return new float[]
			{ 0f, Util.normalise(height, -20, 80) * m, 0f, 1f };
		}
		if (height > 70) // ice
		{
			return new float[]
			{ Util.normalise(height, 0, 90) * m, Util.normalise(height, 0, 90) * m, Util.normalise(height, 0, 90) * m, 1f };
		}

		return new float[] { 0, 0, 0, 1 };
	}
	
	public float getHeight(int x, int z)
	{
		return heightMap[x + z * HEIGHT_MAP_SIZE];
	}
}
