package steve6472.sge.gfx.game.stack.mix;

import steve6472.sge.gfx.game.stack.buffer.Buffer2f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public interface ITexture<T> extends IMain<T>
{
	Buffer2f getTextureBuffer();

	default T uv(float u, float v)
	{
		getTextureBuffer().set(u, v);
		return getTess();
	}
}
