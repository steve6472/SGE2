package com.steve6472.sge.gfx;

import com.steve6472.sge.main.MainApp;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static com.steve6472.sge.main.util.ColorUtil.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Sprite
{
	protected int id, width, height;
	protected int[] pixels;
	
	public Sprite()
	{
		id = -1;
		width = 0;
		height = 0;
	}

	public Sprite(BufferedImage image)
	{
		create(image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth()), image.getWidth(), image.getHeight());
	}

	public Sprite(File file)
	{
		BufferedImage image = load(file);
//		System.out.println(image.getWindowWidth() * image.getWindowHeight() + " " + file.getAbsolutePath());
		create(image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth()), image.getWidth(), image.getHeight());
	}
	
	public Sprite(String fileName)
	{
		load(fileName);
	}

	public void load(String fileName)
	{
		if (fileName.startsWith("*"))
		{
			Sprite s = new Sprite(new File(fileName.substring(1)));
			this.width = s.getWidth();
			this.height = s.getHeight();
			this.id = s.getId();
			this.pixels = s.getPixels();
		} else
		{
			if (!fileName.startsWith("/"))
			{
				fileName = "/" + fileName;
			}

			fileName = "/textures" + fileName;
			BufferedImage image = load(MainApp.class.getResourceAsStream(fileName));
			create(image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth()), image.getWidth(), image.getHeight());
		}
	}
	
	public Sprite(int[] pixels, int width, int height)
	{
		create(pixels, width, height);
	}

	protected void create(int[] pixels_raw, int width, int height)
	{
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
		
		ByteBuffer pixels = loadPixels(pixels_raw);
		
		id = glGenTextures();

		glBindTexture(GL_TEXTURE_2D, id);

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		
		glDisable(GL_TEXTURE_2D);
		
	}
	
	public ByteBuffer loadPixels(int[] rawPixels)
	{
		ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

		for(int y = 0; y < height; y++)
		{
            for(int x = 0; x < width; x++)
            {
                int pixel = rawPixels[y * width + x];
				this.pixels[y * width + x] = pixel;
				pixels.put((byte) ((pixel >> 16) & 0xFF)); // RED
				pixels.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
				pixels.put((byte) ((pixel) & 0xFF)); // BLUE
				pixels.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
			}
		}

		pixels.flip();
		
		return pixels;
	}

	public void deleteSprite()
	{
		if (id == -1) return;

		glDeleteTextures(id);
		id = -1;
		width = 0;
		height = 0;
	}
	
	private BufferedImage load(File file)
	{
		try
		{
			return ImageIO.read(file);
		} catch (IOException e)
		{
			System.err.println(file.getAbsolutePath());
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
		bind(0);
	}

	public void bind(int sampler)
	{
		if (id != -1)
		{
			if (sampler >= 0 && sampler <= 31)
				glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}

	public static void bind(int sampler, int id)
	{
		if (id != -1)
		{
			if (sampler >= 0 && sampler <= 31)
				glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
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
	
	public int[] getPixels()
	{
		return pixels;
	}
	
	public void setPixel(int x, int y, int color)
	{
		this.pixels[x + y * width] = color;
	}
	
	public void setPixels(int[] pixels)
	{
		this.pixels = pixels;
	}

	@Override
	public String toString()
	{
		return "Sprite [id=" + id + ", width=" + width + ", height=" + height + "]";
	}
	
	public BufferedImage toBufferedImage()
	{
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				img.setRGB(i, j, pixels[i + j * width]);
			}
		}
		return img;
	}
	
	public void save(File file)
	{
		try
		{
			ImageIO.write(toBufferedImage(), "PNG", file);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public void change(int newWidth, int newHeight, int[] newPixels)
	{
		setSize(newWidth, newHeight);
		setPixels(newPixels);
		
		glBindTexture(GL_TEXTURE_2D, getId());

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, newWidth, newHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, newPixels);
		
		glDisable(GL_TEXTURE_2D);
	}

	public void change(boolean nearest)
	{
		glBindTexture(GL_TEXTURE_2D, getId());

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, nearest ? GL_NEAREST : GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, nearest ? GL_NEAREST : GL_LINEAR);

		glDisable(GL_TEXTURE_2D);
	}
	
	public static int[] fromRGBtoBGR(int[] pixels)
	{
		int[] newPixels = new int[pixels.length];
		
		for (int i = 0; i < newPixels.length; i++)
		{
			int oldPixel = pixels[i];
			int r = getRed(oldPixel);
			int g = getGreen(oldPixel);
			int b = getBlue(oldPixel);
			int a = getAlpha(oldPixel);
			int newPixel = getColor(b, g, r, a);
			newPixels[i] = newPixel;
		}
		return newPixels;
	}
	
}
