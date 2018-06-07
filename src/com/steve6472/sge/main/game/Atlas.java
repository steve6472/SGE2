/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 23. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main.game;

import java.util.List;

import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gfx.Texture;
import com.steve6472.sge.main.Util;

public class Atlas
{
	List<String> textures;
	Sprite atlas;
	int size = 0;
	
	/* TODO:
	 * 
	 * Getting sprite positions based on index or name
	 * 
	 */
	
	
	public Atlas(List<String> textures)
	{
		this.textures = textures;
	}
	
	public Atlas(Sprite sprite, int size)
	{
		this.atlas = sprite;
		this.size = size;
	}
	
	public Sprite getAtlas()
	{
		return atlas;
	}
	
	public void create(int tileSize, Iter iter)
	{
		int box = 0;
		
		box = Util.getBiggestClosetsSqrt(textures.size()) * tileSize;
		
//		System.out.println("Box: " + box + " (" + (box / tileSize) + ")");
		
		size = box / tileSize;

		Texture atlas = new Texture(new int[box * box], box, box);

		for (int i = 0; i < textures.size(); i++)
		{
			int x = i % (box / tileSize);
			int y = i / (box / tileSize);
			
			iter.invoke(x, y, i);
			
			if (textures.get(i) != null && !textures.get(i).isEmpty())
			{
				System.out.println("Loading from " + textures.get(i) + " at index " + i + " X/Y: " + x + "/" + y);
				insertSprite(atlas, new Texture(textures.get(i)), 32, 32, box, x * tileSize, y * tileSize);
			}
		}
		
		this.atlas = new Sprite(atlas.getPixels(), box, box);
	}
	
	public static void insertSprite(Texture original, Texture toInsert, int toInsertWidth, int toInsertHeight, int originalWidth, int x, int y)
	{
		if (toInsert.getPixels().length > original.getPixels().length)
			return;
		
		for (int i = 0; i < toInsertWidth; i++)
		{
			for (int j = 0; j < toInsertHeight; j++)
			{
				original.setPixel((i + x) + (j + y) * originalWidth,  toInsert.getPixels()[i + j * toInsertWidth]);
			}
		}
	}
	
	public int getSize()
	{
		return size;
	}
	
	@FunctionalInterface
	public interface Iter
	{
		public void invoke(int x, int y, int i);
	}
}
