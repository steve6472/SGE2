/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 11. 3. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.gfx;

import java.awt.image.BufferedImage;
import java.io.File;

public class Texture extends Sprite
{
	public Texture()
	{
		id = -1;
		width = 0;
		height = 0;
	}

	public Texture(BufferedImage image)
	{
		super(image);
	}
	
	public Texture(int[] pixels, int width, int height)
	{
		create(pixels, width, height);
	}
	
	public Texture(int width, int height)
	{
		this.pixels = new int[width * height];
		this.width = width;
		this.height = height;
	}

	public Texture(File file)
	{
		super(file);
	}
	
	@Override
	protected void create(int[] pixels_raw, int width, int height)
	{
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
		
		for(int y = 0; y < height; y++)
		{
            for(int x = 0; x < width; x++)
            {
                int pixel = pixels_raw[y * width + x];
				this.pixels[y * width + x] = pixel;
			}
		}
	}
	
	public void setPixels(int[] arr)
	{
		this.pixels = arr;
	}
	
	public void setPixel(int index, int color)
	{
		this.pixels[index] = color;
	}
	
	public void setPixel(int x, int y, int color)
	{
		this.pixels[x + y * width] = color;
	}
}
