package steve6472.sge.gfx.game.blockbench.model;

import java.util.HashMap;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/28/2021
 * Project: StevesGameEngine
 *
 ***********************/
public interface IProperties
{
	HashMap<String, ModelPropertyValue> getProperties();

	PropertyClass getPropertyClass();
}
