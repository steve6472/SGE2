package steve6472.sge.gfx.game.stack.buffer;

import org.joml.Vector3f;

/**********************
 * Created by steve6472
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Buffer3f extends AbstractBuffer
{
	private float x, y, z;

	public Buffer3f(int size, int index)
	{
		super(size, index);
	}

	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(Vector3f v)
	{
		set(v.x, v.y, v.z);
	}

	public void endVertex()
	{
		buffer.put(x).put(y).put(z);
	}

	@Override
	public int vertexSize()
	{
		return 3;
	}
}
