package steve6472.sge.gfx.game.voxelizer;

import steve6472.sge.gfx.game.blockbench.model.BBModel;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/13/2021
 * Project: StevesGameEngine
 *
 ***********************/
public interface IModelAccessor
{
	BBModel getModel(int x, int y, int z);
}
