package com.steve6472.sge.gfx.shaders;

import com.steve6472.sge.gfx.Shader;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 29.4.2019
 * Project: SGE2
 *
 ***********************/
public class FontShader_backup
{
	private Shader shader;

	private final int transformation;
	private final int projection;

	private final int sampler;
	private final int spriteData;
	private final int color;

	public FontShader_backup()
	{
		shader = new Shader("shaders\\components\\font");

		transformation = glGetUniformLocation(shader.getProgram(), "transformation");
		projection = glGetUniformLocation(shader.getProgram(), "projection");

		sampler = glGetUniformLocation(shader.getProgram(), "sampler");
		spriteData = glGetUniformLocation(shader.getProgram(), "spriteData");
		color = glGetUniformLocation(shader.getProgram(), "color");

		shader.bind();
		setTransformation(new Matrix4f());
	}

	public void bind()
	{
		shader.bind();
	}

	public void setTransformation(Matrix4f matrix4f)
	{
		FloatBuffer b1 = BufferUtils.createFloatBuffer(16);
		matrix4f.get(b1);
		glUniformMatrix4fv(transformation, false, b1);
	}

	public void setProjection(Matrix4f matrix4f)
	{
		FloatBuffer b1 = BufferUtils.createFloatBuffer(16);
		matrix4f.get(b1);
		glUniformMatrix4fv(projection, false, b1);
	}

	public void setSampler(int sampler)
	{
		glUniform1i(this.sampler, sampler);
	}

	public void setSpriteData(float x, float y, float w, float h)
	{
		glUniform4f(this.spriteData, x, y, w, h);
	}

	public void setColor(float r, float g, float b, float a)
	{
		glUniform4f(this.color, r, g, b, a);
	}

	public Shader getShader()
	{
		return shader;
	}
}
