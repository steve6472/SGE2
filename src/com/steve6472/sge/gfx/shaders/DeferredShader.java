package com.steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.02.2020
 * Project: SJP
 *
 ***********************/
public class DeferredShader extends StaticShader2D
{
	public static Type position, normal, albedo, cameraPos;
	public static LightUniform[] lights;

	public DeferredShader()
	{
		super("deferred");
	}

	public void createLights(int lightCount)
	{
		lights = new LightUniform[lightCount];

		for (int i = 0; i < lightCount; i++)
		{
			lights[i] = new LightUniform(i);
		}
	}

	@Override
	protected void createUniforms()
	{
		addUniform("gPosition", position = new Type(EnumUniformType.INT_1));
		addUniform("gNormal", normal = new Type(EnumUniformType.INT_1));
		addUniform("gAlbedo", albedo = new Type(EnumUniformType.INT_1));

		addUniform("cameraPos", cameraPos = new Type(EnumUniformType.FLOAT_3));
	}

	public class LightUniform
	{
		public final Type position, color;

		public LightUniform(int index)
		{
			addUniform("lights[" + index + "].position", position = new Type(EnumUniformType.FLOAT_3));
			addUniform("lights[" + index + "].color", color = new Type(EnumUniformType.FLOAT_3));
		}
	}
}
