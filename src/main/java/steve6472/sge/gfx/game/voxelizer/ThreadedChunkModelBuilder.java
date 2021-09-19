package steve6472.sge.gfx.game.voxelizer;

import org.joml.*;
import org.lwjgl.BufferUtils;
import steve6472.sge.gfx.game.blockbench.model.*;
import steve6472.sge.main.util.MathUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
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
	public boolean DEBUG = false;

	private final Builder center = new Builder();
	private final Builder[] neighbours = new Builder[6];
	private final Vector3i[] offsets = {new Vector3i(1, 0, 0), new Vector3i(-1, 0, 0), new Vector3i(0, 1, 0), new Vector3i(0, -1, 0), new Vector3i(0, 0, 1), new Vector3i(0, 0, -1)};
	private final HashMap<BBModel, List<Vector3f>> modelCache = new HashMap<>();
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
		for (int i = 0; i < 6; i++)
		{
			neighbours[i] = new Builder();
		}
	}

	private void loadNeightbour(int x, int y, int z, int index)
	{
		Matrix4f m = new Matrix4f().translate(offsets[index].x, offsets[index].y, offsets[index].z).scale(1f / 16f);
//		neighbours[index].transform().identity();
//		neighbours[index].transform().translate(offsets[index].x, offsets[index].y, offsets[index].z);
//		neighbours[index].transform().scale(1f / 16f);
		modelAccessor.loadElements(neighbours[index], x, y, z);
		for (Builder.BuilderData datum : neighbours[index].getData())
		{
			m.mul(datum.transform(), datum.transform());
			datum.transform().scale(16f);
		}
//		neighbours[index].transform().scale(16f);
	}

	public PassData rebuild_(VoxModel model)
	{
		for (int i = 0; i < 16; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				for (int k = 0; k < 16; k++)
				{
					offset.set(i, j, k);

					modelAccessor.loadElements(center, i + model.position.x() * 16, j + model.position.y() * 16, k + model.position.z() * 16);

					if (center.isEmpty())
						continue;

					loadNeightbour(i + model.position.x() * 16 + 1, j + model.position.y() * 16, k + model.position.z() * 16, 0);
					loadNeightbour(i + model.position.x() * 16 - 1, j + model.position.y() * 16, k + model.position.z() * 16, 1);
					loadNeightbour(i + model.position.x() * 16, j + model.position.y() * 16 + 1, k + model.position.z() * 16, 2);
					loadNeightbour(i + model.position.x() * 16, j + model.position.y() * 16 - 1, k + model.position.z() * 16, 3);
					loadNeightbour(i + model.position.x() * 16, j + model.position.y() * 16, k + model.position.z() * 16 + 1, 4);
					loadNeightbour(i + model.position.x() * 16, j + model.position.y() * 16, k + model.position.z() * 16 - 1, 5);

					for (Builder neighbour : neighbours)
					{
						for (Builder.BuilderData neighbourDatum : neighbour.getData())
						{
							neighbourDatum.getModels().forEach(this::addToCache);
						}
					}

					int size = center.getData().size();
					for (int i1 = 0; i1 < size; i1++)
					{

						for (BBModel bbModel : center.getModels())
						{
							addToCache(bbModel);
							build(bbModel, i + model.position.x() * 16, j + model.position.y() * 16, k + model.position.z() * 16, model.layer);
						}

						STACK.clear();
						STACK.scale(1f / 16f);
						STACK.mul(center.transform());
						STACK.translate(0, -8f, 0);
						for (Element element : center.getElements())
						{
							color(center.getColor().x, center.getColor().y, center.getColor().z, center.getColor().w);
							rect(element, model.layer);
						}

						center.removeLast();
					}

					for (Builder neighbour : neighbours)
					{
						neighbour.clear();
					}

					center.clear();

					if (DEBUG)
						System.out.println("");
				}
			}
		}


		PassData passData = new PassData(model.getLayer(), toFloatBuffer3(vertices), toFloatBuffer4(colors), toFloatBuffer2(textures), toFloatBuffer3(normal), vertexCount / 3, model.position
			.x(), model.position.y(), model.position.z());
		clear();
		return passData;
	}

	private void rebuild(VoxModel model)
	{
		PassData data = rebuild_(model);

		try
		{
			outputQueue.put(data);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		clear();
	}

	public void clear()
	{
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
		modelCache.clear();
	}

	private void addToCache(BBModel model)
	{
		if (model == null || modelCache.containsKey(model))
			return;

		List<Vector3f> vertices = new ArrayList<>();
		renderCache(model, vertices, new Matrix4fStack(16));
		modelCache.put(model, vertices);
	}

	public void renderCache(BBModel model, List<Vector3f> vertices, Matrix4fStack stack)
	{
		stack.translate(0, -0.5f, 0);
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

		if (element.up != null && !ModelRepository.getBoolProperty(element.up, ModelRepository.DISABLE_OTHER_CULL))
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

		if (element.down != null && !ModelRepository.getBoolProperty(element.down, ModelRepository.DISABLE_OTHER_CULL))
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

		if (element.north != null && !ModelRepository.getBoolProperty(element.north, ModelRepository.DISABLE_OTHER_CULL))
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

		if (element.east != null && !ModelRepository.getBoolProperty(element.east, ModelRepository.DISABLE_OTHER_CULL))
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

		if (element.south != null && !ModelRepository.getBoolProperty(element.south, ModelRepository.DISABLE_OTHER_CULL))
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

		if (element.west != null && !ModelRepository.getBoolProperty(element.west, ModelRepository.DISABLE_OTHER_CULL))
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

	protected int elementCache(Matrix4f transformations, Element element, List<Vector3f> vertices)
	{
		float x = element.fromX;
		float y = element.fromY;
		float z = element.fromZ;

		float w = element.toX - x;
		float h = element.toY - y;
		float d = element.toZ - z;

		int s = -1;

		if (element.up != null && !ModelRepository.getBoolProperty(element.up, ModelRepository.DISABLE_OTHER_CULL))
		{
			transformations.transformPosition(x + w, y + h, z, vertices.get(++s));
			transformations.transformPosition(x, y + h, z, vertices.get(++s));
			transformations.transformPosition(x, y + h, z + d, vertices.get(++s));
			transformations.transformPosition(x + w, y + h, z + d, vertices.get(++s));
		}

		if (element.down != null && !ModelRepository.getBoolProperty(element.down, ModelRepository.DISABLE_OTHER_CULL))
		{
			transformations.transformPosition(x, y, z + d, vertices.get(++s));
			transformations.transformPosition(x, y, z, vertices.get(++s));
			transformations.transformPosition(x + w, y, z, vertices.get(++s));
			transformations.transformPosition(x + w, y, z + d, vertices.get(++s));
		}

		if (element.north != null && !ModelRepository.getBoolProperty(element.north, ModelRepository.DISABLE_OTHER_CULL))
		{
			transformations.transformPosition(x + w, y + h, z, vertices.get(++s));
			transformations.transformPosition(x + w, y, z, vertices.get(++s));
			transformations.transformPosition(x, y, z, vertices.get(++s));
			transformations.transformPosition(x, y + h, z, vertices.get(++s));
		}

		if (element.east != null && !ModelRepository.getBoolProperty(element.east, ModelRepository.DISABLE_OTHER_CULL))
		{
			transformations.transformPosition(x + w, y + h, z + d, vertices.get(++s));
			transformations.transformPosition(x + w, y, z + d, vertices.get(++s));
			transformations.transformPosition(x + w, y, z, vertices.get(++s));
			transformations.transformPosition(x + w, y + h, z, vertices.get(++s));
		}

		if (element.south != null && !ModelRepository.getBoolProperty(element.south, ModelRepository.DISABLE_OTHER_CULL))
		{
			transformations.transformPosition(x, y + h, z + d, vertices.get(++s));
			transformations.transformPosition(x, y, z + d, vertices.get(++s));
			transformations.transformPosition(x + w, y, z + d, vertices.get(++s));
			transformations.transformPosition(x + w, y + h, z + d, vertices.get(++s));
		}

		if (element.west != null && !ModelRepository.getBoolProperty(element.west, ModelRepository.DISABLE_OTHER_CULL))
		{
			transformations.transformPosition(x, y + h, z, vertices.get(++s));
			transformations.transformPosition(x, y, z, vertices.get(++s));
			transformations.transformPosition(x, y, z + d, vertices.get(++s));
			transformations.transformPosition(x, y + h, z + d, vertices.get(++s));
		}

		return s;
	}

	/*
	 * Model builder functions
	 */

	private boolean buildFace()
	{
		for (int i = 0; i < 6; i++)
		{
			Builder n = neighbours[i];

			for (int j = 0; j < n.getData().size(); j++)
			{
				Builder.BuilderData builderData = n.getData().get(j);
				Matrix4f transformations = builderData.transform();

				for (Element element : builderData.getElements())
				{
					List<Vector3f> vertices = ELEMENT_VERTICES;
					int count = elementCache(MATRIX4F.set(builderData.transform()).scale(1f / 16f).translate(0, -24, 0), element, vertices);
					if (count == 0)
						continue;

					for (int k = 0; k < count / 4; k++)
					{
						v0.set(vertices.get(k * 4));
						v1.set(vertices.get(k * 4 + 1));
						v2.set(vertices.get(k * 4 + 2));

						transformations.transformPosition(v0);
						transformations.transformPosition(v1);
						transformations.transformPosition(v2);

						//					PointTess point = (PointTess) VoxRenderTest.stack.getRenderType("point").getTess();
						//					point.color(0xffffffff);
						//					point.pos(v0.x, v0.y, v0.z).endVertex();
						//					point.pos(v1.x, v1.y, v1.z).endVertex();
						//					point.pos(v2.x, v2.y, v2.z).endVertex();

						GeometryUtils.normal(v0, v1, v2, NORMAL);
						GeometryUtils.normal(V0, V1, V2, NORMAL_);

						if (NORMAL.add(NORMAL_).absolute().length() > 0.000001f)
							continue;

						v3.set(vertices.get(k * 4 + 3));
						transformations.transformPosition(v3);

						if (DEBUG)
						{
							System.out.printf("v0: %s, v1: %s, v2: %s, v3: %s \n", v0, v1, v2, v3);
							System.out.printf("V0: %s, V1: %s, V2: %s, V3: %s \n", V0, V1, V2, V3);
						}

						if (isPointInsideRectangle(v0, v1, v2, v3, V0) && isPointInsideRectangle(v0, v1, v2, v3, V1) && isPointInsideRectangle(v0, v1, v2, v3, V2) && isPointInsideRectangle(v0, v1, v2, v3, V3))
						{
							return false;
						}
					}
				}

				for (BBModel model : builderData.getModels())
				{
					List<Vector3f> vertices = modelCache.get(model);
					if (vertices != null && !vertices.isEmpty())
					{
						if (DEBUG)
							System.out.println(offset);

						for (int k = 0; k < vertices.size() / 4; k++)
						{
							v0.set(vertices.get(k * 4));
							v1.set(vertices.get(k * 4 + 1));
							v2.set(vertices.get(k * 4 + 2));

							transformations.transformPosition(v0);
							transformations.transformPosition(v1);
							transformations.transformPosition(v2);

							//						if (offset.equals(0, 0, 0))
							//						{
							//							PointTess point = (PointTess) VoxRenderTest.stack.getRenderType("point").getTess();
							//							point.color(0xff00ffff);
							//							point.pos(v0.x, v0.y, v0.z).endVertex();
							//							point.pos(v1.x, v1.y, v1.z).endVertex();
							//							point.pos(v2.x, v2.y, v2.z).endVertex();
							//						}


							GeometryUtils.normal(v0, v1, v2, NORMAL);
							GeometryUtils.normal(V0, V1, V2, NORMAL_);

							if (NORMAL.add(NORMAL_).absolute().length() > 0.000001f)
								continue;

							v3.set(vertices.get(k * 4 + 3));
							transformations.transformPosition(v3);

							if (DEBUG)
							{
								System.out.printf("v0: %s, v1: %s, v2: %s, v3: %s \n", v0, v1, v2, v3);
								System.out.printf("V0: %s, V1: %s, V2: %s, V3: %s \n", V0, V1, V2, V3);
							}

							if (isPointInsideRectangle(v0, v1, v2, v3, V0) && isPointInsideRectangle(v0, v1, v2, v3, V1) && isPointInsideRectangle(v0, v1, v2, v3, V2) && isPointInsideRectangle(v0, v1, v2, v3, V3))
							{
								return false;
							}
						}
					}
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

	private void rect(Element element, VoxLayer layer)
	{
		float x = element.fromX;
		float y = element.fromY;
		float z = element.fromZ;

		float w = element.toX - x;
		float h = element.toY - y;
		float d = element.toZ - z;

		if (DEBUG)
			System.out.println(element);

		if (element.up != null && element.up.getProperties().get(ModelRepository.LAYER_PROPERTY).equals(layer))
		{
			if (DEBUG)
				System.out.println("up");
//			normal(0, 1, 0);

			STACK.transformPosition(x + w, y + h, z, V0);
			STACK.transformPosition(x, y + h, z, V1);
			STACK.transformPosition(x, y + h, z + d, V2);
			STACK.transformPosition(x + w, y + h, z + d, V3);

			GeometryUtils.normal(V0, V1, V2, NORMAL);

			builderNormal.set(NORMAL);

			if (buildFace())
			{
				if (DEBUG)
					System.out.println("Building up " + V0 + " " + V1 + " " + V2);
				vert3(element.up, pos(V0));
				vert0(element.up, pos(V1));
				vert1(element.up, pos(V2));

				vert1(element.up, pos(V2));
				vert2(element.up, pos(V3));
				vert3(element.up, pos(V0));
			}
		}

		if (element.down != null && element.down.getProperties().get(ModelRepository.LAYER_PROPERTY).equals(layer))
		{
			if (DEBUG)
				System.out.println("down");
//			normal(0, -1, 0);

			STACK.transformPosition(x, y, z + d, V0);
			STACK.transformPosition(x, y, z, V1);
			STACK.transformPosition(x + w, y, z, V2);
			STACK.transformPosition(x + w, y, z + d, V3);

			GeometryUtils.normal(V0, V1, V2, NORMAL);

			builderNormal.set(NORMAL);

			if (buildFace())
			{
				vert0(element.down, pos(V0));
				vert1(element.down, pos(V1));
				vert2(element.down, pos(V2));

				vert2(element.down, pos(V2));
				vert3(element.down, pos(V3));
				vert0(element.down, pos(V0));
			}
		}


		if (element.north != null && element.north.getProperties().get(ModelRepository.LAYER_PROPERTY).equals(layer))
		{
			if (DEBUG)
				System.out.println("north");
//			normal(1, 0, 0);

			STACK.transformPosition(x + w, y + h, z, V0);
			STACK.transformPosition(x + w, y, z, V1);
			STACK.transformPosition(x, y, z, V2);
			STACK.transformPosition(x, y + h, z, V3);

			GeometryUtils.normal(V0, V1, V2, NORMAL);

			builderNormal.set(NORMAL);

			if (buildFace())
			{
				vert0(element.north, pos(V0));
				vert1(element.north, pos(V1));
				vert2(element.north, pos(V2));

				vert2(element.north, pos(V2));
				vert3(element.north, pos(V3));
				vert0(element.north, pos(V0));
			}
		}


		if (element.east != null && element.east.getProperties().get(ModelRepository.LAYER_PROPERTY).equals(layer))
		{
			if (DEBUG)
				System.out.println("east");
//			normal(0, 0, 1);

			STACK.transformPosition(x + w, y + h, z + d, V0);
			STACK.transformPosition(x + w, y, z + d, V1);
			STACK.transformPosition(x + w, y, z, V2);
			STACK.transformPosition(x + w, y + h, z, V3);

			GeometryUtils.normal(V0, V1, V2, NORMAL);

			builderNormal.set(NORMAL);

			if (buildFace())
			{
				vert0(element.east, pos(V0));
				vert1(element.east, pos(V1));
				vert2(element.east, pos(V2));

				vert2(element.east, pos(V2));
				vert3(element.east, pos(V3));
				vert0(element.east, pos(V0));
			}
		}


		if (element.south != null && element.south.getProperties().get(ModelRepository.LAYER_PROPERTY).equals(layer))
		{
			if (DEBUG)
				System.out.println("south");
//			normal(-1, 0, 0);

			STACK.transformPosition(x, y + h, z + d, V0);
			STACK.transformPosition(x, y, z + d, V1);
			STACK.transformPosition(x + w, y, z + d, V2);
			STACK.transformPosition(x + w, y + h, z + d, V3);

			GeometryUtils.normal(V0, V1, V2, NORMAL);

			builderNormal.set(NORMAL);

			if (buildFace())
			{
				vert0(element.south, pos(V0));
				vert1(element.south, pos(V1));
				vert2(element.south, pos(V2));

				vert2(element.south, pos(V2));
				vert3(element.south, pos(V3));
				vert0(element.south, pos(V0));
			}
		}


		if (element.west != null && element.west.getProperties().get(ModelRepository.LAYER_PROPERTY).equals(layer))
		{
			if (DEBUG)
				System.out.println("west");
//			normal(0, 0, -1);

			STACK.transformPosition(x, y + h, z, V0);
			STACK.transformPosition(x, y, z, V1);
			STACK.transformPosition(x, y, z + d, V2);
			STACK.transformPosition(x, y + h, z + d, V3);

			GeometryUtils.normal(V0, V1, V2, NORMAL);

			builderNormal.set(NORMAL);

			if (buildFace())
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

	private final List<Vector3f> ELEMENT_VERTICES = new ArrayList<>(4 * 6);
	{
		for (int i = 0; i < 4 * 6; i++)
		{
			ELEMENT_VERTICES.add(new Vector3f());
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
	private final Quaternionf QUAT = new Quaternionf();
	private final Vector3f NORMAL = new Vector3f();
	private final Vector3f NORMAL_ = new Vector3f();
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

	private void build(BBModel model, int x, int y, int z, VoxLayer layer)
	{
		STACK.clear();
		//todo : move -8, 0, -8
//		STACK.translate(offset.x, offset.y, offset.z);
		STACK.scale(1f / 16f);
		STACK.mul(center.transform());
		STACK.translate(0, -8f, 0);
		color(center.getColor().x, center.getColor().y, center.getColor().z, center.getColor().w);
		for (OutlinerElement el : model.getElements())
		{
			build(el, layer);
		}
	}

	private void build(OutlinerElement el, VoxLayer layer)
	{
		STACK.pushMatrix();
		STACK.translate(-el.positionX, el.positionY, el.positionZ);
		STACK.translate(el.originX, el.originY, el.originZ);
		QUAT.identity();
		QUAT.rotateZ(el.rotationZ);
		QUAT.rotateY(el.rotationY);
		QUAT.rotateX(el.rotationX);
		STACK.rotate(QUAT);
		STACK.translate(-el.originX, -el.originY, -el.originZ);
		STACK.scale(el.scaleX, el.scaleY, el.scaleZ);
		if (el instanceof Outliner outliner)
		{
			for (OutlinerElement child : outliner.children)
			{
				build(child, layer);
			}
		} else if (el instanceof Element element)
		{
			rect(element, layer);
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
