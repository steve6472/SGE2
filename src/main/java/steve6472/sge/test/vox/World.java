package steve6472.sge.test.vox;

import steve6472.sge.gfx.game.voxelizer.Builder;
import steve6472.sge.gfx.game.voxelizer.IModelAccessor;
import steve6472.sge.main.game.VoxPos;
import steve6472.sge.main.game.stateengine.State;

import java.util.HashMap;

/**********************
 * Created by steve6472
 * On date: 7/13/2021
 * Project: StevesGameEngine
 *
 ***********************/
class World implements IModelAccessor
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
	public void loadElements(Builder elements, int x, int y, int z)
	{
		State state = blocks.get(new VoxPos(x, y, z));
		if (state != null)
		{
			elements.addModel(((Block) state.getObject()).getModel(state));
		}
//		Element CENTER = ElementBuilder
//			.create()
//			.rectangle(0, 0, 0, 16, 16, 16)
//			.autoFaces(Models.grass, Direction.UP)
//			.autoFaces(Models.grass_side, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)
//			.autoFaces(Models.dirt, Direction.DOWN)
//			.build();
//
//		VoxRenderTest.models.getAtlas().faces(CENTER);
//
//		if (x == 0 && y == 0 && z == 0)
//		{
//			elements.addElement(CENTER);
//		}
	}
}
