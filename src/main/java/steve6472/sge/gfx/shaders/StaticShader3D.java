package steve6472.sge.gfx.shaders;

import org.joml.Matrix4f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.08.2019
 * Project: SJP
 *
 ***********************/
public abstract class StaticShader3D extends StaticShaderBase
{
	protected final Type transformation;
	protected final Type projection;
	protected final Type view;

	public StaticShader3D(String path)
	{
		this(Shader.fromShaders(path));
		this.path = path;
	}

	public StaticShader3D(Shader shader)
	{
		this.shader = shader;

		addUniform("transformation", transformation = new Type(EnumUniformType.MAT_4));
		addUniform("projection", projection = new Type(EnumUniformType.MAT_4));
		addUniform("view", view = new Type(EnumUniformType.MAT_4));

		initMatrixBuffers();
		createUniforms();

		shader.bind();
		setTransformation(new Matrix4f());
	}

	public void bind(Matrix4f view)
	{
		shader.bind();
		setView(view);
	}

	/* Base Uniforms */

	public void setTransformation(Matrix4f matrix4f)
	{
		setUniform(transformation, matrix4f);
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
