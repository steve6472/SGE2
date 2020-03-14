package com.steve6472.sge.gfx.shaders;

import com.steve6472.sge.main.Util;

import static org.lwjgl.opengl.GL20.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.08.2019
 * Project: SJP
 *
 ***********************/
public abstract class StaticShaderBase
{
	protected Shader shader;
	protected String path;

	public void reload(Object... uniforms)
	{
		if (!Util.isEven(uniforms.length)) throw new IllegalArgumentException("Incorrect Uniform data!");

		shader.updateShader(path);

		for (int i = 0; i < uniforms.length / 2; i++)
		{
			addUniform((String) uniforms[i * 2], (Type) uniforms[i * 2 + 1]);
		}
	}

	public void bind()
	{
		shader.bind();
	}

	public Shader getShader()
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

	public void setUniform(Type type, Object... variables)
	{
		if (type.id == -1) return;

		switch (type.uniformType)
		{
			case FLOAT_1 -> set1f(type, (Float) variables[0]);
			case FLOAT_2 -> set2f(type, (Float) variables[0], (Float) variables[1]);
			case FLOAT_3 -> set3f(type, (Float) variables[0], (Float) variables[1], (Float) variables[2]);
			case FLOAT_4 -> set4f(type, (Float) variables[0], (Float) variables[1], (Float) variables[2], (Float) variables[3]);

			case INT_1 -> set1i(type, (Integer) variables[0]);
		}
	}

	private void set1f(Type type, float f0)
	{
		glUniform1f(type.getId(), f0);
	}

	private void set2f(Type type, float f0, float f1)
	{
		glUniform2f(type.getId(), f0, f1);
	}

	private void set3f(Type type, float f0, float f1, float f2)
	{
		glUniform3f(type.getId(), f0, f1, f2);
	}

	private void set4f(Type type, float f0, float f1, float f2, float f3)
	{
		glUniform4f(type.getId(), f0, f1, f2, f3);
	}

	private void set1i(Type type, int i0)
	{
		glUniform1i(type.getId(), i0);
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
}
