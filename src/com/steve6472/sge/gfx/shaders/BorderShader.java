package com.steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.08.2019
 * Project: SJP
 *
 ***********************/
public final class BorderShader extends StaticShader2D
{
	public BorderShader()
	{
		super("shaders\\components\\border");
	}

	public static Type BORDER;

	@Override
	protected void createUniforms()
	{
		addUniform("border", BORDER= new Type(EnumUniformType.FLOAT_4));
	}
}
