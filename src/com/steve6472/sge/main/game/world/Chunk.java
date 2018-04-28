/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.game.world;

import com.steve6472.sge.main.SGArray;
import com.steve6472.sge.main.Util;

public class Chunk
{
	/**
	 * Tile ids
	 */
	private SGArray<int[]> map;
	
	/*
	 * Static Chunk Variables
	 */
	static boolean inited = false;
	static int chunkWidth;
	static int chunkHeight;
	static int layerCount;
	
	public static void initChunks(int chunkWidth, int chunkHeight, int layerCount)
	{
		Chunk.inited = true;
		Chunk.chunkWidth = Math.min(chunkWidth, 4096);
		Chunk.chunkHeight = Math.min(chunkHeight, 4096);
		Chunk.layerCount = Math.min(layerCount, 4096);
	}
	
	public Chunk()
	{
		map = new SGArray<int[]>(layerCount, false, false);
		for (int l = 0; l < layerCount; l++)
		{
			map.setObject(l, new int[chunkWidth * chunkHeight]);
		}
	}
	
	/**
	 * Automatically ignores {@code 0} in map
	 */
	public void render(GameCamera camera, int offsetX, int offsetY)
	{
		GameTile.prepare();

		for (int l = 0; l < layerCount; l++)
		{
			for (int x = 0; x < chunkWidth; x++)
			{
				for (int y = 0; y < chunkHeight; y++)
				{
					int id = map.getObject(l)[x + y * chunkWidth];
					if (id != 0)
					{
						GameTile.quickRender(x + offsetX * chunkWidth, y + offsetY * chunkHeight, id, camera);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param layer
	 * @return if boundaries are valid return id. -1 othervise
	 */
	public int getTileId(int x, int y, int layer)
	{
		return isCoordInBounds(x, y, layer) ? map.getObject(layer)[x + y * chunkWidth] : -1;
	}
	
	public int getTileIdUnsafe(int x, int y, int layer)
	{
		return map.getObject(layer)[x + y * chunkWidth];
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param layer
	 * @param id
	 * @return true if tile has been succesfuly setted. False in case of negative or too big coordinates.
	 */
	public boolean setTileId(int x, int y, int layer, int id)
	{
		boolean flag = isCoordInBounds(x, y, layer);
		if (flag)
			map.getObject(layer)[x + y * chunkWidth] = id;
		return flag;
	}
	
	public boolean isCoordInBounds(int x, int y, int layer)
	{
		return (Util.isNumberInRange(0, chunkWidth, x) && Util.isNumberInRange(0, chunkHeight, y) && Util.isNumberInRange(0, layerCount, layer));
	}
}
