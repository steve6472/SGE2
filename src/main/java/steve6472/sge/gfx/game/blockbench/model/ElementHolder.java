package steve6472.sge.gfx.game.blockbench.model;

import java.util.function.Supplier;

/**********************
 * Created by steve6472
 * On date: 7/20/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class ElementHolder
{
	private final Supplier<Element> constructor;
	private Element object;

	public ElementHolder(Supplier<Element> constructor)
	{
		this.constructor = constructor;
		this.object = constructor.get();
	}

	public Supplier<Element> getConstructor()
	{
		return constructor;
	}

	public Element getObject()
	{
		return object;
	}

	public void reload()
	{
		object = constructor.get();
	}
}
