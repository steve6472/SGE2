package steve6472.sge.gfx;

import steve6472.sge.main.MainApp;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 05.02.2020
 * Project: SJP
 *
 ***********************/
public class StaticTexture
{
	private int id, width, height;

	private static final List<Integer> staticTextures = new ArrayList<>();

	private StaticTexture()
	{
	}

	public static StaticTexture fromFile(File file, ResParam... params)
	{
		return createResource(loadImage(file), params);
	}

	public static StaticTexture fromFile(String path, ResParam... params)
	{
		return createResource(loadImage(new File(path)), params);
	}

	public static StaticTexture fromResource(InputStream stream, ResParam... params)
	{
		return createResource(loadImage(stream), params);
	}

	public static StaticTexture fromResource(String path, ResParam... params)
	{
		return createResource(loadImage(MainApp.class.getResourceAsStream(path)), params);
	}

	/**
	 * Defaults to [resource file]/textures/
	 * @param path Path to texture
	 * @param params Texture paramemets
	 * @return StaticTexture
	 */
	public static StaticTexture fromTexture(String path, ResParam... params)
	{
		return createResource(loadImage(MainApp.class.getResourceAsStream("/textures/" + path)), params);
	}


	public int getId()
	{
		return id;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
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

	public void delete()
	{
		if (id == -1) return;

		staticTextures.remove((Integer) id);
		glDeleteTextures(id);
		id = -1;
	}

	public static void cleanUp()
	{
		for (int i : staticTextures)
		{
			glDeleteTextures(i);
		}
	}

	private static StaticTexture createResource(BufferedImage image, ResParam... params)
	{
		if (image == null)
		{
			throw new NullPointerException();
		}

		StaticTexture resource = new StaticTexture();
		resource.width = image.getWidth();
		resource.height = image.getHeight();
		if (params == null || params.length == 0)
			resource.id = resource.createTexture(image, ResParam.MIN_N, ResParam.MAG_N);
		else
			resource.id = resource.createTexture(image, params);

		return resource;
	}

	private int createTexture(BufferedImage image, ResParam... params)
	{
		int id = glGenTextures();
		staticTextures.add(id);

		ByteBuffer pixels = toBuffer(image);

		glBindTexture(GL_TEXTURE_2D, id);

		for (ResParam p : params)
		{
			glTexParameteri(GL_TEXTURE_2D, p.getPname(), p.getParam());
		}

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

		glDisable(GL_TEXTURE_2D);

		return id;
	}

	private ByteBuffer toBuffer(BufferedImage image)
	{
		int[] rawPixels = image.getRGB(0, 0, width, height, null, 0, width);

		ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				int pixel = rawPixels[y * width + x];
				pixels.put((byte) ((pixel >> 16) & 0xFF));  // RED
				pixels.put((byte) ((pixel >> 8) & 0xFF));   // GREEN
				pixels.put((byte) ((pixel) & 0xFF));        // BLUE
				pixels.put((byte) ((pixel >> 24) & 0xFF));  // ALPHA
			}
		}

		pixels.flip();

		return pixels;
	}

	private static BufferedImage loadImage(File file)
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

	private static BufferedImage loadImage(InputStream file)
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
}
