/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.game.world;

import java.util.List;

import com.steve6472.sge.gfx.Camera;
import com.steve6472.sge.gfx.Model;
import com.steve6472.sge.main.SGArray;
import com.steve6472.sge.main.Util;

public class Chunk
{
	/**
	 * Tile ids
	 */
	protected SGArray<int[]> map;
	
	/*
	 * Static Chunk Variables
	 */
	static boolean inited = false;
	public static int chunkWidth;
	public static int chunkHeight;
	public static int layerCount;
	
	/*
	 * Chunk specific data 
	 */
	Model model;
	float[] vertices;
	float[] textures;
	float[] colors;
	
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
		
		gen(chunkWidth, chunkHeight);
		
		vertices = toFloat(ver);
		textures = toFloat(tex);
		colors = toFloat(col);
		
		model = new Model(vertices, textures, colors);
		
		ver.clear();
		tex.clear();
		col.clear();
	}
	
	private float[] toFloat(List<Float> list)
	{
		float[] f = new float[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			f[i] = list.get(i);
		}
		return f;
	}
	
	List<Float> ver;
	List<Float> tex;
	List<Float> col;
	
	public void gen(int sizeX, int sizeZ)
	{
		ver.clear();
		tex.clear();
		col.clear();

		for (float i = 0; i < sizeX; i++)
		{
			for (float j = 0; j < sizeZ; j++)
			{
				float inX = 0;
				float inY = 0;
			
				inX++;
				inY++;
				
				float sx = 1f / (GameTile.tileAtlas.getAtlas().getWidth() / GameTile.tileWidth);
				float sy = 1f / (GameTile.tileAtlas.getAtlas().getHeight() / GameTile.tileHeight);
				
				float x = sx * inX;
				float y = sy * inY;
				
				float X = sx * (inX - 1);
				float Y = sy * (inY - 1);
				
				add(0 + i, 0 + j, X, y);
				add(1 + i, 0 + j, x, y);
				add(1 + i, 1 + j, x, Y);
				add(0 + i, 1 + j, X, Y);
			}
		}
	}
	
	private void add(float x, float y, float tx, float ty)
	{
		ver.add(x);
		ver.add(y);
		
		tex.add(tx);
		tex.add(ty);

		col.add(0f);
		col.add(0f);
		col.add(0f);
		col.add(0f);
	}
	
	private void set(int x_, int y_, int ix, int iy)
	{
		int i = 4;
		
		float inX = ix;
		float inY = iy;
	
		inX++;
		inY++;
		
		float sx = 1f / (GameTile.tileAtlas.getAtlas().getWidth() / GameTile.tileWidth);
		float sy = 1f / (GameTile.tileAtlas.getAtlas().getHeight() / GameTile.tileHeight);
		
		float x = sx * inX;
		float y = sy * inY;
		
		float X = sx * (inX - 1);
		float Y = sy * (inY - 1);
		
		textures[i * 8 + 0] = X;
		textures[i * 8 + 1] = y;
		
		textures[i * 8 + 2] = x;
		textures[i * 8 + 3] = y;
		
		textures[i * 8 + 4] = x;
		textures[i * 8 + 5] = Y;
		
		textures[i * 8 + 6] = X;
		textures[i * 8 + 7] = Y;
	}
	
	/**
	 * Automatically ignores {@code 0} in map
	 */
	public void render(Camera camera, int offsetX, int offsetY)
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
	 * @return if boundaries are valid return id. 0 othervise
	 */
	public int getTileIdSafe(int x, int y, int layer)
	{
		return isCoordInBounds(x, y, layer) ? map.getObject(layer)[x + y * chunkWidth] : 0;
	}
	
	public int getTileId(int x, int y, int layer)
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
	public boolean setTileIdSafe(int x, int y, int layer, int id)
	{
		boolean flag = isCoordInBounds(x, y, layer);
		if (flag)
			map.getObject(layer)[x + y * chunkWidth] = id;
		return flag;
	}

	public void setTileId(int x, int y, int layer, int id)
	{
		map.getObject(layer)[x + y * chunkWidth] = id;
	}
	
	public boolean isCoordInBounds(int x, int y, int layer)
	{
		return (Util.isNumberInRange(0, chunkWidth - 1, x) && Util.isNumberInRange(0, chunkHeight - 1, y) && Util.isNumberInRange(0, layerCount - 1, layer));
	}
	
	public SGArray<int[]> getMap()
	{
		return map;
	}
	
	public void setTiles(int[] tiles, int layer)
	{
		map.setObject(layer, tiles);
	}
}
