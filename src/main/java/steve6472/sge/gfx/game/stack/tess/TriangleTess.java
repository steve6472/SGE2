package steve6472.sge.gfx.game.stack.tess;

import org.joml.Matrix4fStack;
import steve6472.sge.gfx.game.stack.buffer.AbstractBuffer;
import steve6472.sge.gfx.game.stack.buffer.Buffer3f;
import steve6472.sge.gfx.game.stack.buffer.Buffer4f;
import steve6472.sge.gfx.game.stack.mix.IColor4;
import steve6472.sge.gfx.game.stack.mix.IStackPos3;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/8/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class TriangleTess extends AbstractTess implements IStackPos3<TriangleTess>, IColor4<TriangleTess>
{
	private final Matrix4fStack stack;

	private Buffer3f positionBuffer;
	private Buffer4f colorBuffer;

	private float lastR, lastG, lastB, lastA;

	public TriangleTess(Matrix4fStack stack, int maxSize)
	{
		super(maxSize);
		this.stack = stack;
	}

	public void axisGizmo(float lenght, float size, float alpha)
	{
		color(1, 0, 0, alpha);
		shadedBox(size, 0, 0, lenght, size, size);
		color(0, 1, 0, alpha);
		shadedBox(0, size, 0, size, lenght, size);
		color(0, 0, 1, alpha);
		shadedBox(0, 0, size, size, size, lenght);
	}

	public void triangularPyramid()
	{
		float sqrtThree = (float) Math.sqrt(3f);
		float sqrtSix = (float) Math.sqrt(6f);
		pos(1, -1 / sqrtSix, -1 / sqrtThree).endVertex();
		pos(0, 3f / sqrtSix, 0).endVertex();
		pos(0, -1 / sqrtSix, 2f / sqrtThree).endVertex();

		pos(-1, -1 / sqrtSix, -1 / sqrtThree).endVertex();
		pos(0, -1 / sqrtSix, 2f / sqrtThree).endVertex();
		pos(0, 3f / sqrtSix, 0).endVertex();

		pos(1, -1 / sqrtSix, -1 / sqrtThree).endVertex();
		pos(-1, -1 / sqrtSix, -1 / sqrtThree).endVertex();
		pos(0, 3f / sqrtSix, 0).endVertex();

		pos(-1, -1 / sqrtSix, -1 / sqrtThree).endVertex();
		pos(1, -1 / sqrtSix, -1 / sqrtThree).endVertex();
		pos(0, -1 / sqrtSix, 2f / sqrtThree).endVertex();
	}

	public void box(float w, float h, float d)
	{
		float W = w / 2f;
		float H = h / 2f;
		float D = d / 2f;

		// Up
		pos(+W, +H, -D).endVertex();
		pos(-W, +H, -D).endVertex();
		pos(-W, +H, +D).endVertex();

		pos(-W, +H, +D).endVertex();
		pos(+W, +H, +D).endVertex();
		pos(+W, +H, -D).endVertex();


		// West
		pos(-W, +H, -D).endVertex();
		pos(-W, -H, -D).endVertex();
		pos(-W, -H, +D).endVertex();

		pos(-W, -H, +D).endVertex();
		pos(-W, +H, +D).endVertex();
		pos(-W, +H, -D).endVertex();


		// South
		pos(-W, +H, +D).endVertex();
		pos(-W, -H, +D).endVertex();
		pos(+W, -H, +D).endVertex();

		pos(+W, -H, +D).endVertex();
		pos(+W, +H, +D).endVertex();
		pos(-W, +H, +D).endVertex();


		// East
		pos(+W, +H, +D).endVertex();
		pos(+W, -H, +D).endVertex();
		pos(+W, -H, -D).endVertex();

		pos(+W, -H, -D).endVertex();
		pos(+W, +H, -D).endVertex();
		pos(+W, +H, +D).endVertex();


		// North
		pos(+W, +H, -D).endVertex();
		pos(+W, -H, -D).endVertex();
		pos(-W, -H, -D).endVertex();

		pos(-W, -H, -D).endVertex();
		pos(-W, +H, -D).endVertex();
		pos(+W, +H, -D).endVertex();


		// Down
		pos(-W, -H, +D).endVertex();
		pos(-W, -H, -D).endVertex();
		pos(+W, -H, -D).endVertex();

		pos(+W, -H, -D).endVertex();
		pos(+W, -H, +D).endVertex();
		pos(-W, -H, +D).endVertex();
	}

	public void box(float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		// Up
		pos(maxX, maxY, minZ).endVertex();
		pos(minX, maxY, minZ).endVertex();
		pos(minX, maxY, maxZ).endVertex();

		pos(minX, maxY, maxZ).endVertex();
		pos(maxX, maxY, maxZ).endVertex();
		pos(maxX, maxY, minZ).endVertex();


		// West
		pos(minX, maxY, minZ).endVertex();
		pos(minX, minY, minZ).endVertex();
		pos(minX, minY, maxZ).endVertex();

		pos(minX, minY, maxZ).endVertex();
		pos(minX, maxY, maxZ).endVertex();
		pos(minX, maxY, minZ).endVertex();


		// South
		pos(minX, maxY, maxZ).endVertex();
		pos(minX, minY, maxZ).endVertex();
		pos(maxX, minY, maxZ).endVertex();

		pos(maxX, minY, maxZ).endVertex();
		pos(maxX, maxY, maxZ).endVertex();
		pos(minX, maxY, maxZ).endVertex();


		// East
		pos(maxX, maxY, maxZ).endVertex();
		pos(maxX, minY, maxZ).endVertex();
		pos(maxX, minY, minZ).endVertex();

		pos(maxX, minY, minZ).endVertex();
		pos(maxX, maxY, minZ).endVertex();
		pos(maxX, maxY, maxZ).endVertex();


		// North
		pos(maxX, maxY, minZ).endVertex();
		pos(maxX, minY, minZ).endVertex();
		pos(minX, minY, minZ).endVertex();

		pos(minX, minY, minZ).endVertex();
		pos(minX, maxY, minZ).endVertex();
		pos(maxX, maxY, minZ).endVertex();


		// Down
		pos(minX, minY, maxZ).endVertex();
		pos(minX, minY, minZ).endVertex();
		pos(maxX, minY, minZ).endVertex();

		pos(maxX, minY, minZ).endVertex();
		pos(maxX, minY, maxZ).endVertex();
		pos(minX, minY, maxZ).endVertex();
	}

	public void shadedBox(float w, float h, float d)
	{
		float W = w / 2f;
		float H = h / 2f;
		float D = d / 2f;

		float r = lastR;
		float g = lastG;
		float b = lastB;
		float a = lastA;

		// Up
		color(r, g, b, a);
		pos(+W, +H, -D).endVertex();
		pos(-W, +H, -D).endVertex();
		pos(-W, +H, +D).endVertex();

		pos(-W, +H, +D).endVertex();
		pos(+W, +H, +D).endVertex();
		pos(+W, +H, -D).endVertex();


		// West
		color(r * 0.6f, g * 0.6f, b * 0.6f, a);
		pos(-W, +H, -D).endVertex();
		pos(-W, -H, -D).endVertex();
		pos(-W, -H, +D).endVertex();

		pos(-W, -H, +D).endVertex();
		pos(-W, +H, +D).endVertex();
		pos(-W, +H, -D).endVertex();


		// South
		color(r * 0.8f, g * 0.8f, b * 0.8f, a);
		pos(-W, +H, +D).endVertex();
		pos(-W, -H, +D).endVertex();
		pos(+W, -H, +D).endVertex();

		pos(+W, -H, +D).endVertex();
		pos(+W, +H, +D).endVertex();
		pos(-W, +H, +D).endVertex();


		// East
		color(r * 0.6f, g * 0.6f, b * 0.6f, a);
		pos(+W, +H, +D).endVertex();
		pos(+W, -H, +D).endVertex();
		pos(+W, -H, -D).endVertex();

		pos(+W, -H, -D).endVertex();
		pos(+W, +H, -D).endVertex();
		pos(+W, +H, +D).endVertex();


		// North
		color(r * 0.8f, g * 0.8f, b * 0.8f, a);
		pos(+W, +H, -D).endVertex();
		pos(+W, -H, -D).endVertex();
		pos(-W, -H, -D).endVertex();

		pos(-W, -H, -D).endVertex();
		pos(-W, +H, -D).endVertex();
		pos(+W, +H, -D).endVertex();


		// Down
		color(r * 0.5f, g * 0.5f, b * 0.5f, a);
		pos(-W, -H, +D).endVertex();
		pos(-W, -H, -D).endVertex();
		pos(+W, -H, -D).endVertex();

		pos(+W, -H, -D).endVertex();
		pos(+W, -H, +D).endVertex();
		pos(-W, -H, +D).endVertex();
	}

	public void shadedBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		float r = lastR;
		float g = lastG;
		float b = lastB;
		float a = lastA;

		// Up
		color(r, g, b, a);
		pos(maxX, maxY, minZ).endVertex();
		pos(minX, maxY, minZ).endVertex();
		pos(minX, maxY, maxZ).endVertex();

		pos(minX, maxY, maxZ).endVertex();
		pos(maxX, maxY, maxZ).endVertex();
		pos(maxX, maxY, minZ).endVertex();


		// West
		color(r * 0.6f, g * 0.6f, b * 0.6f, a);
		pos(minX, maxY, minZ).endVertex();
		pos(minX, minY, minZ).endVertex();
		pos(minX, minY, maxZ).endVertex();

		pos(minX, minY, maxZ).endVertex();
		pos(minX, maxY, maxZ).endVertex();
		pos(minX, maxY, minZ).endVertex();


		// South
		color(r * 0.8f, g * 0.8f, b * 0.8f, a);
		pos(minX, maxY, maxZ).endVertex();
		pos(minX, minY, maxZ).endVertex();
		pos(maxX, minY, maxZ).endVertex();

		pos(maxX, minY, maxZ).endVertex();
		pos(maxX, maxY, maxZ).endVertex();
		pos(minX, maxY, maxZ).endVertex();


		// East
		color(r * 0.6f, g * 0.6f, b * 0.6f, a);
		pos(maxX, maxY, maxZ).endVertex();
		pos(maxX, minY, maxZ).endVertex();
		pos(maxX, minY, minZ).endVertex();

		pos(maxX, minY, minZ).endVertex();
		pos(maxX, maxY, minZ).endVertex();
		pos(maxX, maxY, maxZ).endVertex();


		// North
		color(r * 0.8f, g * 0.8f, b * 0.8f, a);
		pos(maxX, maxY, minZ).endVertex();
		pos(maxX, minY, minZ).endVertex();
		pos(minX, minY, minZ).endVertex();

		pos(minX, minY, minZ).endVertex();
		pos(minX, maxY, minZ).endVertex();
		pos(maxX, maxY, minZ).endVertex();


		// Down
		color(r * 0.5f, g * 0.5f, b * 0.5f, a);
		pos(minX, minY, maxZ).endVertex();
		pos(minX, minY, minZ).endVertex();
		pos(maxX, minY, minZ).endVertex();

		pos(maxX, minY, minZ).endVertex();
		pos(maxX, minY, maxZ).endVertex();
		pos(minX, minY, maxZ).endVertex();
	}

	@Override
	public TriangleTess color(float r, float g, float b, float a)
	{
		lastR = r;
		lastG = g;
		lastB = b;
		lastA = a;
		return IColor4.super.color(r, g, b, a);
	}

	@Override
	public void begin()
	{
		super.begin();
		color(1, 1, 1, 1);
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
	public TriangleTess getTess()
	{
		return this;
	}
}
