package steve6472.sge.gfx.game.stack.mix;

import steve6472.sge.gfx.game.stack.buffer.Buffer2f;

/**********************
 * Created by steve6472
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public interface IPos2<T> extends IMain<T>
{
	Buffer2f getPositionBuffer();

	default T pos(float x, float y)
	{
		getPositionBuffer().set(x, y);
		return getTess();
	}
}
