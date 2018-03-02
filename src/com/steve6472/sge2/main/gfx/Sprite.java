package com.steve6472.sge2.main.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import com.steve6472.sge2.main.MainApplication;

public class Sprite
{
	private int id, width, height;
	
	public Sprite()
	{
		id = -1;
		width = 0;
		height = 0;
	}

	public Sprite(File file)
	{
		create(load(file));
	}
	
	public Sprite(String fileName)
	{
		if (fileName.startsWith("*"))
		{
			Sprite s = new Sprite(new File(fileName.substring(1)));
			this.id = s.getId();
			this.width = s.getWidth();
			this.height = s.getHeight();
		} else
		{
			if (!fileName.startsWith("/"))
			{
				fileName = "/" + fileName;
			}
			
			fileName = "/textures" + fileName;
			create(load(MainApplication.class.getResourceAsStream(fileName)));
		}
	}

	private void create(BufferedImage bi)
	{
		width = bi.getWidth();
		height = bi.getHeight();
		
		int[] pixels_raw = new int[width * height];
		pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);

		ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				int pixel = pixels_raw[i * width + j];
				pixels.put((byte) ((pixel >> 16) & 0xFF)); // RED
				pixels.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
				pixels.put((byte) ((pixel) & 0xFF)); // BLUE
				pixels.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
			}
		}

		pixels.flip();

		id = glGenTextures();

		glBindTexture(GL_TEXTURE_2D, id);

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		
		glDisable(GL_TEXTURE_2D);
		
	}

	private BufferedImage load(File file)
	{
		try
		{
			return ImageIO.read(file);
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private BufferedImage load(InputStream file)
	{
		try
		{
			return ImageIO.read(file);
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void bind()
	{
		if (id != -1)
			glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getId()
	{
		return id;
	}
}
