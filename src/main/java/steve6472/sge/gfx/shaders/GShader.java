package steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472
 * On date: 25.01.2020
 * Project: SJP
 *
 ***********************/
public class GShader extends StaticShader3D
{
	public static Type albedo, normal;

	public GShader()
	{
		super("g");
	}

	public GShader(String path)
	{
		super(path);
	}

	@Override
	protected void createUniforms()
	{
		addUniform("albedo", albedo = new Type(EnumUniformType.INT_1));
		addUniform("normal", normal = new Type(EnumUniformType.INT_1));
	}
}
