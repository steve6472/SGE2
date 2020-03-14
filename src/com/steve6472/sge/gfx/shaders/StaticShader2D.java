package com.steve6472.sge.gfx.shaders;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.08.2019
 * Project: SJP
 *
 ***********************/
public abstract class StaticShader2D extends StaticShaderBase
{
	private final int transformation;
	private final int projection;

	private final FloatBuffer matrixBuffer;

	public StaticShader2D(String path)
	{
		shader = Shader.fromShaders(path);
		this.path = path;

		transformation = getUniform("transformation");
		projection = getUniform("projection");

		matrixBuffer = BufferUtils.createFloatBuffer(16);

		createUniforms();

		shader.bind();
		setTransformation(new Matrix4f());
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
