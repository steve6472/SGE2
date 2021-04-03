package steve6472.sge.gfx.game;

import org.joml.Matrix4f;

public abstract class StackTess
{
	protected final Stack stack;

	public StackTess(Stack stack)
	{
		this.stack = stack;
	}

	public abstract StackTess endVertex();

	public abstract void render(Matrix4f view);

	public abstract void reset();
}
