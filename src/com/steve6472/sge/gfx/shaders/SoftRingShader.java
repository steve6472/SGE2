package com.steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 29.4.2019
 * Project: SGE2
 *
 ***********************/
public class SoftRingShader extends StaticShader2D
{
	public static Type FILL, HOLE, SOFTNESS;

	public SoftRingShader()
	{
		super("shaders\\components\\smooth_ring");
	}

	@Override
	protected void createUniforms()
	{
		addUniform("fill", FILL = new Type(EnumUniformType.FLOAT_4));
		addUniform("hole", HOLE = new Type(EnumUniformType.FLOAT_1));
		addUniform("softness", SOFTNESS = new Type(EnumUniformType.FLOAT_1));
	}
}
