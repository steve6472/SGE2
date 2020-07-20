package steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 19.07.2020
 * Project: FloatingDialogs
 *
 ***********************/
public class DialogShader extends StaticShader3D
{
	public static Type SAMPLER;

	public DialogShader()
	{
		super("dialog_shader");
	}

	@Override
	protected void createUniforms()
	{
		addUniform("sampler", SAMPLER = new Type(EnumUniformType.INT_1));
	}
}
