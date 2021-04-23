package steve6472.sge.gfx.game.stack;

import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import steve6472.sge.gfx.StaticTexture;
import steve6472.sge.gfx.shaders.StaticShader3D;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Stack extends Matrix4fStack
{
	private final BBModelTess blockbenchTess;
	private final LineTess lineTess;

	public Stack()
	{
		super(16);
		blockbenchTess = new BBModelTess(this);
		lineTess = new LineTess(this);
	}

	public void render(Matrix4f view, StaticShader3D bbShader, StaticTexture bbTexture, StaticShader3D lineShader)
	{
		blockbenchTess.render(view, bbShader, bbTexture);
		lineTess.render(view, lineShader, null);
	}

	public void reset()
	{
		blockbenchTess.reset();
		lineTess.reset();
	}

	public BBModelTess getBlockbenchTess()
	{
		return blockbenchTess;
	}

	public LineTess getLineTess()
	{
		return lineTess;
	}
}
