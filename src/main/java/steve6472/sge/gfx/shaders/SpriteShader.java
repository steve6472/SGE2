package steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472
 * On date: 29.4.2019
 * Project: SGE2
 *
 ***********************/
public class SpriteShader extends StaticShader2D
{
	public static Type SAMPLER;

	public SpriteShader()
	{
		super("components/sprite_shader");
	}

	@Override
	protected void createUniforms()
	{
		addUniform("sampler", SAMPLER = new Type(EnumUniformType.INT_1));
	}
}
