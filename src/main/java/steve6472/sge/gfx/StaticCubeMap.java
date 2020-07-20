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
import static org.lwjgl.opengl.GL13.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 05.02.2020
 * Project: SJP
 *
 ***********************/
public class StaticCubeMap
{
	private int id, width, height;

	private static final List<Integer> staticCubeMaps = new ArrayList<>();

	private static final String[] suffix = {"PX", "NX", "PY", "NY", "PZ", "NZ"};
	private static final String[] faces = {"right", "left", "top", "bottom", "front", "back"};

	private StaticCubeMap()
	{
	}

	/**
	 * Defaults to [resource file]/textures/
	 *
	 * @param path Path to texture <br>
	 *             <i>Example:</i><br>
	 *             cubemap/sky
	 *             <br>
	 *             Requires six textures ending with sky_PX, sky_NX... to exist
	 *             <br>
	 *
	 *             <br>
	 * @param extension extension of texture file png jpg ...
	 * @param params Texture paramemets
	 * @return StaticTexture
	 */
	public static StaticCubeMap fromTextureSuffix(String path, String extension, ResParam... params)
	{
		BufferedImage[] images = new BufferedImage[6];
		for (int i = 0; i < suffix.length; i++)
		{
			String ex = suffix[i];
			images[i] = loadImage(MainApp.class.getResourceAsStream("/textures/" + path + "_" + ex + "." + extension));
		}
		return createResource(images, params);
	}

	/**
	 * Defaults to [resource file]/textures/
	 *
	 * @param path Path to file with texture <br>
	 *             Textures must have names: right, left, top, bottom, front, back
	 * @param extension extension of texture file png jpg ...
	 * @param params Texture paramemets
	 * @return StaticTexture
	 */
	public static StaticCubeMap fromTextureFace(String path, String extension, ResParam... params)
	{
		BufferedImage[] images = new BufferedImage[6];
		for (int i = 0; i < faces.length; i++)
		{
			images[i] = loadImage(MainApp.class.getResourceAsStream("/textures/" + path + "/" + faces[i] + "." + extension));
		}
		return createResource(images, params);
	}

	/**
	 * Defaults to [resource file]/textures/
	 *
	 * @param path Path to file with faces (can not be empty)
	 * @param faces face names
	 * @param extension extension of texture file png jpg ...
	 * @param params Texture paramemets
	 * @return StaticTexture
	 */
	public static StaticCubeMap fromTextureFaces(String path, String[] faces, String extension, ResParam... params)
	{
		BufferedImage[] images = new BufferedImage[6];
		for (int i = 0; i < faces.length; i++)
		{
			images[i] = loadImage(MainApp.class.getResourceAsStream("/textures/" + path + "/" + faces[i] + "." + extension));
		}
		return createResource(images, params);
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
			glBindTexture(GL_TEXTURE_CUBE_MAP, id);
		}
	}

	public void delete()
	{
		if (id == -1) return;

		staticCubeMaps.remove((Integer) id);
		glDeleteTextures(id);
		id = -1;
	}

	public static void cleanUp()
	{
		for (int i : staticCubeMaps)
		{
			glDeleteTextures(i);
		}
	}

	private static StaticCubeMap createResource(BufferedImage[] images, ResParam... params)
	{
		if (images == null || images.length != 6)
		{
			throw new NullPointerException();
		}

		StaticCubeMap resource = new StaticCubeMap();
		resource.width = images[0].getWidth();
		resource.height = images[0].getHeight();
		if (params == null || params.length == 0)
			resource.id = resource.createTexture(images,
				ResParam.MAG_L, ResParam.MIN_L, ResParam.WRAP_R_EDGE, ResParam.WRAP_S_EDGE, ResParam.WRAP_T_EDGE);
		else
			resource.id = resource.createTexture(images, params);

		return resource;
	}

	private int createTexture(BufferedImage[] images, ResParam... params)
	{
		int id = glGenTextures();
		staticCubeMaps.add(id);

		glBindTexture(GL_TEXTURE_CUBE_MAP, id);

		for (int i = 0; i < 6; i++)
		{
			ByteBuffer pixels = toBuffer(images[i]);

			glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		}

		for (ResParam p : params)
		{
			glTexParameteri(GL_TEXTURE_CUBE_MAP, p.getPname(), p.getParam());
		}

		glDisable(GL_TEXTURE_CUBE_MAP);

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
