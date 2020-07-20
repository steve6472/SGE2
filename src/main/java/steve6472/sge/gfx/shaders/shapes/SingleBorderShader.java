package steve6472.sge.gfx.shaders.shapes;

import steve6472.sge.gfx.shaders.StaticShader2D;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 29.4.2019
 * Project: SGE2
 *
 ***********************/
public final class SingleBorderShader extends StaticShader2D
{
	public static Type BORDER, FILL, SIZE;

	public SingleBorderShader()
	{
		super("components/single_border");
	}

	@Override
	protected void createUniforms()
	{
		addUniform("border", BORDER = new Type(EnumUniformType.FLOAT_4));
		addUniform("fill", FILL = new Type(EnumUniformType.FLOAT_4));
		addUniform("size", SIZE = new Type(EnumUniformType.FLOAT_2));
	}
}
