package steve6472.sge.gfx.game.stack.mix;

import steve6472.sge.gfx.game.stack.buffer.Buffer3f;

/**********************
 * Created by steve6472
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public interface INormal<T> extends IMain<T>
{
	Buffer3f getNormalBuffer();

	default T normal(float nx, float ny, float nz)
	{
		getNormalBuffer().set(nx, ny, nz);
		return getTess();
	}
}
