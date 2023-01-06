package steve6472.sge.gfx.game.blockbench.model;

import org.joml.GeometryUtils;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import steve6472.sge.gfx.game.blockbench.animation.BBAnimation;
import steve6472.sge.gfx.game.stack.Stack;
import steve6472.sge.gfx.game.stack.tess.BBTess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

/**********************
 * Created by steve6472
 * On date: 22.10.2020
 * Project: CaveGame
 *
 ***********************/
public class BBModel
{
	private static final Quaternionf QUAT = new Quaternionf();
	private static final Vector3f NORM = new Vector3f();
	private static final Vector3f V0 = new Vector3f();
	private static final Vector3f V1 = new Vector3f();
	private static final Vector3f V2 = new Vector3f();

	private final HashMap<String, BBAnimation> animations = new HashMap<>();
	private OutlinerElement[] elements;
	private HashMap<String, OutlinerElement> animElements;
	private final String name;
	private final Supplier<OutlinerElement[]> elementCreator;

	BBModel(ModelRepository repository, String name)
	{
		this.name = name;
		this.elementCreator = null;
		reload(repository);
	}

	BBModel(String name, OutlinerElement[] elements)
	{
		this.name = name;
		this.elements = elements;
		this.elementCreator = null;
	}

	/**
	 * Creates static model that can not be reloaded
	 * @param elements elements
	 */
	BBModel(OutlinerElement... elements)
	{
		this(null, elements);
	}

	/**
	 * @param elementCreator Function to create elements
	 */
	BBModel(Supplier<OutlinerElement[]> elementCreator)
	{
		this.name = null;
		this.elementCreator = elementCreator;
		this.elements = elementCreator.get();
	}

	public void reload(ModelRepository repository)
	{
		if (name != null)
		{
			animations.clear();
			elements = Loader.load(repository, animations, name);
			animElements = Loader.assignElements(elements);
			animations.values().forEach(v -> v.setModel(this));
		} else if (elementCreator != null)
		{
			elements = elementCreator.get();
			animElements = Loader.assignElements(elements);
		}
	}

	public void render(Stack stack)
	{
		stack.scale(1f / 16f);
		BBTess tess = (BBTess) stack.getRenderType("blockbench").getTess();
		for (OutlinerElement el : elements)
		{
			render(stack, tess, el);
		}
		stack.scale(16f);
	}

	protected void render(Stack stack, BBTess tess, OutlinerElement el)
	{
		stack.pushMatrix();
		stack.translate(-el.positionX, el.positionY, el.positionZ);
		stack.translate(el.originX, el.originY, el.originZ);
		QUAT.identity();
		QUAT.rotateZ(el.rotationZ);
		QUAT.rotateY(el.rotationY);
		QUAT.rotateX(el.rotationX);
		stack.rotate(QUAT);
		stack.scale(el.scaleX, el.scaleY, el.scaleZ);
		stack.translate(-el.originX, -el.originY, -el.originZ);
		if (el instanceof Outliner outliner)
		{
			for (OutlinerElement child : outliner.children)
			{
				render(stack, tess, child);
			}
		} else if (el instanceof Element element)
		{
			rect(tess, element);
		} else if (el instanceof MeshElement mesh)
		{
			mesh(tess, mesh);
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
			V0.set(tess.getTransformedVector(x + w, y + h, z));
			V1.set(tess.getTransformedVector(x, y + h, z));
			V2.set(tess.getTransformedVector(x, y + h, z + d));

			GeometryUtils.normal(V0, V1, V2, NORM);
			tess.normal(NORM.x, NORM.y, NORM.z);

			vert3(element.up, tess.posUntransformed(V0));
			vert0(element.up, tess.posUntransformed(V1));
			vert1(element.up, tess.posUntransformed(V2));

			vert1(element.up, tess.posUntransformed(V2));
			vert2(element.up, tess.pos(x + w, y + h, z + d));
			vert3(element.up, tess.posUntransformed(V0));
		}

		if (element.down != null)
		{
			V0.set(tess.getTransformedVector(x, y, z + d));
			V1.set(tess.getTransformedVector(x, y, z));
			V2.set(tess.getTransformedVector(x + w, y, z));

			GeometryUtils.normal(V0, V1, V2, NORM);
			tess.normal(NORM.x, NORM.y, NORM.z);

			vert0(element.down, tess.posUntransformed(V0));
			vert1(element.down, tess.posUntransformed(V1));
			vert2(element.down, tess.posUntransformed(V2));

			vert2(element.down, tess.posUntransformed(V2));
			vert3(element.down, tess.pos(x + w, y, z + d));
			vert0(element.down, tess.posUntransformed(V0));
		}


		if (element.north != null)
		{
			V0.set(tess.getTransformedVector(x + w, y + h, z));
			V1.set(tess.getTransformedVector(x + w, y, z));
			V2.set(tess.getTransformedVector(x, y, z));

			GeometryUtils.normal(V0, V1, V2, NORM);
			tess.normal(NORM.x, NORM.y, NORM.z);

			vert0(element.north, tess.posUntransformed(V0));
			vert1(element.north, tess.posUntransformed(V1));
			vert2(element.north, tess.posUntransformed(V2));

			vert2(element.north, tess.posUntransformed(V2));
			vert3(element.north, tess.pos(x, y + h, z));
			vert0(element.north, tess.posUntransformed(V0));
		}


		if (element.east != null)
		{
			V0.set(tess.getTransformedVector(x + w, y + h, z + d));
			V1.set(tess.getTransformedVector(x + w, y, z + d));
			V2.set(tess.getTransformedVector(x + w, y, z));

			GeometryUtils.normal(V0, V1, V2, NORM);
			tess.normal(NORM.x, NORM.y, NORM.z);

			vert0(element.east, tess.posUntransformed(V0));
			vert1(element.east, tess.posUntransformed(V1));
			vert2(element.east, tess.posUntransformed(V2));

			vert2(element.east, tess.posUntransformed(V2));
			vert3(element.east, tess.pos(x + w, y + h, z));
			vert0(element.east, tess.posUntransformed(V0));
		}


		if (element.south != null)
		{
			V0.set(tess.getTransformedVector(x, y + h, z + d));
			V1.set(tess.getTransformedVector(x, y, z + d));
			V2.set(tess.getTransformedVector(x + w, y, z + d));

			GeometryUtils.normal(V0, V1, V2, NORM);
			tess.normal(NORM.x, NORM.y, NORM.z);

			vert0(element.south, tess.posUntransformed(V0));
			vert1(element.south, tess.posUntransformed(V1));
			vert2(element.south, tess.posUntransformed(V2));

			vert2(element.south, tess.posUntransformed(V2));
			vert3(element.south, tess.pos(x + w, y + h, z + d));
			vert0(element.south, tess.posUntransformed(V0));
		}


		if (element.west != null)
		{
			V0.set(tess.getTransformedVector(x, y + h, z));
			V1.set(tess.getTransformedVector(x, y, z));
			V2.set(tess.getTransformedVector(x, y, z + d));

			GeometryUtils.normal(V0, V1, V2, NORM);
			tess.normal(NORM.x, NORM.y, NORM.z);

			vert0(element.west, tess.posUntransformed(V0));
			vert1(element.west, tess.posUntransformed(V1));
			vert2(element.west, tess.posUntransformed(V2));

			vert2(element.west, tess.posUntransformed(V2));
			vert3(element.west, tess.pos(x, y + h, z + d));
			vert0(element.west, tess.posUntransformed(V0));
		}
	}

	private void mesh(BBTess tess, MeshElement element)
	{
		for (MeshElement.Face face : element.getFaces())
		{
			V0.set(tess.getTransformedVector(face.getVerts()[0]));
			V1.set(tess.getTransformedVector(face.getVerts()[1]));
			V2.set(tess.getTransformedVector(face.getVerts()[2]));

			GeometryUtils.normal(V0, V1, V2, NORM);
			tess.normal(NORM.x, NORM.y, NORM.z);

			tess.posUntransformed(V0).uv(face.getUvs()[0].x, face.getUvs()[0].y).endVertex();
			tess.posUntransformed(V1).uv(face.getUvs()[1].x, face.getUvs()[1].y).endVertex();
			tess.posUntransformed(V2).uv(face.getUvs()[2].x, face.getUvs()[2].y).endVertex();
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

	public BBAnimation getAnimation(String name)
	{
		return animations.get(name);
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