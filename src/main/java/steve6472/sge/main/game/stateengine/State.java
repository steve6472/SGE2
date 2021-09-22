package steve6472.sge.main.game.stateengine;

import steve6472.sge.main.game.stateengine.properties.IProperty;

import java.util.HashMap;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 3/20/2021
 * Project: GameMaker
 *
 ***********************/
public class State
{
	private final StateObject tile;
	private final HashMap<IProperty<?>, Comparable<?>> properties;
	private final List<State> tileStates;

	public State(StateObject tile, HashMap<IProperty<?>, Comparable<?>> properties, List<State> tileStates)
	{
		this.tile = tile;
		this.properties = properties;
		this.tileStates = tileStates;
	}

	public <T extends Comparable<T>> T get(IProperty<T> property)
	{
		return (T) properties.get(property);
	}

	public <T extends Comparable<T>> State with(IProperty<T> property, T value)
	{
		if (properties.get(property) == null)
			throw new IllegalStateException("Property '" + property.getName() + "' does not exist!");

		m: for (State tileState : tileStates)
		{
			for (IProperty<?> iProperty : tileState.properties.keySet())
			{
				if (iProperty == property)
				{
					if (!tileState.get(property).equals(value))
					{
						continue m;
					}
				} else
				{
					Comparable<?> val = properties.get(iProperty);
					if (!tileState.get(iProperty).equals(val))
					{
						continue m;
					}
				}
			}

			return tileState;
		}

		throw new IllegalStateException("Could not find desired value '" + value + "' for property '" + property.getName() + "'");
	}

	public StateObject getObject()
	{
		return tile;
	}

	@Override
	public String toString()
	{
		return "State{" + "tile=" + tile + ", properties=" + properties + '}';
	}
}
