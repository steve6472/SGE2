package steve6472.sge.main.game.stateengine.properties;

/**********************
 * Created by steve6472
 * On date: 02.07.2020
 * Project: StateTest
 *
 ***********************/
public interface IProperty<T extends Comparable<T>>
{
	T[] getPossibleValues();

	String getName();
}
