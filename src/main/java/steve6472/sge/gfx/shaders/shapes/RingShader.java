package steve6472.sge.gfx.shaders.shapes;

import steve6472.sge.gfx.shaders.StaticShader2D;

/**********************
 * Created by steve6472
 * On date: 29.4.2019
 * Project: SGE2
 *
 ***********************/
public final class RingShader extends StaticShader2D
{
	public static Type FILL, HOLE;

	public RingShader()
	{
		super("components/ring");
	}

	@Override
	protected void createUniforms()
	{
		addUniform("fill", FILL = new Type(EnumUniformType.FLOAT_4));
		addUniform("hole", HOLE = new Type(EnumUniformType.FLOAT_1));
	}
}
