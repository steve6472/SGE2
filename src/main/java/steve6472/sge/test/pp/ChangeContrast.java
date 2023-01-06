package steve6472.sge.test.pp;

import steve6472.sge.gfx.shaders.Shader;
import steve6472.sge.gfx.Sprite;
import steve6472.sge.gfx.post.Effect;

/**********************
 * Created by steve6472
 * On date: 16.08.2019
 * Project: SJP
 *
 ***********************/
public class ChangeContrast extends Effect
{
	public ChangeContrast(int width, int height)
	{
		super(Shader.fromShaders("game_test\\contrast"), width, height);
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
