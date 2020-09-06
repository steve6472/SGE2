package steve6472.sge.gfx.shaders;

import org.joml.Matrix4f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 16.02.2020
 * Project: SJP
 *
 ***********************/
public abstract class StaticShaderCubeMap extends StaticShaderBase
{
	private final Type projection;
	private final Type view;

	public StaticShaderCubeMap(String path)
	{
		shader = Shader.fromShaders(path);
		this.path = path;

		addUniform("projection", projection = new Type(EnumUniformType.MAT_4));
		addUniform("view", view = new Type(EnumUniformType.MAT_4));

		if (projection.getId() == -1)
			throw new RuntimeException("Uniform name not found for projection");
		if (view.getId() == -1)
			throw new RuntimeException("Uniform name not found for view");

		initMatrixBuffers();
		createUniforms();

		shader.bind();
	}

	public void bind(Matrix4f view)
	{
		shader.bind();
		setView(view);
	}

	public void setProjection(Matrix4f matrix4f)
	{
		setUniform(projection, matrix4f);
	}

	public void setView(Matrix4f matrix4f)
	{
		setUniform(view, matrix4f);
	}
}
