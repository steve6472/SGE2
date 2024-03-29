package steve6472.sge.gfx.shaders.shapes;

import steve6472.sge.gfx.shaders.StaticShader2D;

/**********************
 * Created by steve6472
 * On date: 13.08.2019
 * Project: SJP
 *
 ***********************/
public final class BorderShader extends StaticShader2D
{
	public BorderShader()
	{
		super("components/border");
	}

	public static Type BORDER;

	@Override
	protected void createUniforms()
	{
		addUniform("border", BORDER= new Type(EnumUniformType.FLOAT_4));
	}
}
