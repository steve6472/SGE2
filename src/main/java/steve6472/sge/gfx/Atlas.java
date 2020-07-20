package steve6472.sge.gfx;

import steve6472.sge.main.MainApp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: (Original: 23. 3. 2018) Edited: 2. 1. 2019
 * Project: SGE2
 *
 ***********************/
public class Atlas
{
	private Sprite texture;
	private BufferedImage tempImage;

	private int s, size;
	private int index;
	private boolean finished;

	/**
	 *
	 * @param n number of max elements
	 * @param size width/height of invidual sprite
	 */
	public Atlas(int n, int size)
	{
//		this.s = (int) Math.ceil(log(2, n));
		this.s = getNextPowerOfTwo((int) Math.ceil(Math.sqrt(n)));

		this.size = size;
		tempImage = new BufferedImage(size * s, size * s, BufferedImage.TYPE_INT_ARGB);
	}

	public static int getNextPowerOfTwo(int value) {
		value -= 1;
		value |= value >> 16;
		value |= value >> 8;
		value |= value >> 4;
		value |= value >> 2;
		value |= value >> 1;
		return value + 1;
	}

	private double log(double base, double argument)
	{
		return Math.log(argument) / Math.log(base);
	}

	/**
	 *
	 * @param s number tileCount
	 * @param size width/height of invidual sprite
	 */
	public Atlas(int s, int size, File file)
	{
		this.s = s;
		this.size = size;
		tempImage = new BufferedImage(size * s, size * s, BufferedImage.TYPE_INT_ARGB);
		loadFull(file);
	}

	public void add(File file)
	{
		int x = index % s;
		int y = index / s;
		index++;

		BufferedImage image = loadImageFromSource(file);

		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				tempImage.setRGB(i + x * size, j + y * size, image.getRGB(i, j));
			}
		}
	}

	public void addNonSource(File file)
	{
		int x = index % s;
		int y = index / s;
		index++;

		BufferedImage image = null;
		try
		{
			image = ImageIO.read(file);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				tempImage.setRGB(i + x * size, j + y * size, image.getRGB(i, j));
			}
		}
	}

	public static BufferedImage loadImageFromSource(File file)
	{
		try
		{
			return ImageIO.read(MainApp.class.getResourceAsStream("/textures/" + file.getAbsolutePath().split("\\\\textures\\\\")[1]));
//			return ImageIO.read(new File("textures/" + file.getAbsolutePath().split("\\\\textures\\\\")[1]));
		} catch (IOException e)
		{
			System.err.println(file.getAbsolutePath().split("/textures/")[1]);
			e.printStackTrace();
			return null;
		}
	}

	public void add(BufferedImage image)
	{
		int x = index % s;
		int y = index / s;
		index++;

		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				tempImage.setRGB(i + x * size, j + y * size, image.getRGB(i, j));
			}
		}
	}

	public void loadFull(File file)
	{
		tempImage = loadImageFromSource(file);

		finished = true;
		//Create texture
		texture = new Sprite(tempImage);
		try
		{
			ImageIO.write(tempImage, "PNG", new File("textureAtlas.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void finish()
	{
		finished = true;
		//Create texture
//		for (int i = 0; i < getTotalSize(); i++)
//		{
//			for (int j = 0; j < getTotalSize(); j++)
//			{
//				if (tempImage.getRGB(i, j) == 0)
//				{
//					tempImage.setRGB(i, j, 0xff00ffff);
//				}
//			}
//		}

		texture = new Sprite(tempImage.getRGB(0, 0, size * s, size * s, null, 0, size * s), size * s, size * s);
		try
		{
			ImageIO.write(tempImage, "PNG", new File("textureAtlas.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public int getSpriteId()
	{
		if (!finished)
			throw new IllegalStateException("Atlas has not been finished!");
		return texture.id;
	}

	public Sprite getSprite()
	{
		if (!finished)
			throw new IllegalStateException("Atlas has not been finished!");
		return texture;
	}

	public int getSize()
	{
		return size;
	}

	public int getTileCount()
	{
		return s;
	}

	public int getTotalSize()
	{
		return getSize() * getTileCount();
	}
}
