package steve6472.sge.gfx.game.stack;

import org.joml.Matrix4f;
import steve6472.sge.gfx.game.stack.tess.AbstractTess;
import steve6472.sge.gfx.shaders.StaticGeometryShader3D;
import steve6472.sge.gfx.shaders.StaticShader3D;
import steve6472.sge.gfx.shaders.StaticShaderBase;

import java.util.function.BiConsumer;

/**********************
 * Created by steve6472
 * On date: 7/7/2021
 * Project: StevesGameEngine
 *
 ***********************/
public class RenderType
{
	private final StaticShaderBase shader;
	private final AbstractTess tess;
	private final BiConsumer<StaticShaderBase, AbstractTess> renderFunction;

	public RenderType(StaticShaderBase shader, AbstractTess tess, BiConsumer<StaticShaderBase, AbstractTess> renderFunction)
	{
		this.shader = shader;
		this.tess = tess;
		this.renderFunction = renderFunction;
	}

	/**
	 * Automatically binds shader
	 * Binds with view if shader is 3d
	 * @param view view matrix
	 */
	public void render(Matrix4f view)
	{
		if (shader instanceof StaticShader3D shader3D)
			shader3D.bind(view);
		else if (shader instanceof StaticGeometryShader3D geometryShader)
			geometryShader.bind(view);
		else
			shader.bind();

		renderFunction.accept(shader, tess);
	}

	public void reset()
	{
		tess.begin();
	}

	public AbstractTess getTess()
	{
		return tess;
	}
}
