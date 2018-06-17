/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 28. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.game.world;

import com.steve6472.sge.gfx.Camera;
import com.steve6472.sge.main.Util;
import com.steve6472.sge.test.DynamicModel;

public class World
{
	Chunk[] chunks;
	int startX;
	int startY;
	int endX;
	int endY;
	int oldX = Integer.MIN_VALUE;
	int oldY = Integer.MIN_VALUE;
	public int renderedChunks = 0;
	
	/*
	 * Static variables
	 */
	static boolean inited = false;
	public static int worldWidth;
	public static int worldHeight;
	
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
	
	@FunctionalInterface
	public interface IRender<X, Y, L>
	{
		public void apply(X x, Y y, L l);
	}
	
	@FunctionalInterface
	public interface IRender0<X, Y, L, I>
	{
		public void apply(X x, Y y, L l, I i);
	}
	
	public void render(Camera camera)
	{
		GameTile.prepare();
		
		tryRecalculateBounds(camera, -1);
		
		renderedChunks = 0;
		
		DynamicModel.start();
		
		Chunk.prepare();
		
		iterateVisibleChunks(camera, (x, y, l, c) ->
		{
			c.render(camera, camera.getX() + -x * Chunk.chunkWidth * GameTile.tileWidth,
					camera.getY() + -y * Chunk.chunkHeight * GameTile.tileHeight);
			renderedChunks++;
		});
		
		DynamicModel.finish();
	}
	
	/**
	 * x, y, l, id
	 */
	public void iterateVisibleChunks(Camera camera, IRender0<Integer, Integer, Integer, Chunk> iter)
	{
		for (int x = startX; x < endX; x++)
		{
			for (int y = startY; y < endY; y++)
			{
				Chunk c = getChunk(x, y);
				
				if (c == null)
					continue;
				
				for (int l = 0; l < Chunk.layerCount; l++)
				{
					iter.apply(x, y, l, c);
				}
			}
		}
	}
	
	public void createBlankChunks()
	{
		for (int i = 0; i < worldWidth * worldHeight; i++)
		{
			chunks[i] = new Chunk();
		}
	}
	
	public void createBlankChunks(Class<? extends Chunk> chunk)
	{
		for (int i = 0; i < worldWidth * worldHeight; i++)
		{
			try
			{
				chunks[i] = chunk.newInstance();
			} catch (InstantiationException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public Chunk getChunkFromTileCoords(int x, int y)
	{
		return chunks[(x / Chunk.chunkWidth) + (y / Chunk.chunkHeight) * worldWidth];
	}
	
	public int getTileInWorld(int x, int y, int layer)
	{
		int cx = x / Chunk.chunkWidth;
		int cy = y / Chunk.chunkHeight;
		Chunk c = chunks[cx + cy * worldWidth];
		return c.getTileId(x - cx * Chunk.chunkWidth, y - cy * Chunk.chunkHeight, layer);
	}
	
	public int getTileInWorldSafe(int x, int y, int layer)
	{
		int cx = x / Chunk.chunkWidth;
		int cy = y / Chunk.chunkHeight;
		if (cx < 0 || cy < 0 || cx > worldWidth - 1 || cy > worldHeight - 1)
			return 0;
		Chunk c = chunks[cx + cy * worldWidth];
		if (c != null)
		{
			return c.getTileIdSafe(x - cx * Chunk.chunkWidth, y - cy * Chunk.chunkHeight, layer);
		}
		else return 0;
	}
	
	public int getTileInWorld(int index, int layer)
	{
		int x = index % (Chunk.chunkWidth * World.worldWidth);
		int y = index / (Chunk.chunkHeight * World.worldHeight);
		int cx = x / Chunk.chunkWidth;
		int cy = y / Chunk.chunkHeight;
		Chunk c = chunks[cx + cy * worldWidth];
		return c.getTileId(x - cx * Chunk.chunkWidth, y - cy * Chunk.chunkHeight, layer);
	}
	
	public int getTileInWorldSafe(int index, int layer)
	{
		int x = index % (Chunk.chunkWidth * World.worldWidth);
		int y = index / (Chunk.chunkHeight * World.worldHeight);
		int cx = x / Chunk.chunkWidth;
		int cy = y / Chunk.chunkHeight;
		if (cx < 0 || cy < 0 || cx > worldWidth || cy > worldHeight)
			return 0;
		Chunk c = chunks[cx + cy * worldWidth];
		if (c != null)
		{
			return c.getTileIdSafe(x - cx * Chunk.chunkWidth, y - cy * Chunk.chunkHeight, layer);
		} else
		{
			return 0;
		}
	}
	
	public void setTileInWorld(int x, int y, int layer, int id)
	{
		int cx = x / Chunk.chunkWidth;
		int cy = y / Chunk.chunkHeight;
		Chunk c = chunks[cx + cy * worldWidth];
		c.setTileId(x - cx * Chunk.chunkWidth, y - cy * Chunk.chunkHeight, layer, id);
	}
	
	public void setTileInWorldSafe(int x, int y, int layer, int id)
	{
		int cx = x / Chunk.chunkWidth;
		int cy = y / Chunk.chunkHeight;
		if (cx < 0 || cy < 0 || cx > worldWidth || cy > worldHeight)
			return;
		Chunk c = chunks[cx + cy * worldWidth];
		if (c != null)
		{
			c.setTileIdSafe(x - cx * Chunk.chunkWidth, y - cy * Chunk.chunkHeight, layer, id);
		}
	}
	
	public void setTileInWorld(int index, int layer, int id)
	{
		int x = index % (Chunk.chunkWidth * World.worldWidth);
		int y = index / (Chunk.chunkHeight * World.worldHeight);
		int cx = x / Chunk.chunkWidth;
		int cy = y / Chunk.chunkHeight;
		Chunk c = chunks[cx + cy * worldWidth];
		c.setTileId(x - cx * Chunk.chunkWidth, y - cy * Chunk.chunkHeight, layer, id);
	}
	
	public void setTileInWorldSafe(int index, int layer, int id)
	{
		int x = index % (Chunk.chunkWidth * World.worldWidth);
		int y = index / (Chunk.chunkHeight * World.worldHeight);
		int cx = x / Chunk.chunkWidth;
		int cy = y / Chunk.chunkHeight;
		if (cx < 0 || cy < 0 || cx > worldWidth || cy > worldHeight)
			return;
		Chunk c = chunks[cx + cy * worldWidth];
		if (c != null)
		{
			c.setTileIdSafe(x - cx * Chunk.chunkWidth, y - cy * Chunk.chunkHeight, layer, id);
		}
	}
	
	public void setTiles(int[] tiles, int layer, int chunkX, int chunkY)
	{
		chunks[chunkX + chunkY * worldWidth].setTiles(tiles, layer);
	}
	
	public void tryRecalculateBounds(Camera camera, int out)
	{
		if (camera.getX() != oldX || camera.getY() != oldY)
		{
			oldX = camera.getX();
			oldY = camera.getY();
			recalculateBounds(camera, out);
		}
	}
	
	public void recalculateBounds(Camera camera, int out)
	{
		int width = Chunk.chunkWidth * World.worldWidth;
		int height = Chunk.chunkHeight * World.worldHeight;
		
		int tw = GameTile.tileWidth * Chunk.chunkWidth;
		int th = GameTile.tileHeight * Chunk.chunkHeight;
		
		int x = camera.getX();
		int y = camera.getY();
		
		int w = camera.getWidth();
		int h = camera.getHeight();
		
		int mx = w / -tw - 1;
		int my = h / -th - 1;
		
		startX = Util.getNumberBetween(mx, width, (x + w / 2) / tw - (w / 2) / tw + out);
		startY = Util.getNumberBetween(my, height,(y + h / 2) / th - (h / 2) / th + out);
		
		endX = Util.getNumberBetween(0, World.worldWidth, startX + w / tw - out * 2 + 1);
		endY = Util.getNumberBetween(0, World.worldHeight, startY + h / th - out * 2 + 1);
		
		startX = Math.max(startX, 0);
		startY = Math.max(startY, 0);
	}
}
