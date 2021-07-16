package steve6472.sge.gfx.game.voxelizer;

import java.nio.FloatBuffer;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/8/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class PassData
{
	public final VoxLayer layer;
	public final FloatBuffer vert, normal, color, text;
	public final int triangleCount, x, y, z;

	public PassData(VoxLayer layer, FloatBuffer vert, FloatBuffer color, FloatBuffer text, FloatBuffer normal, int triangleCount, int x, int y, int z)
	{
		this.layer = layer;
		this.vert = vert;
		this.color = color;
		this.text = text;
		this.normal = normal;
		this.triangleCount = triangleCount;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString()
	{
		return "PassData{" + "layer=" + layer + ", triangleCount=" + triangleCount + ", x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}
