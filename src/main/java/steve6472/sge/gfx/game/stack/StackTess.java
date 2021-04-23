package steve6472.sge.gfx.game.stack;

import org.joml.Matrix4f;
import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.gfx.shaders.StaticShader3D;

public abstract class StackTess
{
	protected final Stack stack;

	public StackTess(Stack stack)
	{
		this.stack = stack;
	}

	public abstract StackTess endVertex();

	public abstract void render(Matrix4f view, StaticShader3D shader, StaticTexture texture);

	public abstract void reset();
}
