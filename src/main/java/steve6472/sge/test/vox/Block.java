package steve6472.sge.test.vox;

import steve6472.sge.gfx.game.blockbench.model.BBModel;
import steve6472.sge.main.game.stateengine.State;
import steve6472.sge.main.game.stateengine.StateObject;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/8/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Block extends StateObject
{
	private final BBModel model;

	public Block(BBModel model)
	{
		this.model = model;
	}

	public BBModel getModel(State state)
	{
		return model;
	}
}