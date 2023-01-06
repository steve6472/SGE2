package steve6472.sge.gfx.game.stack.tess;

import org.joml.Matrix4fStack;
import steve6472.sge.gfx.game.stack.buffer.AbstractBuffer;
import steve6472.sge.gfx.game.stack.buffer.Buffer3f;
import steve6472.sge.gfx.game.stack.buffer.Buffer4f;
import steve6472.sge.gfx.game.stack.mix.IColor4;
import steve6472.sge.gfx.game.stack.mix.IStackPos3;

/**********************
 * Created by steve6472
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class LineTess extends AbstractTess implements IStackPos3<LineTess>, IColor4<LineTess>
{
	private final Matrix4fStack stack;

	private Buffer3f positionBuffer;
	private Buffer4f colorBuffer;

	public LineTess(Matrix4fStack stack, int maxSize)
	{
		super(maxSize);
		this.stack = stack;
	}

	public void axisGizmo(float lenght, float size, float alpha)
	{
		coloredBox(size, 0, 0, lenght, size, size, 1, 0, 0, alpha);
		coloredBox(0, size, 0, size, lenght, size, 0, 1, 0, alpha);
		coloredBox(0, 0, size, size, size, lenght, 0, 0, 1, alpha);
	}

	public void coloredBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float r, float g, float b, float a)
	{
		color(r, g, b, a);
		box(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public void box(float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		pos(minX, minY, minZ).endVertex();
		pos(maxX, minY, minZ).endVertex();
		pos(minX, minY, minZ).endVertex();
		pos(minX, minY, maxZ).endVertex();
		pos(minX, minY, maxZ).endVertex();
		pos(maxX, minY, maxZ).endVertex();
		pos(maxX, minY, minZ).endVertex();
		pos(maxX, minY, maxZ).endVertex();

		pos(minX, minY, minZ).endVertex();
		pos(minX, maxY, minZ).endVertex();
		pos(maxX, minY, minZ).endVertex();
		pos(maxX, maxY, minZ).endVertex();
		pos(minX, minY, maxZ).endVertex();
		pos(minX, maxY, maxZ).endVertex();
		pos(maxX, minY, maxZ).endVertex();
		pos(maxX, maxY, maxZ).endVertex();

		pos(minX, maxY, minZ).endVertex();
		pos(maxX, maxY, minZ).endVertex();
		pos(minX, maxY, minZ).endVertex();
		pos(minX, maxY, maxZ).endVertex();
		pos(minX, maxY, maxZ).endVertex();
		pos(maxX, maxY, maxZ).endVertex();
		pos(maxX, maxY, minZ).endVertex();
		pos(maxX, maxY, maxZ).endVertex();
	}

	public void box(float w, float h, float d)
	{
		float W = w / 2f;
		float H = h / 2f;
		float D = d / 2f;

		// Up
		pos(-W, +H, +D).endVertex();
		pos(+W, +H, +D).endVertex();

		pos(-W, +H, -D).endVertex();
		pos(+W, +H, -D).endVertex();

		pos(+W, +H, +D).endVertex();
		pos(+W, +H, -D).endVertex();

		pos(-W, +H, +D).endVertex();
		pos(-W, +H, -D).endVertex();

		// North-East
		pos(+W, +H, -D).endVertex();
		pos(+W, -H, -D).endVertex();

		// South-East
		pos(+W, +H, +D).endVertex();
		pos(+W, -H, +D).endVertex();

		// South-West
		pos(-W, +H, +D).endVertex();
		pos(-W, -H, +D).endVertex();

		// North-West
		pos(-W, +H, -D).endVertex();
		pos(-W, -H, -D).endVertex();

		// Down
		pos(-W, -H, +D).endVertex();
		pos(+W, -H, +D).endVertex();

		pos(-W, -H, -D).endVertex();
		pos(+W, -H, -D).endVertex();

		pos(+W, -H, +D).endVertex();
		pos(+W, -H, -D).endVertex();

		pos(-W, -H, +D).endVertex();
		pos(-W, -H, -D).endVertex();
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
	public LineTess getTess()
	{
		return this;
	}
}
