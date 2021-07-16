package steve6472.sge.gfx.game.blockbench.model;

import steve6472.sge.gfx.game.blockbench.ModelTextureAtlas;
import steve6472.sge.gfx.game.stack.tess.BBTess;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.voxelizer.VoxLayers;

import java.util.Arrays;
import java.util.HashMap;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 22.10.2020
 * Project: CaveGame
 *
 ***********************/
public class BBModel
{
	private OutlinerElement[] elements;
	private HashMap<String, OutlinerElement> animElements;
	private final String name;

	public BBModel(ModelTextureAtlas modelTextureAtlas, String name)
	{
		this.name = name;
		reload(modelTextureAtlas);
	}

	public BBModel(ModelTextureAtlas modelTextureAtlas, String name, VoxLayers layers)
	{
		this.name = name;
		reload(modelTextureAtlas, layers);
	}

	public BBModel(String name, OutlinerElement[] elements)
	{
		this.name = name;
		this.elements = elements;
	}

	public BBModel(OutlinerElement[] elements)
	{
		this(null, elements);
	}

	public void reload(ModelTextureAtlas modelTextureAtlas)
	{
		if (name != null)
		{
			elements = Loader.load(modelTextureAtlas, name);
			animElements = Loader.assignElements(elements);
		}
	}

	public void reload(ModelTextureAtlas modelTextureAtlas, VoxLayers layers)
	{
		if (name != null)
		{
			elements = Loader.load(modelTextureAtlas, name, layers);
			animElements = Loader.assignElements(elements);
		}
	}

	public void render(Stack stack)
	{
		stack.scale(1f / 16f);
		BBTess tess = (BBTess) stack.getRenderType("blockbench").getTess();
		for (OutlinerElement el : elements)
		{
			if (el instanceof Outliner outliner)
			{
				render(stack, tess, outliner);
			} else if (el instanceof Element element)
			{
				rect(tess, element);
			}
		}
		stack.scale(16f);
	}

	protected void render(Stack stack, BBTess tess, OutlinerElement el)
	{
		stack.pushMatrix();
		stack.translate(-el.positionX, el.positionY, el.positionZ);
		stack.translate(el.originX, el.originY, el.originZ);
		stack.rotateXYZ(el.rotationX, el.rotationY, el.rotationZ);
		stack.translate(-el.originX, -el.originY, -el.originZ);
		stack.scale(el.scaleX, el.scaleY, el.scaleZ);
		if (el instanceof Outliner outliner)
		{
			for (OutlinerElement child : outliner.children)
			{
				render(stack, tess, child);
			}
		} else if (el instanceof Element element)
		{
			rect(tess, element);
		}
		stack.popMatrix();
	}

	protected void vert0(Element.Face face, BBTess tess)
	{
		switch (face.getRotation())
		{
			case 0 -> tess.uv(face.getU0(), face.getV0());
			case 1 -> tess.uv(face.getU0(), face.getV1());
			case 2 -> tess.uv(face.getU1(), face.getV1());
			case 3 -> tess.uv(face.getU1(), face.getV0());
		}
		tess.endVertex();
	}

	protected void vert1(Element.Face face, BBTess tess)
	{
		switch (face.getRotation())
		{
			case 0 -> tess.uv(face.getU0(), face.getV1());
			case 1 -> tess.uv(face.getU1(), face.getV1());
			case 2 -> tess.uv(face.getU1(), face.getV0());
			case 3 -> tess.uv(face.getU0(), face.getV0());
		}
		tess.endVertex();
	}

	protected void vert2(Element.Face face, BBTess tess)
	{
		switch (face.getRotation())
		{
			case 0 -> tess.uv(face.getU1(), face.getV1());
			case 1 -> tess.uv(face.getU1(), face.getV0());
			case 2 -> tess.uv(face.getU0(), face.getV0());
			case 3 -> tess.uv(face.getU0(), face.getV1());
		}
		tess.endVertex();
	}

	protected void vert3(Element.Face face, BBTess tess)
	{
		switch (face.getRotation())
		{
			case 0 -> tess.uv(face.getU1(), face.getV0());
			case 1 -> tess.uv(face.getU0(), face.getV0());
			case 2 -> tess.uv(face.getU0(), face.getV1());
			case 3 -> tess.uv(face.getU1(), face.getV1());
		}
		tess.endVertex();
	}

	protected void rect(BBTess tess, Element element)
	{
		float x = element.fromX;
		float y = element.fromY;
		float z = element.fromZ;

		float w = element.toX - x;
		float h = element.toY - y;
		float d = element.toZ - z;

		if (element.up != null)
		{
			tess.normal(0, 1, 0);

			vert3(element.up, tess.pos(x + w, y + h, z));
			vert0(element.up, tess.pos(x, y + h, z));
			vert1(element.up, tess.pos(x, y + h, z + d));

			vert1(element.up, tess.pos(x, y + h, z + d));
			vert2(element.up, tess.pos(x + w, y + h, z + d));
			vert3(element.up, tess.pos(x + w, y + h, z));
		}

		if (element.down != null)
		{
			tess.normal(0, -1, 0);

			vert0(element.down, tess.pos(x, y, z + d));
			vert1(element.down, tess.pos(x, y, z));
			vert2(element.down, tess.pos(x + w, y, z));

			vert2(element.down, tess.pos(x + w, y, z));
			vert3(element.down, tess.pos(x + w, y, z + d));
			vert0(element.down, tess.pos(x, y, z + d));
		}


		if (element.north != null)
		{
			tess.normal(1, 0, 0);

			vert0(element.north, tess.pos(x + w, y + h, z));
			vert1(element.north, tess.pos(x + w, y, z));
			vert2(element.north, tess.pos(x, y, z));

			vert2(element.north, tess.pos(x, y, z));
			vert3(element.north, tess.pos(x, y + h, z));
			vert0(element.north, tess.pos(x + w, y + h, z));
		}


		if (element.east != null)
		{
			tess.normal(0, 0, 1);

			vert0(element.east, tess.pos(x + w, y + h, z + d));
			vert1(element.east, tess.pos(x + w, y, z + d));
			vert2(element.east, tess.pos(x + w, y, z));

			vert2(element.east, tess.pos(x + w, y, z));
			vert3(element.east, tess.pos(x + w, y + h, z));
			vert0(element.east, tess.pos(x + w, y + h, z + d));
		}


		if (element.south != null)
		{
			tess.normal(-1, 0, 0);

			vert0(element.south, tess.pos(x, y + h, z + d));
			vert1(element.south, tess.pos(x, y, z + d));
			vert2(element.south, tess.pos(x + w, y, z + d));

			vert2(element.south, tess.pos(x + w, y, z + d));
			vert3(element.south, tess.pos(x + w, y + h, z + d));
			vert0(element.south, tess.pos(x, y + h, z + d));
		}


		if (element.west != null)
		{
			tess.normal(0, 0, -1);

			vert0(element.west, tess.pos(x, y + h, z));
			vert1(element.west, tess.pos(x, y, z));
			vert2(element.west, tess.pos(x, y, z + d));

			vert2(element.west, tess.pos(x, y, z + d));
			vert3(element.west, tess.pos(x, y + h, z + d));
			vert0(element.west, tess.pos(x, y + h, z));
		}

	}

	@Override
	public String toString()
	{
		return "Model{" + "elements=" + Arrays.toString(elements) + ", animElements=" + animElements + ", name='" + name + '\'' + '}';
	}

	public String getName()
	{
		return name;
	}

	public void setElements(OutlinerElement[] elements)
	{
		this.elements = elements;
	}

	public void setAnimElements(HashMap<String, OutlinerElement> animElements)
	{
		this.animElements = animElements;
	}

	public OutlinerElement[] getElements()
	{
		return elements;
	}

	public HashMap<String, OutlinerElement> getAnimElements()
	{
		return animElements;
	}
}