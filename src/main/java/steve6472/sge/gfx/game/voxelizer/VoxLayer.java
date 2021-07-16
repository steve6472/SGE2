package steve6472.sge.gfx.game.voxelizer;

import java.util.Objects;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/8/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class VoxLayer
{
	private final String id;

	public VoxLayer(String id)
	{
		this.id = id;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VoxLayer voxLayer = (VoxLayer) o;
		return Objects.equals(id, voxLayer.id);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}

	public String getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return "VoxLayer{" + "id='" + id + '\'' + '}';
	}
}
