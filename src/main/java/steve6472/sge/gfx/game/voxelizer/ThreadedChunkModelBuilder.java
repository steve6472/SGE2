package steve6472.sge.gfx.game.voxelizer;

import org.joml.*;
import org.lwjgl.BufferUtils;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.blockbench.model.Element;
import steve6472.sge.gfx.game.blockbench.model.Outliner;
import steve6472.sge.gfx.game.blockbench.model.OutlinerElement;
import steve6472.sge.main.util.MathUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/8/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class ThreadedChunkModelBuilder extends Thread
{
	private final BBModel[] neighbours = new BBModel[6];
	private final Vector3i[] offsets = {new Vector3i(1, 0, 0), new Vector3i(-1, 0, 0), new Vector3i(0, 1, 0), new Vector3i(0, -1, 0), new Vector3i(0, 0, 1), new Vector3i(0, 0, -1)};
	private final HashMap<BBModel, List<Vector3f>> cache = new HashMap<>();
	private final BlockingQueue<VoxModel> inputQueue;
	private final BlockingQueue<PassData> outputQueue;
	private final Vector3i offset;
	private boolean terminateRequested;
	private int vertexCount = 0;

	private final List<Vector3f> vertices;
	private final List<Vector4f> colors;
	private final List<Vector2f> textures;
	private final List<Vector3f> normal;

	private IModelAccessor modelAccessor;

	public ThreadedChunkModelBuilder()
	{
		inputQueue = new LinkedBlockingQueue<>();
		outputQueue = new LinkedBlockingQueue<>();

		this.vertices = new ArrayList<>();
		this.colors = new ArrayList<>();
		this.textures = new ArrayList<>();
		this.normal = new ArrayList<>();

		offset = new Vector3i();
	}

	private void rebuild(VoxModel model)
	{
		for (int i = 0; i < 16; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				for (int k = 0; k < 16; k++)
				{
					offset.set(i, j, k);

					neighbours[0] = modelAccessor.getModel(i + model.position.x() * 16 + 1, j + model.position.y() * 16, k + model.position.z() * 16);
					neighbours[1] = modelAccessor.getModel(i + model.position.x() * 16 - 1, j + model.position.y() * 16, k + model.position.z() * 16);
					neighbours[2] = modelAccessor.getModel(i + model.position.x() * 16, j + model.position.y() * 16 + 1, k + model.position.z() * 16);
					neighbours[3] = modelAccessor.getModel(i + model.position.x() * 16, j + model.position.y() * 16 - 1, k + model.position.z() * 16);
					neighbours[4] = modelAccessor.getModel(i + model.position.x() * 16, j + model.position.y() * 16, k + model.position.z() * 16 + 1);
					neighbours[5] = modelAccessor.getModel(i + model.position.x() * 16, j + model.position.y() * 16, k + model.position.z() * 16 - 1);

					for (BBModel neighbour : neighbours)
					{
						addToCache(neighbour);
					}

					BBModel bbModel = modelAccessor.getModel(i + model.position.x() * 16, j + model.position.y() * 16, k + model.position
						.z() * 16);

					addToCache(bbModel);

					build(bbModel);

					Arrays.fill(neighbours, null);
				}
			}
		}

		try
		{
			outputQueue.put(new PassData(model.getLayer(), toFloatBuffer3(vertices), toFloatBuffer4(colors), toFloatBuffer2(textures), toFloatBuffer3(normal), vertexCount / 3, model.position.x(), model.position.y(), model.position.z()));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		vertices.clear();
		colors.clear();
		textures.clear();
		normal.clear();
		vertexCount = 0;
	}

	/*
	 * Cache
	 */

	/**
	 * Probably bad
	 */
	public void clearCache()
	{
		cache.clear();
	}

	private void addToCache(BBModel model)
	{
		if (model == null || cache.containsKey(model))
			return;

		List<Vector3f> vertices = new ArrayList<>();
		renderCache(model, vertices, new Matrix4fStack(16));
		cache.put(model, vertices);
	}

	public void renderCache(BBModel model, List<Vector3f> vertices, Matrix4fStack stack)
	{
		stack.scale(1f / 16f);
		for (OutlinerElement el : model.getElements())
		{
			if (el instanceof Outliner outliner)
			{
				renderCache(stack, vertices, outliner);
			} else if (el instanceof Element element)
			{
				cache(stack, element, vertices);
			}
		}
	}

	protected void renderCache(Matrix4fStack stack, List<Vector3f> vertices, OutlinerElement el)
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
				renderCache(stack, vertices, child);
			}
		} else if (el instanceof Element element)
		{
			cache(stack, element, vertices);
		}
		stack.popMatrix();
	}

	protected void cache(Matrix4fStack stack, Element element, List<Vector3f> vertices)
	{
		float x = element.fromX;
		float y = element.fromY;
		float z = element.fromZ;

		float w = element.toX - x;
		float h = element.toY - y;
		float d = element.toZ - z;

		if (element.up != null)
		{
			stack.transformPosition(x + w, y + h, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x, y + h, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x, y + h, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x + w, y + h, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
		}

		if (element.down != null)
		{
			stack.transformPosition(x, y, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x, y, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x + w, y, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x + w, y, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
		}

		if (element.north != null)
		{
			stack.transformPosition(x + w, y + h, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x + w, y, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x, y, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x, y + h, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
		}

		if (element.east != null)
		{
			stack.transformPosition(x + w, y + h, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x + w, y, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x + w, y, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x + w, y + h, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
		}

		if (element.south != null)
		{
			stack.transformPosition(x, y + h, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x, y, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x + w, y, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x + w, y + h, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
		}

		if (element.west != null)
		{
			stack.transformPosition(x, y + h, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x, y, z, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x, y, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
			stack.transformPosition(x, y + h, z + d, TEMP_VECTOR);
			vertices.add(new Vector3f(TEMP_VECTOR));
		}

	}

	/*
	 * Model builder functions
	 */

	private boolean buildFace(int index)
	{
		BBModel n = neighbours[index];
		Vector3i o = offsets[index];

		List<Vector3f> vertices = cache.get(n);
		if (vertices != null && !vertices.isEmpty())
		{
			for (int j = 0; j < vertices.size() / 4; j++)
			{
				v0.set(vertices.get(j * 4)).add(o.x, o.y, o.z);
				v1.set(vertices.get(j * 4 + 1)).add(o.x, o.y, o.z);
				v2.set(vertices.get(j * 4 + 2)).add(o.x, o.y, o.z);
				v3.set(vertices.get(j * 4 + 3)).add(o.x, o.y, o.z);

				Vector3f norm = new Vector3f();
				Vector3f norm_ = new Vector3f();
				GeometryUtils.normal(v0, v1, v2, norm);
				GeometryUtils.normal(V0, V1, V2, norm_);

				if (norm.add(norm_).absolute().length() > 0.000001f)
					continue;

				if (isPointInsideRectangle(v0, v1, v2, v3, V0) && isPointInsideRectangle(v0, v1, v2, v3, V1) && isPointInsideRectangle(v0, v1, v2, v3, V2) && isPointInsideRectangle(v0, v1, v2, v3, V3))
				{
					return false;
				}
			}
		}
		return true;
	}

	private static final Vector3f AB = new Vector3f();
	private static final Vector3f AC = new Vector3f();

	private static float getRectArea(Vector3f A, Vector3f B, Vector3f C)
	{
		B.sub(A, AB);
		C.sub(A, AC);
		return AB.cross(AC).length();
	}

	private static float getTriArea(Vector3f A, Vector3f B, Vector3f C)
	{
		return getRectArea(A, B, C) / 2.0f;
	}

	private static boolean isPointInsideRectangle(Vector3f A, Vector3f B, Vector3f C, Vector3f D, Vector3f P)
	{
		float rectArea = getRectArea(A, B, C);

		float t0 = getTriArea(A, B, P);
		float t1 = getTriArea(B, C, P);
		float t2 = getTriArea(C, D, P);
		float t3 = getTriArea(D, A, P);
		return MathUtil.compareFloat(rectArea, t0 + t1 + t2 + t3, 0.00001f);
	}

	private void rect(Element element)
	{
		float x = element.fromX;
		float y = element.fromY;
		float z = element.fromZ;

		float w = element.toX - x;
		float h = element.toY - y;
		float d = element.toZ - z;

		if (element.up != null)
		{
			normal(0, 1, 0);

			STACK.transformPosition(x + w, y + h, z, V0);
			STACK.transformPosition(x, y + h, z, V1);
			STACK.transformPosition(x, y + h, z + d, V2);
			STACK.transformPosition(x + w, y + h, z + d, V3);

			if (buildFace(2))
			{
				vert0(element.up, pos(V0));
				vert1(element.up, pos(V1));
				vert2(element.up, pos(V2));

				vert2(element.up, pos(V2));
				vert3(element.up, pos(V3));
				vert0(element.up, pos(V0));
			}
		}

		if (element.down != null)
		{
			normal(0, -1, 0);

			STACK.transformPosition(x, y, z + d, V0);
			STACK.transformPosition(x, y, z, V1);
			STACK.transformPosition(x + w, y, z, V2);
			STACK.transformPosition(x + w, y, z + d, V3);

			if (buildFace(3))
			{
				vert0(element.down, pos(V0));
				vert1(element.down, pos(V1));
				vert2(element.down, pos(V2));

				vert2(element.down, pos(V2));
				vert3(element.down, pos(V3));
				vert0(element.down, pos(V0));
			}
		}


		if (element.north != null)
		{
			normal(1, 0, 0);

			STACK.transformPosition(x + w, y + h, z, V0);
			STACK.transformPosition(x + w, y, z, V1);
			STACK.transformPosition(x, y, z, V2);
			STACK.transformPosition(x, y + h, z, V3);

			if (buildFace(5))
			{
				vert0(element.north, pos(V0));
				vert1(element.north, pos(V1));
				vert2(element.north, pos(V2));

				vert2(element.north, pos(V2));
				vert3(element.north, pos(V3));
				vert0(element.north, pos(V0));
			}
		}


		if (element.east != null)
		{
			normal(0, 0, 1);
			STACK.transformPosition(x + w, y + h, z + d, V0);
			STACK.transformPosition(x + w, y, z + d, V1);
			STACK.transformPosition(x + w, y, z, V2);
			STACK.transformPosition(x + w, y + h, z, V3);

			if (buildFace(0))
			{
				vert0(element.east, pos(V0));
				vert1(element.east, pos(V1));
				vert2(element.east, pos(V2));

				vert2(element.east, pos(V2));
				vert3(element.east, pos(V3));
				vert0(element.east, pos(V0));
			}
		}


		if (element.south != null)
		{
			normal(-1, 0, 0);

			STACK.transformPosition(x, y + h, z + d, V0);
			STACK.transformPosition(x, y, z + d, V1);
			STACK.transformPosition(x + w, y, z + d, V2);
			STACK.transformPosition(x + w, y + h, z + d, V3);

			if (buildFace(4))
			{
				vert0(element.south, pos(V0));
				vert1(element.south, pos(V1));
				vert2(element.south, pos(V2));

				vert2(element.south, pos(V2));
				vert3(element.south, pos(V3));
				vert0(element.south, pos(V0));
			}
		}


		if (element.west != null)
		{
			normal(0, 0, -1);

			STACK.transformPosition(x, y + h, z, V0);
			STACK.transformPosition(x, y, z, V1);
			STACK.transformPosition(x, y, z + d, V2);
			STACK.transformPosition(x, y + h, z + d, V3);

			if (buildFace(1))
			{
				vert0(element.west, pos(V0));
				vert1(element.west, pos(V1));
				vert2(element.west, pos(V2));

				vert2(element.west, pos(V2));
				vert3(element.west, pos(V3));
				vert0(element.west, pos(V0));
			}
		}
	}

	private final Vector3f TEMP_VECTOR = new Vector3f();
	private final Vector3f V0 = new Vector3f();
	private final Vector3f V1 = new Vector3f();
	private final Vector3f V2 = new Vector3f();
	private final Vector3f V3 = new Vector3f();
	private final Vector3f v0 = new Vector3f();
	private final Vector3f v1 = new Vector3f();
	private final Vector3f v2 = new Vector3f();
	private final Vector3f v3 = new Vector3f();
	private final Matrix4fStack STACK = new Matrix4fStack(16);
	private final Matrix4f MATRIX4F = new Matrix4f();
	private final Vector3f builderPos = new Vector3f();
	private final Vector3f builderNormal = new Vector3f();
	private final Vector4f builderColor = new Vector4f(1, 1, 1, 1);
	private final Vector2f builderTexture = new Vector2f();

	private ThreadedChunkModelBuilder pos(Vector3f v)
	{
		builderPos.set(v).add(offset.x, offset.y, offset.z);
		return this;
	}

	private void normal(float nx, float ny, float nz)
	{
		MATRIX4F.identity();
		MATRIX4F.transformPosition(nx, ny, nz, TEMP_VECTOR);
		TEMP_VECTOR.normalize();
		builderNormal.set(TEMP_VECTOR);
	}

	private void uv(float u, float v)
	{
		builderTexture.set(u, v);
	}

	private void color(float r, float g, float b, float a)
	{
		builderColor.set(r, g, b, a);
	}

	private void endVertex()
	{
		vertices.add(new Vector3f(builderPos));
		colors.add(new Vector4f(builderColor));
		textures.add(new Vector2f(builderTexture));
		normal.add(new Vector3f(builderNormal));
		vertexCount++;
	}

	private void build(BBModel model)
	{
		STACK.identity();
		//todo : move -8, 0, -8
//		STACK.translate(offset.x, offset.y, offset.z);
		STACK.scale(1f / 16f);
		color(1, 1, 1, 1);
		for (OutlinerElement el : model.getElements())
		{
			if (el instanceof Outliner outliner)
			{
				build(outliner);
			} else if (el instanceof Element element)
			{
				rect(element);
			}
		}
	}

	private void build(OutlinerElement el)
	{
		STACK.pushMatrix();
		STACK.translate(-el.positionX, el.positionY, el.positionZ);
		STACK.translate(el.originX, el.originY, el.originZ);
		STACK.rotateXYZ(el.rotationX, el.rotationY, el.rotationZ);
		STACK.translate(-el.originX, -el.originY, -el.originZ);
		STACK.scale(el.scaleX, el.scaleY, el.scaleZ);
		if (el instanceof Outliner outliner)
		{
			for (OutlinerElement child : outliner.children)
			{
				build(child);
			}
		} else if (el instanceof Element element)
		{
			rect(element);
		}
		STACK.popMatrix();
	}

	private void vert0(Element.Face face, ThreadedChunkModelBuilder useless)
	{
		switch (face.getRotation())
		{
			case 0 -> uv(face.getU0(), face.getV0());
			case 1 -> uv(face.getU0(), face.getV1());
			case 2 -> uv(face.getU1(), face.getV1());
			case 3 -> uv(face.getU1(), face.getV0());
		}
		endVertex();
	}

	private void vert1(Element.Face face, ThreadedChunkModelBuilder useless)
	{
		switch (face.getRotation())
		{
			case 0 -> uv(face.getU0(), face.getV1());
			case 1 -> uv(face.getU1(), face.getV1());
			case 2 -> uv(face.getU1(), face.getV0());
			case 3 -> uv(face.getU0(), face.getV0());
		}
		endVertex();
	}

	private void vert2(Element.Face face, ThreadedChunkModelBuilder useless)
	{
		switch (face.getRotation())
		{
			case 0 -> uv(face.getU1(), face.getV1());
			case 1 -> uv(face.getU1(), face.getV0());
			case 2 -> uv(face.getU0(), face.getV0());
			case 3 -> uv(face.getU0(), face.getV1());
		}
		endVertex();
	}

	private void vert3(Element.Face face, ThreadedChunkModelBuilder useless)
	{
		switch (face.getRotation())
		{
			case 0 -> uv(face.getU1(), face.getV0());
			case 1 -> uv(face.getU0(), face.getV0());
			case 2 -> uv(face.getU0(), face.getV1());
			case 3 -> uv(face.getU1(), face.getV1());
		}
		endVertex();
	}

	/*
	 *
	 */

	@Override
	public void run()
	{
		while (!terminateRequested)
		{
			try
			{
				VoxModel subChunk = inputQueue.take();
				rebuild(subChunk);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
				terminate();
			}
		}
	}

	public void addToQueue(VoxModel model)
	{
		try
		{
			if (model.rebuildInProgress)
				return;
			model.rebuildInProgress = true;
			if (!inputQueue.contains(model))
				inputQueue.put(model);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
			terminate();
		}
	}

	public boolean canTake()
	{
		return !outputQueue.isEmpty();
	}

	public PassData take()
	{
		try
		{
			return outputQueue.take();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void terminate()
	{
		terminateRequested = true;
	}

	public void setModelAccessor(IModelAccessor modelAccessor)
	{
		this.modelAccessor = modelAccessor;
	}

	public IModelAccessor getModelAccessor()
	{
		return modelAccessor;
	}

	public static FloatBuffer toFloatBuffer3(List<Vector3f> arr)
	{
		FloatBuffer buff = BufferUtils.createFloatBuffer(arr.size() * 3);

		for (Vector3f i : arr)
		{
			buff.put(i.x);
			buff.put(i.y);
			buff.put(i.z);
		}

		buff.flip();
		return buff;
	}

	public static FloatBuffer toFloatBuffer4(List<Vector4f> arr)
	{
		FloatBuffer buff = BufferUtils.createFloatBuffer(arr.size() * 4);

		for (Vector4f i : arr)
		{
			buff.put(i.x);
			buff.put(i.y);
			buff.put(i.z);
			buff.put(i.w);
		}

		buff.flip();
		return buff;
	}

	public static FloatBuffer toFloatBuffer2(List<Vector2f> arr)
	{
		FloatBuffer buff = BufferUtils.createFloatBuffer(arr.size() * 2);

		for (Vector2f i : arr)
		{
			buff.put(i.x);
			buff.put(i.y);
		}

		buff.flip();
		return buff;
	}
}
