package steve6472.sge.gfx.shaders;

import org.joml.Matrix4f;
import steve6472.sge.main.MainApp;

import static org.lwjgl.opengl.GL20.*;

/**********************
 * Created by steve6472
 * On date: 29.4.2019
 * Project: SGE2
 *
 ***********************/
public class FontShader
{
	private GeometryShader shader;

	private final int transformation;
	private final int projection;

	private final int sampler;
	private final int fontSize;
	private final int spriteData;
	private final int color;

	public FontShader()
	{
		shader = GeometryShader.fromShaders("components/font");

		transformation = glGetUniformLocation(shader.getProgram(), "transformation");
		projection = glGetUniformLocation(shader.getProgram(), "projection");

		sampler = glGetUniformLocation(shader.getProgram(), "sampler");
		fontSize = glGetUniformLocation(shader.getProgram(), "fontSize");
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
//		FloatBuffer b1 = BufferUtils.createFloatBuffer(16);
//		matrix4f.get(b1);
		glUniformMatrix4fv(transformation, false, MainApp.loadMatrix(matrix4f));
	}

	public void setProjection(Matrix4f matrix4f)
	{
//		FloatBuffer b1 = BufferUtils.createFloatBuffer(16);
//		matrix4f.get(b1);
		glUniformMatrix4fv(projection, false, MainApp.loadMatrix(matrix4f));
	}

	public void setSampler(int sampler)
	{
		glUniform1i(this.sampler, sampler);
	}

	public void setFontSize(float w, float h)
	{
		glUniform2f(this.fontSize, w, h);
	}

	public void setSpriteData(float x, float y, float w, float h)
	{
		glUniform4f(this.spriteData, x, y, w, h);
	}

	public void setColor(float r, float g, float b, float a)
	{
		glUniform4f(this.color, r, g, b, a);
	}

	public GeometryShader getShader()
	{
		return shader;
	}
}
