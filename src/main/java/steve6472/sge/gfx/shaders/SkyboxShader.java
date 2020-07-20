package steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 16.02.2020
 * Project: SJP
 *
 ***********************/
public class SkyboxShader extends StaticShaderCubeMap
{
	public static Type skybox;

	public SkyboxShader()
	{
		super("cubemap");
	}

	@Override
	protected void createUniforms()
	{
		addUniform("skybox", skybox = new Type(EnumUniformType.INT_1));
	}
}
