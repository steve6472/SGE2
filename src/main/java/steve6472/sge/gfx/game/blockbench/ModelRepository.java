package steve6472.sge.gfx.game.blockbench;

import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.voxelizer.VoxLayer;
import steve6472.sge.gfx.game.voxelizer.VoxLayers;

import java.util.ArrayList;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/8/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class ModelRepository
{
	private final List<BBModel> models = new ArrayList<>();
	private final ModelTextureAtlas atlas = new ModelTextureAtlas();
	private final VoxLayers layers = new VoxLayers();

	public static final VoxLayer NORMAL_LAYER = new VoxLayer("normal");

	public ModelRepository()
	{
		addLayer(NORMAL_LAYER);
	}

	public BBModel addModel(String path)
	{
		BBModel model = new BBModel(atlas, path, layers);
		models.add(model);
		return model;
	}

	public BBModel addModel(BBModel model)
	{
		models.add(model);
		return model;
	}

	public void finish()
	{
		atlas.compileTextures(0);
		models.forEach(atlas::assignTextures);
	}

	public void reload()
	{
		atlas.clean();
		models.forEach(m -> m.reload(atlas, layers));
		finish();
	}

	public StaticTexture getAtlasTexture()
	{
		return atlas.getTexture();
	}

	public void addLayer(VoxLayer layer)
	{
		layers.addLayer(layer);
	}

	public List<VoxLayer> getLayers()
	{
		return layers.getLayers();
	}

	public VoxLayer getLayer(int index)
	{
		return layers.getLayer(index);
	}

	public int getLayerIndex(String id)
	{
		return layers.getLayerIndex(id);
	}

	public VoxLayer getLayer(String id)
	{
		return layers.getLayer(id);
	}
}
