package steve6472.sge.gfx.shaders;

import org.joml.Matrix4f;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.08.2019
 * Project: SJP
 *
 ***********************/
public abstract class StaticGeometryShader3D extends StaticShaderBase
{
	protected final Type transformation;
	protected final Type projection;
	protected final Type view;

	public StaticGeometryShader3D(String path)
	{
		this(GeometryShader.fromShaders(path));
		this.path = path;
	}

	public StaticGeometryShader3D(GeometryShader shader)
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

	public void bind()
	{
		shader.bind();
	}

	public void bind(Matrix4f viewMatrix)
	{
		shader.bind();
		setView(viewMatrix);
	}

	public AbstractShader getShader()
	{
		return shader;
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
