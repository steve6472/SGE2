package steve6472.sge.gfx.game.stack.buffer;

/**********************
 * Created by steve6472
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Buffer1f extends AbstractBuffer
{
	private float x;

	public Buffer1f(int size, int index)
	{
		super(size, index);
	}

	public void set(float x)
	{
		this.x = x;
	}

	public void endVertex()
	{
		buffer.put(x);
	}

	@Override
	public int vertexSize()
	{
		return 1;
	}
}
