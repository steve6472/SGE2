package steve6472.sge.gfx.game.blockbench.animation;

/**********************
 * Created by steve6472
 * On date: 25.10.2020
 * Project: CaveGame
 *
 ***********************/
public record Key(double time, IKeyValue x, IKeyValue y, IKeyValue z, EnumKeyType keyType) implements IKey
{
	@Override
	public float x(double time)
	{
		return x.getValue(time);
	}

	@Override
	public float y(double time)
	{
		return y.getValue(time);
	}

	@Override
	public float z(double time)
	{
		return z.getValue(time);
	}
}
