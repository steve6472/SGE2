package steve6472.sge.gfx.game.stack.buffer;

import org.joml.Vector2f;

/**********************
 * Created by steve6472
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Buffer2f extends AbstractBuffer
{
	private float x, y;

	public Buffer2f(int size, int index)
	{
		super(size, index);
	}

	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public void set(Vector2f v)
	{
		set(v.x, v.y);
	}

	public void endVertex()
	{
		buffer.put(x).put(y);
	}

	@Override
	public int vertexSize()
	{
		return 2;
	}
}
