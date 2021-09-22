package steve6472.sge.main.game.stateengine;

import steve6472.sge.main.game.stateengine.properties.BooleanProperty;
import steve6472.sge.main.game.stateengine.properties.IProperty;
import steve6472.sge.main.game.stateengine.properties.IntProperty;

import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 9/22/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class TestStateObject extends StateObject
{
	public static BooleanProperty LIT = BooleanProperty.create("lit");
	public static IntProperty FACING = IntProperty.create("facing", 0, 3);
	public static BooleanProperty UNUSED_BOOL = BooleanProperty.create("unused");

	public TestStateObject()
	{
		setDefaultState(getDefaultState().with(LIT, false).with(FACING, 0));
	}

	@Override
	public void fillStates(List<IProperty<?>> properties)
	{
		properties.add(FACING);
		properties.add(LIT);
	}
}
