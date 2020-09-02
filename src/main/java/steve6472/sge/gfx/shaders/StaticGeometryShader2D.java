package steve6472.sge.gfx.shaders;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.08.2019
 * Project: SJP
 *
 ***********************/
public abstract class StaticGeometryShader2D
{
	private GeometryShader shader;

	private String path;

	private final int transformation;
	private final int projection;

	private final FloatBuffer matrixBuffer;

	public StaticGeometryShader2D(String path)
	{
		shader = GeometryShader.fromShaders(path);
		this.path = path;

		transformation = getUniform("transformation");
		projection = getUniform("projection");

		matrixBuffer = BufferUtils.createFloatBuffer(16);

		createUniforms();

		shader.bind();
		setTransformation(new Matrix4f());
	}

//	public void reload(Object... uniforms)
//	{
//		if (!Util.isEven(uniforms.length)) throw new IllegalArgumentException("Incorrect Uniform data!");
//
//		shader.updateShader(path);
//
//		for (int i = 0; i < uniforms.length / 2; i++)
//		{
//			addUniform((String) uniforms[i * 2], (Type) uniforms[i * 2 + 1]);
//		}
//	}

	public void bind()
	{
		shader.bind();
	}

	public GeometryShader getShader()
	{
		return shader;
	}

	protected abstract void createUniforms();

	protected int getUniform(String name)
	{
		return glGetUniformLocation(shader.getProgram(), name);
	}

	protected void addUniform(String name, Type type)
	{
		type.setId(getUniform(name));
	}

	public void setUniform(Type type, float f0)
	{
		if (type.id == -1) return;
		if (type.uniformType == EnumUniformType.FLOAT_1) glUniform1f(type.getId(), f0);
	}

	public void setUniform(Type type, float f0, float f1)
	{
		if (type.id == -1) return;
		if (type.uniformType == EnumUniformType.FLOAT_2) glUniform2f(type.getId(), f0, f1);
	}

	public void setUniform(Type type, float f0, float f1, float f2)
	{
		if (type.id == -1) return;
		if (type.uniformType == EnumUniformType.FLOAT_3) glUniform3f(type.getId(), f0, f1, f2);
	}

	public void setUniform(Type type, float f0, float f1, float f2, float f3)
	{
		if (type.id == -1) return;
		if (type.uniformType == EnumUniformType.FLOAT_4) glUniform4f(type.getId(), f0, f1, f2, f3);
	}

	public void setUniform(Type type, int i0)
	{
		if (type.id == -1) return;
		if (type.uniformType == EnumUniformType.INT_1) glUniform1i(type.getId(), i0);
	}

	protected enum EnumUniformType
	{
		FLOAT_1, FLOAT_2, FLOAT_3, FLOAT_4,
		INT_1
	}

	protected static class Type
	{
		private int id;

		EnumUniformType uniformType;

		public Type(EnumUniformType uniformType)
		{
			this.uniformType = uniformType;
		}

		private void setId(int id)
		{
			this.id = id;
		}

		public int getId()
		{
			return id;
		}

		@Override
		public String toString()
		{
			return "Type{" + "id=" + id + ", uniformType=" + uniformType + '}';
		}
	}

	/* Base Uniforms */

	public void setTransformation(Matrix4f matrix4f)
	{
		matrixBuffer.clear();
		matrix4f.get(matrixBuffer);
		glUniformMatrix4fv(transformation, false, matrixBuffer);
	}

	public void setProjection(Matrix4f matrix4f)
	{
		matrixBuffer.clear();
		matrix4f.get(matrixBuffer);
		glUniformMatrix4fv(projection, false, matrixBuffer);
	}
}
