/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.game.world;

import com.steve6472.sge.main.Util;

public class World
{
	Chunk[] chunks;
	int startX;
	int startY;
	int endX;
	int endY;
	int oldX = Integer.MIN_VALUE;
	int oldY = Integer.MIN_VALUE;
	public int renderedTiles = 0;
	
	/*
	 * Static variables
	 */
	static boolean inited = false;
	static int worldWidth;
	static int worldHeight;
	
	public static void initWorlds(int worldWidth, int worldHeight)
	{
		inited = true;
		World.worldWidth = Math.min(worldWidth, 4096);
		World.worldHeight = Math.min(worldHeight, 4096);
	}
	
	public World()
	{
		chunks = new Chunk[worldWidth * worldHeight];
	}
	
	public Chunk getChunk(int x, int y)
	{
		return chunks[x + y * worldWidth];
	}
	
	public void setChunk(int x, int y, Chunk chunk)
	{
		chunks[x + y * worldWidth] = chunk;
	}
	
	public void render(GameCamera camera)
	{
		GameTile.prepare();
		
		if (camera.getX() != oldX || camera.getY() != oldY)
		{
			oldX = camera.getX();
			oldY = camera.getY();
			recalculateBounds(camera, 0);
		}
		
		renderedTiles = 0;

		for (int x = startX; x < endX; x++)
		{
			for (int y = startY; y < endY; y++)
			{
				Chunk c = getChunkFromTileCoords(x, y);
				
				if (c == null)
					continue;
				
				for (int l = 0; l < Chunk.layerCount; l++)
				{
					int id = c.getTileIdUnsafe(x / Chunk.chunkWidth, y / Chunk.chunkHeight, l);
					if (id != 0)
					{
						renderedTiles++;
						GameTile.quickRender(x, y, id, camera);
					}
				}
			}
		}
	}
	
	public Chunk getChunkFromTileCoords(int x, int y)
	{
		return chunks[(x / Chunk.chunkWidth) + (y / Chunk.chunkHeight) * worldWidth];
	}
	
	public int getTileInWorld(int x, int y, int layer)
	{
		return getChunkFromTileCoords(x, y).getTileIdUnsafe(x / Chunk.chunkWidth, y / Chunk.chunkHeight, layer);
	}
	
	public void recalculateBounds(GameCamera camera, int out)
	{
		int width = Chunk.chunkWidth * World.worldWidth;
		int height = Chunk.chunkHeight * World.worldHeight;
		
		int tw = GameTile.tileWidth;
		int th = GameTile.tileHeight;
		
		startX = Util.getNumberBetween(0, width, (camera.getX() + camera.getWidth() / 2) / tw - (camera.getWidth() / 2) / tw + out);
		startY = Util.getNumberBetween(0, height, (camera.getY() + camera.getHeight() / 2) / th - (camera.getHeight() / 2) / th + out);
		endX = Util.getNumberBetween(0, width, startX + camera.getWidth() / tw - out * 2 + 1);
		endY = Util.getNumberBetween(0, height, startY + camera.getHeight() / th - out * 2 + 1);
	}
}
