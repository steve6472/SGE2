package steve6472.sge.gfx.post;

import steve6472.sge.gfx.FrameBuffer;
import steve6472.sge.gfx.shaders.Shader;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

/**********************
 * Created by steve6472
 * On date: 31.03.2019
 * Project: SGE2
 *
 ***********************/
public abstract class Effect
{
	protected Shader shader;
	FrameBuffer out;
	int width, height;

	public Effect(Shader shader, int width, int height)
	{
		this.shader = shader;
		this.width = width;
		this.height = height;
		out = new FrameBuffer(width, height, false);
	}

	protected void render()
	{
		out.bindFrameBuffer(width, height);
		FrameBuffer.clearCurrentBuffer();
		glDrawArrays(GL_TRIANGLES, 0, 6);

		//TODO
		out.unbindCurrentFrameBuffer(16, 9);
	}

	public abstract void applyShader(int texture);

	public FrameBuffer getOut()
	{
		return out;
	}

	public int getOutTexture()
	{
		return getOut().texture;
	}
}
