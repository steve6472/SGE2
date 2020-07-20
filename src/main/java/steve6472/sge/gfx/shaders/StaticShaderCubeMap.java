package steve6472.sge.gfx.shaders;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 16.02.2020
 * Project: SJP
 *
 ***********************/
public abstract class StaticShaderCubeMap extends StaticShaderBase
{
	private final int projection;
	private final int view;

	private final FloatBuffer matrixBuffer;

	public StaticShaderCubeMap(String path)
	{
		shader = Shader.fromShaders(path);
		this.path = path;

		projection = getUniform("projection");
		view = getUniform("view");

		if (projection == -1)
			throw new RuntimeException("Uniform name not found for projection");
		if (view == -1)
			throw new RuntimeException("Uniform name not found for view");

		matrixBuffer = BufferUtils.createFloatBuffer(16);

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
