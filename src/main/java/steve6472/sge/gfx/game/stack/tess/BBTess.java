package steve6472.sge.gfx.game.stack.tess;

import org.joml.Matrix4fStack;
import steve6472.sge.gfx.game.stack.buffer.AbstractBuffer;
import steve6472.sge.gfx.game.stack.buffer.Buffer2f;
import steve6472.sge.gfx.game.stack.buffer.Buffer3f;
import steve6472.sge.gfx.game.stack.buffer.Buffer4f;
import steve6472.sge.gfx.game.stack.mix.IColor4;
import steve6472.sge.gfx.game.stack.mix.INormal;
import steve6472.sge.gfx.game.stack.mix.IStackPos3;
import steve6472.sge.gfx.game.stack.mix.ITexture;

/**********************
 * Created by steve6472
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class BBTess extends AbstractTess implements IStackPos3<BBTess>, IColor4<BBTess>, ITexture<BBTess>, INormal<BBTess>
{
	private final Matrix4fStack stack;

	private Buffer3f positionBuffer;
	private Buffer4f colorBuffer;
	private Buffer2f textureBuffer;
	private Buffer3f normalBuffer;

	public BBTess(Matrix4fStack stack, int maxSize)
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
		textureBuffer = new Buffer2f(maxSize, 2);
		normalBuffer = new Buffer3f(maxSize, 3);
	}

	@Override
	protected AbstractBuffer[] buffers()
	{
		return new AbstractBuffer[] {positionBuffer, colorBuffer, textureBuffer, normalBuffer};
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
	public Buffer2f getTextureBuffer()
	{
		return textureBuffer;
	}

	@Override
	public Buffer3f getNormalBuffer()
	{
		return normalBuffer;
	}

	@Override
	public Matrix4fStack getStack()
	{
		return stack;
	}

	@Override
	public BBTess getTess()
	{
		return this;
	}
}
