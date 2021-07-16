package steve6472.sge.test.vox;

import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.gfx.game.voxelizer.IModelAccessor;
import steve6472.sge.main.game.VoxPos;
import steve6472.sge.main.game.stateengine.State;

import java.util.HashMap;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/13/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class World implements IModelAccessor
{
	public HashMap<VoxPos, State> blocks = new HashMap<>();

	public World()
	{

	}

	public void setState(State state, VoxPos pos)
	{
		blocks.put(pos, state);
	}

	public State getState(VoxPos pos)
	{
		return blocks.get(pos);
	}

	@Override
	public BBModel getModel(int x, int y, int z)
	{
		State state = blocks.get(new VoxPos(x, y, z));
		if (state == null)
			return Models.AIR;
		return ((Block) state.getObject()).getModel(state);
	}
}
