package steve6472.sge.test.pp;

import steve6472.sge.gfx.shaders.Shader;
import steve6472.sge.gfx.Sprite;
import steve6472.sge.gfx.post.Effect;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 31.03.2019
 * Project: SGE2
 *
 ***********************/
public class Brightness extends Effect
{
	public Brightness(int w, int h)
	{
		super(Shader.fromShaders("game_test\\blur\\bright_filter"), w, h);
		shader.setUniform1i("sampler", 0);
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
