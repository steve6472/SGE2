package steve6472.sge.gfx.game.voxelizer;

import org.joml.Vector3i;

import static steve6472.sge.gfx.VertexObjectCreator.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/8/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class VoxModel
{
	int vao, positionVbo, colorVbo, textureVbo, vboNorm;
	int triangleCount;

	public final VoxLayer layer;
	public final Vector3i position;

	public boolean rebuildInProgress;

	public VoxModel(VoxLayer layer, Vector3i position)
	{
		this.layer = layer;
		this.position = position;
		load();
	}

	protected void load()
	{
		vao = createVAO();
		positionVbo = storeFloatDataInAttributeList(0, 3, new float[]{-1, 0, 1, -1, 0, -1, 1, 0, -1});
		colorVbo = storeFloatDataInAttributeList(1, 4, new float[]{1, 1, 1, 1});
		textureVbo = storeFloatDataInAttributeList(2, 2, new float[]{0, 0, 0, 1, 1, 1});
		vboNorm = storeFloatDataInAttributeList(3, 3, new float[]{0, 0, 0, 1, 1, 1, 0, 0, 0});
		unbindVAO();
	}

	public void update(PassData data)
	{
		bindVAO(vao);

		triangleCount = data.triangleCount;
		storeFloatDataInAttributeList(0, 3, positionVbo, data.vert);
		storeFloatDataInAttributeList(1, 4, colorVbo, data.color);
		storeFloatDataInAttributeList(2, 2, textureVbo, data.text);
		storeFloatDataInAttributeList(3, 3, vboNorm, data.normal);
		unbindVAO();
		rebuildInProgress = false;
	}

	public void unload()
	{
		delete(vao, positionVbo, colorVbo, textureVbo, vboNorm);
	}

	public int getVao()
	{
		return vao;
	}

	public int getTriangleCount()
	{
		return triangleCount;
	}

	public VoxLayer getLayer()
	{
		return layer;
	}
}
