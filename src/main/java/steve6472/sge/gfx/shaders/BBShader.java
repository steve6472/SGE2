package steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 3/19/2021
 * Project: NoiseGenerator
 *
 ***********************/
public class BBShader extends StaticShader3D
{
	public static Type ATLAS, NORMAL_MATRIX;

	public BBShader()
	{
		super("game/3d/entity");
	}

	@Override
	protected void createUniforms()
	{
		addUniform("atlas", ATLAS = new Type(EnumUniformType.INT_1));
		addUniform("normalMatrix", NORMAL_MATRIX = new Type(EnumUniformType.MAT_3));
	}
}
