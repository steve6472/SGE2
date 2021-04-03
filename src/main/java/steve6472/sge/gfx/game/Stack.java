package steve6472.sge.gfx.game;

import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 4/3/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Stack extends Matrix4fStack
{
	private final EntityTess entityTess;

	public Stack()
	{
		super(16);
		entityTess = new EntityTess(this);
	}

	public void render(Matrix4f view)
	{
		entityTess.render(view);
	}

	public void reset()
	{
		entityTess.reset();
	}

	public EntityTess getEntityTess()
	{
		return entityTess;
	}
}
