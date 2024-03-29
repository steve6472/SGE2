package steve6472.sge.gfx.shaders.shapes;

import steve6472.sge.gfx.shaders.StaticShader2D;

/**********************
 * Created by steve6472
 * On date: 29.4.2019
 * Project: SGE2
 *
 ***********************/
public final class DoubleBorderShader extends StaticShader2D
{
	public static Type OUTSIDEBORDER;
	public static Type INSIDEBORDER;
	public static Type FILL;
	public static Type SIZE;

	public DoubleBorderShader()
	{
		super("components/double_border");
	}

	@Override
	protected void createUniforms()
	{
		addUniform("outsideBorder", OUTSIDEBORDER = new Type(EnumUniformType.FLOAT_4));
		addUniform("insideBorder", INSIDEBORDER = new Type(EnumUniformType.FLOAT_4));
		addUniform("fill", FILL = new Type(EnumUniformType.FLOAT_4));
		addUniform("size", SIZE = new Type(EnumUniformType.FLOAT_2));
	}

}
