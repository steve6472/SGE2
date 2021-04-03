package steve6472.sge.main.game.stateengine;

import steve6472.sge.main.game.stateengine.properties.IProperty;

import java.util.ArrayList;
import java.util.List;

public class StateFinder
{
	private final List<State> tileStates;

	StateFinder(List<State> tileStates)
	{
		this.tileStates = new ArrayList<>(tileStates);
	}

	public <T extends Comparable<T>, V extends T> StateFinder with(IProperty<T> property, V value)
	{
		tileStates.removeIf(pb -> pb.get(property) != value);
		return this;
	}

	public State get()
	{
		return tileStates.get(0);
	}
}