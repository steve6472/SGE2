package com.steve6472.sge.gfx.shaders;

import com.steve6472.sge.gfx.Shader;
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
public abstract class StaticShader3D extends StaticShaderBase
{
	private final int transformation;
	private final int projection;
	private final int view;

	private final FloatBuffer matrixBuffer;

	public StaticShader3D(String path)
	{
		shader = new Shader(path);
		this.path = path;

		transformation = getUniform("transformation");
		projection = getUniform("projection");
		view = getUniform("view");

		if (transformation == -1)
			throw new RuntimeException("Uniform name not found for transformation");
		if (projection == -1)
			throw new RuntimeException("Uniform name not found for projection");
		if (view == -1)
			throw new RuntimeException("Uniform name not found for view");

		matrixBuffer = BufferUtils.createFloatBuffer(16);

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

	public void setView(Matrix4f matrix4f)
	{
		matrixBuffer.clear();
		matrix4f.get(matrixBuffer);
		glUniformMatrix4fv(view, false, matrixBuffer);
	}
}
