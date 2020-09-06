package steve6472.sge.gfx.shaders;

import org.joml.Matrix4f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.08.2019
 * Project: SJP
 *
 ***********************/
public abstract class StaticShader2D extends StaticShaderBase
{
	private final Type transformation;
	private final Type projection;

	public StaticShader2D(String path)
	{
		this(Shader.fromShaders(path));
		this.path = path;
	}

	public StaticShader2D(Shader shader)
	{
		this.shader = shader;

		addUniform("transformation", transformation = new Type(EnumUniformType.MAT_4));
		addUniform("projection", projection = new Type(EnumUniformType.MAT_4));

		initMatrixBuffers();
		createUniforms();

		shader.bind();
		setTransformation(new Matrix4f());
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
}
