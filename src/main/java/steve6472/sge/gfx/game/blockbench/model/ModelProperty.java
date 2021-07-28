package steve6472.sge.gfx.game.blockbench.model;

import java.util.function.Function;
import java.util.function.Supplier;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/28/2021
 * Project: StevesGameEngine
 *
 ***********************/
public record ModelProperty(PropertyClass clazz, PropertyType type, String name, Supplier<Object> defaultValue, Function<Object, Object> value)
{
	@Override
	public String toString()
	{
		return "ModelProperty{" + "clazz=" + clazz + ", type=" + type + ", name='" + name + '\'' + ", defaultValue=<...>" + ", value=<...>}";
	}
}
