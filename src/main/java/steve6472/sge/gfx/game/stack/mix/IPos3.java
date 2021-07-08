package steve6472.sge.gfx.game.stack.mix;

import steve6472.sge.gfx.game.stack.buffer.Buffer3f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public interface IPos3<T> extends IMain<T>
{
	Buffer3f getPositionBuffer();

	default T pos(float x, float y, float z)
	{
		getPositionBuffer().set(x, y, z);
		return getTess();
	}
}
