package steve6472.sge.gfx.game.stack.buffer;

import org.joml.Vector4f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Buffer4f extends AbstractBuffer
{
	private float x, y, z, w;

	public Buffer4f(int size, int index)
	{
		super(size, index);
	}

	public void set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public void set(Vector4f v)
	{
		set(v.x, v.y, v.z, v.w);
	}

	public void endVertex()
	{
		buffer.put(x).put(y).put(z).put(w);
	}

	@Override
	public int vertexSize()
	{
		return 4;
	}
}
