package steve6472.sge.gfx.game.blockbench.animation;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.10.2020
 * Project: CaveGame
 *
 ***********************/
public record NumericValue(float value) implements IKeyValue
{
	@Override
	public float getValue(double time)
	{
		return value;
	}
}
