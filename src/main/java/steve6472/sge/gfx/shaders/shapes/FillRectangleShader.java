package steve6472.sge.gfx.shaders.shapes;

import steve6472.sge.gfx.shaders.StaticShader2D;

/**********************
 * Created by steve6472
 * On date: 29.4.2019
 * Project: SGE2
 *
 ***********************/
public final class FillRectangleShader extends StaticShader2D
{
	public static Type FILL;

	public FillRectangleShader()
	{
		super("components/fill_rectangle");
	}

	@Override
	protected void createUniforms()
	{
		addUniform("fill", FILL = new Type(EnumUniformType.FLOAT_4));
	}
}
