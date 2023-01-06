package steve6472.sge.gfx.shaders;

import java.util.function.Supplier;

/**********************
 * Created by steve6472
 * On date: 13.02.2020
 * Project: SJP
 *
 ***********************/
public class DeferredShader extends StaticShader2D
{
	public static Type position, normal, albedo, cameraPos;
	public static ILightUniform[] lights;

	public DeferredShader()
	{
		super("deferred");
	}

	public DeferredShader(String path)
	{
		super(path);
	}

	public DeferredShader(Shader shader)
	{
		super(shader);
	}

	public void createLights(Supplier<? extends ILightUniform> lightUniformConstructor, int lightCount)
	{
		lights = new ILightUniform[lightCount];

		for (int i = 0; i < lightCount; i++)
		{
			ILightUniform newUniform = lightUniformConstructor.get();
			for (ILightUniform.Uniform uniform : newUniform.getUniforms())
			{
				addUniform(newUniform.getArrayName() + "[" + i + "]." + uniform.getName(), uniform.setUniform(new Type(uniform.getUniformType())));
			}
			lights[i] = newUniform;
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
}
