package steve6472.sge.gfx.game.voxelizer;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/8/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class VoxLayers
{
	private final List<VoxLayer> layers = new ArrayList<>();

	public void addLayer(VoxLayer layer)
	{
		layers.add(layer);
	}

	public List<VoxLayer> getLayers()
	{
		return layers;
	}

	public VoxLayer getLayer(int index)
	{
		return layers.get(index);
	}

	public int getLayerIndex(String id)
	{
		for (int i = 0; i < layers.size(); i++)
		{
			if (layers.get(i).getId().equals(id))
				return i;
		}

		throw new IllegalArgumentException("Layer with id '" + id + "' not found!");
	}

	public VoxLayer getLayer(String id)
	{
		for (VoxLayer layer : layers)
		{
			if (layer.getId().equals(id))
				return layer;
		}

		throw new IllegalArgumentException("Layer with id '" + id + "' not found!");
	}
}
