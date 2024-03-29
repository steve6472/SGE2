package steve6472.sge.test.pp;

import steve6472.sge.gfx.shaders.Shader;
import steve6472.sge.gfx.Sprite;
import steve6472.sge.gfx.post.Effect;

/**********************
 * Created by steve6472
 * On date: 31.03.2019
 * Project: SGE2
 *
 ***********************/
public class VerticalBlur extends Effect
{
	public VerticalBlur(int w, int h, int size)
	{
		super(Shader.fromResource(
				"/shaders/game_test/blur/" + size + "/blur_vertical.vs",
				"/shaders/game_test/blur/" + size + "/blur_fs.fs"),
				w,
				h);
	}

	@Override
	public void applyShader(int texture)
	{
		shader.bind();
		Sprite.bind(0, texture);
		render();
		Shader.releaseShader();
	}
}
