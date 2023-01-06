package steve6472.sge.gfx.game.blockbench.model;

import org.joml.Vector2f;
import steve6472.sge.gfx.StaticTexture;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**********************
 * Created by steve6472
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class ModelTextureAtlas
{
	private final List<String> usedTextures = new ArrayList<>();
	private final HashMap<String, Integer> usedTexturesReference = new HashMap<>();
	private Rectangle[] textures;
	private Atlas atlas;
	//todo: create default path
	
	public StaticTexture getTexture()
	{
		return atlas.getSprite();
	}

	public void assignTextures(BBModel model)
	{
		for (OutlinerElement element : model.getElements())
		{
			recursiveAssignTextures(element);
		}
	}

	public void assignTextures(BBModel... models)
	{
		for (BBModel model : models)
		{
			assignTextures(model);
		}
	}

	private void recursiveAssignTextures(OutlinerElement e)
	{
		if (e instanceof Outliner outliner)
		{
			for (OutlinerElement child : outliner.children)
			{
				recursiveAssignTextures(child);
			}
		} else if (e instanceof Element element)
		{
			faces(element);
		} else if (e instanceof MeshElement mesh)
		{
			meshFaces(mesh);
		}
	}

	public void faces(Element element)
	{
		if (element.north != null) face(element.north);
		if (element.east != null) face(element.east);
		if (element.south != null) face(element.south);
		if (element.west != null) face(element.west);
		if (element.up != null) face(element.up);
		if (element.down != null) face(element.down);
	}

	public void meshFaces(MeshElement mesh)
	{
		for (MeshElement.Face face : mesh.getFaces())
		{
			Rectangle r = getTexture(face.texture());
			float x = r.x;
			float y = r.y;
			float w = r.width;
			float h = r.height;
			float texel = atlas.getTexel();

			for (int i = 0; i < 3; i++)
			{
				Vector2f uv = face.getUvs()[i];
				uv.set((x + w * uv.x) * texel, (y + h * uv.y) * texel);
			}
		}
	}

	private void face(Element.Face face)
	{
		Rectangle r = getTexture(face.texture());
		float x = r.x;
		float y = r.y;
		float w = r.width;
		float h = r.height;
		float texel = atlas.getTexel();
		face.setU0((x + w * face.getU0()) * texel);
		face.setV0((y + h * face.getV0()) * texel);
		face.setU1((x + w * face.getU1()) * texel);
		face.setV1((y + h * face.getV1()) * texel);
	}

	public void compileTextures(int overrideSize)
	{
		// Correctly works only if all textures are 16x16 or less
		int size;
		if (overrideSize == 0)
			size = getNextPowerOfTwo((int) Math.ceil(Math.sqrt(usedTextures.size()))) * 16;
		else
			size = overrideSize;

		if (atlas != null)
			atlas.clean();

		atlas = new Atlas(size);
		for (String usedTexture : usedTextures)
		{
			try
			{
				atlas.add(usedTexture, new File("game/textures/" + usedTexture + ".png"));
			} catch (NullPointerException ex)
			{
				System.err.println(usedTexture);
				ex.printStackTrace();
			} catch (RuntimeException ex)
			{
				int newSize = size << 1;
//				System.out.println("Image did not fit, incresing size to " + newSize);
				compileTextures(newSize);
				return;
			}
		}
		atlas.finish();

		textures = new Rectangle[atlas.getRects().size()];

		atlas.getRects().forEach((key, rectangle) -> textures[usedTexturesReference.get(key)] = rectangle);
	}

	public static int getNextPowerOfTwo(int value)
	{
		--value;
		value |= value >> 16;
		value |= value >> 8;
		value |= value >> 4;
		value |= value >> 2;
		value |= value >> 1;
		return value + 1;
	}

	public int putTexture(String name)
	{
		try
		{
			if (!usedTexturesReference.containsKey(name))
			{
				if (textures != null)
				{
					throw new IllegalStateException("Textures already compiled");
				}
				if (!new File("game/textures/" + name + ".png").exists())
				{
					throw new FileNotFoundException("Texture '" + name + "' does not exist!");
				}
				//				System.out.println("New texture: '" + name + "' id: " + usedTexturesReference.id());
				int id = usedTexturesReference.size();
				usedTexturesReference.put(name, id);
//				usedTextures.add(usedTexturesReference.get(name), name);
				usedTextures.add(name);
				return id;
			}
			//TODO: probably don't do this
			return usedTexturesReference.get(name);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			System.exit(64);
			return 0;
		}
	}

	public int getTextureId(String name)
	{
		try
		{
			return usedTexturesReference.get(name);
		} catch (Exception ex) {
			System.err.println(name);
			ex.printStackTrace();
		}
		return 0;
	}

	public void clean()
	{
		atlas.clean();
		textures = null;
		usedTextures.clear();
		usedTexturesReference.clear();
	}

	public Rectangle getTexture(int texture)
	{
		return textures[texture];
	}
}
