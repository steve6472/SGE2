package steve6472.sge.test.test3d.tiles;

import steve6472.sge.main.game.stateengine.State;
import steve6472.sge.main.game.stateengine.properties.BooleanProperty;
import steve6472.sge.main.game.stateengine.properties.IProperty;

import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class DoorTile extends Tile
{
	public static final BooleanProperty OPEN = States.IS_OPEN;
	public static final BooleanProperty FACING_TOP = States.FACING_TOP;

	public DoorTile()
	{
		super(false);
		setDefaultState(getDefaultState().with(OPEN, false).with(FACING_TOP, true).get());
	}

	@Override
	public int getIndex(State state)
	{
		int top = state.get(FACING_TOP) ? 0 : 1;
		int open = state.get(OPEN) ? 0 : 1;
		return 7 + top * 2 + open;
	}

	@Override
	public void fillStates(List<IProperty<?>> properties)
	{
		properties.add(OPEN);
		properties.add(FACING_TOP);
	}
}
