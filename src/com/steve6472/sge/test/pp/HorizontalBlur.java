package com.steve6472.sge.test.pp;

import com.steve6472.sge.gfx.Shader;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gfx.post.Effect;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 31.03.2019
 * Project: SGE2
 *
 ***********************/
public class HorizontalBlur extends Effect
{
	public HorizontalBlur(int w, int h, int size)
	{
		super(new Shader(
				Shader.readFile("shaders\\game_test\\blur\\" + size + "\\blur_horizontal.vs"),
				Shader.readFile("shaders\\game_test\\blur\\" + size + "\\blur_fs.fs")),
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
