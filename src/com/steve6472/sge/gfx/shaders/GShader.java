package com.steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 25.01.2020
 * Project: SJP
 *
 ***********************/
public class GShader extends StaticShader3D
{
	public static Type albedo, normal;

	public GShader()
	{
		super("g", true);
	}

	@Override
	protected void createUniforms()
	{
		addUniform("albedo", albedo = new Type(EnumUniformType.INT_1));
		addUniform("normal", normal = new Type(EnumUniformType.INT_1));
	}
}
