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
public class Combine extends Effect
{
	public Combine(int w, int h)
	{
		super(Shader.fromShaders("game_test/game_test_combine"), w, h);
		shader.setUniform1i("sampler0", 0);
		shader.setUniform1i("sampler1", 1);
	}

	@Override
	public void applyShader(int texture)
	{
	}

	public void applyShader(int texture, int blur)
	{
		shader.bind();
		shader.setUniform1i("sampler0", 0);
		shader.setUniform1i("sampler1", 1);
		Sprite.bind(0, texture);
		Sprite.bind(1, blur);
		render();
		Shader.releaseShader();

		Sprite.bind(1, 0);
		Sprite.bind(0, 0);
	}
}
