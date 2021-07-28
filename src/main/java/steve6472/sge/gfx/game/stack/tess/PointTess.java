package steve6472.sge.gfx.game.stack.tess;

import org.joml.Matrix4fStack;
import steve6472.sge.gfx.game.stack.buffer.AbstractBuffer;
import steve6472.sge.gfx.game.stack.buffer.Buffer3f;
import steve6472.sge.gfx.game.stack.buffer.Buffer4f;
import steve6472.sge.gfx.game.stack.mix.IColor4;
import steve6472.sge.gfx.game.stack.mix.IStackPos3;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class PointTess extends AbstractTess implements IStackPos3<PointTess>, IColor4<PointTess>
{
	private final Matrix4fStack stack;

	private Buffer3f positionBuffer;
	private Buffer4f colorBuffer;

	public PointTess(Matrix4fStack stack, int maxSize)
	{
		super(maxSize);
		this.stack = stack;
	}

	@Override
	public void begin()
	{
		super.begin();
		colorBuffer.set(1, 1, 1, 1);
	}

	@Override
	protected void createBuffers(int maxSize)
	{
		positionBuffer = new Buffer3f(maxSize, 0);
		colorBuffer = new Buffer4f(maxSize, 1);
	}

	@Override
	protected AbstractBuffer[] buffers()
	{
		return new AbstractBuffer[] {positionBuffer, colorBuffer};
	}

	@Override
	public Buffer3f getPositionBuffer()
	{
		return positionBuffer;
	}

	@Override
	public Buffer4f getColorBuffer()
	{
		return colorBuffer;
	}

	@Override
	public Matrix4fStack getStack()
	{
		return stack;
	}

	@Override
	public PointTess getTess()
	{
		return this;
	}
}
