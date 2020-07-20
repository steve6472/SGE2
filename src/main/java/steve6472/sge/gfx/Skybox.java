package steve6472.sge.gfx;

import steve6472.sge.gfx.shaders.Shader;
import steve6472.sge.gfx.shaders.SkyboxShader;
import steve6472.sge.gfx.shaders.StaticShaderCubeMap;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 16.02.2020
 * Project: SJP
 *
 ***********************/
public class Skybox
{
	protected final StaticCubeMap skyboxTexture;
	protected final StaticShaderCubeMap shader;

	private final int vao;

	public Skybox(StaticCubeMap skyboxTexture, Matrix4f projection)
	{
		this.skyboxTexture = skyboxTexture;

		shader = createShader(projection);

		vao = VertexObjectCreator.createVAO();
		setCubeVertices();
		VertexObjectCreator.unbindVAO();
	}

	protected StaticShaderCubeMap createShader(Matrix4f projection)
	{
		StaticShaderCubeMap shader = new SkyboxShader();
		shader.setProjection(projection);
		shader.setUniform(SkyboxShader.skybox, 0);
		Shader.releaseShader();

		return shader;
	}

	public void updateProjection(Matrix4f projection)
	{
		shader.bind();
		shader.setProjection(projection);
	}

	public void render(Matrix4f view)
	{
		glDepthFunc(GL_LEQUAL);
		glDepthMask(false);

		shader.bind();
		shader.setView(new Matrix4f(new Matrix3f(view)));
		skyboxTexture.bind();
		VertexObjectCreator.basicRender(vao, 1, 36, GL_TRIANGLES);

		glDepthMask(true);
		glDepthFunc(GL_LESS);
	}

	private void setCubeVertices()
	{
		VertexObjectCreator.storeFloatDataInAttributeList(0, 3, new float[]
			{
			-1.0f, +1.0f, -1.0f,
			-1.0f, -1.0f, -1.0f,
			+1.0f, -1.0f, -1.0f,
			+1.0f, -1.0f, -1.0f,
			+1.0f, +1.0f, -1.0f,
			-1.0f, +1.0f, -1.0f,

			-1.0f, -1.0f, +1.0f,
			-1.0f, -1.0f, -1.0f,
			-1.0f, +1.0f, -1.0f,
			-1.0f, +1.0f, -1.0f,
			-1.0f, +1.0f, +1.0f,
			-1.0f, -1.0f, +1.0f,

			+1.0f, -1.0f, -1.0f,
			+1.0f, -1.0f, +1.0f,
			+1.0f, +1.0f, +1.0f,
			+1.0f, +1.0f, +1.0f,
			+1.0f, +1.0f, -1.0f,
			+1.0f, -1.0f, -1.0f,

			-1.0f, -1.0f, +1.0f,
			-1.0f, +1.0f, +1.0f,
			+1.0f, +1.0f, +1.0f,
			+1.0f, +1.0f, +1.0f,
			+1.0f, -1.0f, +1.0f,
			-1.0f, -1.0f, +1.0f,

			-1.0f,  +1.0f, -1.0f,
			+1.0f,  +1.0f, -1.0f,
			+1.0f,  +1.0f, +1.0f,
			+1.0f,  +1.0f, +1.0f,
			-1.0f,  +1.0f, +1.0f,
			-1.0f,  +1.0f, -1.0f,

			-1.0f, -1.0f, -1.0f,
			-1.0f, -1.0f, +1.0f,
			+1.0f, -1.0f, -1.0f,
			+1.0f, -1.0f, -1.0f,
			-1.0f, -1.0f, +1.0f,
			+1.0f, -1.0f, +1.0f
			});
	}
}
