package steve6472.sge.gfx.shaders;

import org.joml.Matrix3f;
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
public abstract class StaticShaderBase
{
	protected AbstractShader shader;
	protected String path;

	protected FloatBuffer matrix4Buffer, matrix3Buffer;

	protected void initMatrixBuffers()
	{
		matrix4Buffer = BufferUtils.createFloatBuffer(16);
		matrix3Buffer = BufferUtils.createFloatBuffer(9);
	}

	public void bind()
	{
		shader.bind();
	}

	public AbstractShader getShader()
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

	public void setUniform(Type type, Matrix3f m0)
	{
		if (type.id == -1) return;
		if (type.uniformType == EnumUniformType.MAT_3)
		{
			matrix3Buffer.clear();
			m0.get(matrix3Buffer);
			glUniformMatrix3fv(type.getId(), false, matrix3Buffer);
		}
	}

	public void setUniform(Type type, Matrix4f m0)
	{
		if (type.id == -1) return;
		if (type.uniformType == EnumUniformType.MAT_4)
		{
			matrix4Buffer.clear();
			m0.get(matrix4Buffer);
			glUniformMatrix4fv(type.getId(), false, matrix4Buffer);
		}
	}

	public enum EnumUniformType
	{
		FLOAT_1, FLOAT_2, FLOAT_3, FLOAT_4,
		INT_1,
		MAT_3, MAT_4
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
}
