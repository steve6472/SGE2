package steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472
 * On date: 29.4.2019
 * Project: SGE2
 *
 ***********************/
public class SpriteAtlasShader extends StaticShader2D
{
	public static Type SAMPLER;
	public static Type SPRITEDATA;

	public SpriteAtlasShader()
	{
		super("components/sprite_atlas_shader");
	}

	@Override
	protected void createUniforms()
	{
		addUniform("sampler", SAMPLER = new Type(EnumUniformType.INT_1));
		addUniform("spriteData", SPRITEDATA = new Type(EnumUniformType.FLOAT_4));
	}
}
