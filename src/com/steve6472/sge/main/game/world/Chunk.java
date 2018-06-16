/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 27. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.game.world;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.steve6472.sge.gfx.Camera;
import com.steve6472.sge.gfx.SavedShaders;
import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.gfx.Tessellator;
import com.steve6472.sge.main.SGArray;
import com.steve6472.sge.main.Util;
import com.steve6472.sge.test.DynamicModel;

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
	static Shader chunkShader;
	
	/*
	 * Chunk specific data 
	 */
	DynamicModel[] models;
	
	public static void initChunks(int chunkWidth, int chunkHeight, int layerCount)
	{
		Chunk.inited = true;
		Chunk.chunkWidth = Math.min(chunkWidth, 4096);
		Chunk.chunkHeight = Math.min(chunkHeight, 4096);
		Chunk.layerCount = Math.min(layerCount, 4096);
		chunkShader = new Shader(SavedShaders.TESS_VS, SavedShaders.TESS_FS);
	}
	
	public Chunk()
	{
		map = new SGArray<int[]>(layerCount, false, false);

		ver = new ArrayList<Float>();
		tex = new ArrayList<Float>();
		col = new ArrayList<Float>();
		
		models = new DynamicModel[layerCount];
		
		for (int l = 0; l < layerCount; l++)
		{
			map.setObject(l, new int[chunkWidth * chunkHeight]);
			
			gen(chunkWidth, chunkHeight);
			
			models[l] = new DynamicModel(toFloatBuffer(ver), toFloatBuffer(tex), toFloatBuffer(col));
		}
		
		ver.clear();
		tex.clear();
		col.clear();
	}
	
	static int chunks = 0;
	
	private FloatBuffer toFloatBuffer(List<Float> list)
	{
		FloatBuffer buff = BufferUtils.createFloatBuffer(list.size());
		for (float f : list)
		{
			buff.put(f);
		}
		buff.flip();
		return buff;
	}
	
	List<Float> ver;
	List<Float> tex;
	List<Float> col;
	
	public void gen(int sizeX, int sizeY)
	{
		ver.clear();
		tex.clear();
		col.clear();

		for (float i = 0; i < sizeX; i++)
		{
			for (float j = sizeY; j > 0 ; j--)
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
	
	private void set(int x, int y, int l, int ix, int iy)
	{
		int i = y + x * chunkWidth;
		
		float inX = ix;
		float inY = iy;
	
		inX++;
		inY++;
		
		float sx = 1f / (GameTile.tileAtlas.getAtlas().getWidth() / GameTile.tileWidth);
		float sy = 1f / (GameTile.tileAtlas.getAtlas().getHeight() / GameTile.tileHeight);
		
		float x_ = sx * inX;
		float y_ = sy * inY;
		
		float X_ = sx * (inX - 1);
		float Y_ = sy * (inY - 1);

		models[l].getTex().put(i * 8 + 0, X_);
		models[l].getTex().put(i * 8 + 1, y_);
		
		models[l].getTex().put(i * 8 + 2, x_);
		models[l].getTex().put(i * 8 + 3, y_);
		
		models[l].getTex().put(i * 8 + 4, x_);
		models[l].getTex().put(i * 8 + 5, Y_);
		
		models[l].getTex().put(i * 8 + 6, X_);
		models[l].getTex().put(i * 8 + 7, Y_);
	}
	
	private void set(int x, int y, int l, int index)
	{
		set(x, y, l, index % GameTile.tileAtlas.getSize(), index / GameTile.tileAtlas.getSize());
	}
	
	public static void prepare()
	{
		chunkShader.bind();
	}
	
	/**
	 * Automatically ignores {@code 0} in map
	 */
	public void render(Camera camera, int offsetX, int offsetY)
	{
		float w = (float) camera.getWidth();
		float h = (float) camera.getHeight();
		
		float W = 1f / w * GameTile.tileWidth * 2f;
		float H = 1f / h * GameTile.tileHeight * 2f;
		
		float pixW = 1f / GameTile.tileWidth;
		float pixH = 1f / GameTile.tileHeight;
		
		chunkShader.setUniformMat4f("projection", 
				new Matrix4f()
				.scale(W, H, 0)
				.translate(pixW * -w / 2, -1f + pixH * h / 2 - chunkHeight, 0)
				.translate((float) offsetX / -32, (float) offsetY / 32, 0));
		
		models[0].render2(Tessellator.QUADS);
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
		{
			map.getObject(layer)[x + y * chunkWidth] = id;
			set(x, y, layer, id);
		}
		return flag;
	}

	public void setTileId(int x, int y, int layer, int id)
	{
		map.getObject(layer)[x + y * chunkWidth] = id;
		set(x, y, layer, id);
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
		
		for (int i = 0; i < chunkWidth; i++)
		{
			for (int j = 0; j < chunkHeight; j++)
			{
				set(i, j, layer, tiles[i + j * chunkWidth]);
			}
		}
	}
}
