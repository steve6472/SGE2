package steve6472.sge.gfx.game.stack;

import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

import java.util.HashMap;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class Stack extends Matrix4fStack
{
	public final HashMap<String, RenderType> renderTypes = new HashMap<>();

	public Stack()
	{
		super(16);
	}

	public void addRenderType(String id, RenderType renderType)
	{
		if (renderTypes.containsKey(id))
			throw new IllegalArgumentException("Duplicate render type id:" + id);

		renderTypes.put(id, renderType);
	}

	public RenderType getRenderType(String id)
	{
		return renderTypes.get(id);
	}

	public void render(Matrix4f view)
	{
		for (RenderType value : renderTypes.values())
		{
			value.render(view);
		}
	}

	public void reset()
	{
		for (RenderType value : renderTypes.values())
		{
			value.reset();
		}
	}
}
