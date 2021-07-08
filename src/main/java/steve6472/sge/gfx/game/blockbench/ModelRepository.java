package steve6472.sge.gfx.game.blockbench;

import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.gfx.game.blockbench.model.BBModel;

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

	public BBModel addModel(String path)
	{
		BBModel model = new BBModel(atlas, path);
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
		models.forEach(m -> m.reload(atlas));
		finish();
	}

	public StaticTexture getAtlasTexture()
	{
		return atlas.getTexture();
	}
}
