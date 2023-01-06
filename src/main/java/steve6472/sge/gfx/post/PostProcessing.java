package steve6472.sge.gfx.post;

import steve6472.sge.gfx.FrameBuffer;
import steve6472.sge.main.events.Event;
import steve6472.sge.main.events.WindowSizeEvent;
import steve6472.sge.test.pp.*;

import java.util.ArrayList;
import java.util.List;

import static steve6472.sge.gfx.VertexObjectCreator.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**********************
 * Created by steve6472
 * On date: 31.03.2019
 * Project: SGE2
 *
 ***********************/
public class PostProcessing
{
	FrameBuffer out;

	int vao, v_vbo, t_vbo;
	int width, height;

	List<Effect> steps;
	public Effect contrast, brightness, hBlur, vBlur;
	public Combine combine;

	public PostProcessing(int width, int height)
	{
		vao = createVAO();

		v_vbo = storeFloatDataInAttributeList(0, 2, new float[] { -1, +1, -1, -1, +1, -1, +1, -1, +1, +1, -1, +1 });
//		t_vbo = storeDataInAttributeList(1, 2, new float[] { 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0 });
		t_vbo = storeFloatDataInAttributeList(1, 2, new float[] { 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1 });

		unbindVAO();

		steps = new ArrayList<>();

		out = new FrameBuffer(width, height);
		this.width = width;
		this.height = height;
		contrast = new ChangeContrast(width, height);
		brightness = new Brightness(width, height);
		hBlur = new HorizontalBlur(width, height, 11);
		vBlur = new VerticalBlur(width, height, 11);
		combine = new Combine(width, height);
	}

	@Event
	public void update(WindowSizeEvent e)
	{
		int w = e.getWidth();
		int h = e.getHeight();
		update(w, h, contrast);
		update(w, h, brightness);
		update(w, h, hBlur);
		update(w, h, vBlur);
		update(w, h, combine);
	}

	private void update(int w, int h, Effect e)
	{
		e.getOut().resize(w, h);
		e.width = w;
		e.height = h;
	}

	public void doPostProcessing(int colorTexture)
	{
		start();
		brightness.applyShader(colorTexture);
		hBlur.applyShader(brightness.getOutTexture());
		vBlur.applyShader(hBlur.getOutTexture());
		combine.applyShader(colorTexture, vBlur.getOutTexture());
		contrast.applyShader(combine.getOutTexture());
		end();
	}

	private void start()
	{
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
//		glDisable(GL_DEPTH_TEST);
	}

	private void end()
	{
//		glEnable(GL_DEPTH_TEST);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
}
