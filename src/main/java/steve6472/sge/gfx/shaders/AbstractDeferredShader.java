package steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472
 * On date: 13.02.2020
 * Project: SJP
 *
 ***********************/
public class AbstractDeferredShader<T extends ILightUniform> extends AbstractLightShader2D<T>
{
	public static Type position, normal, albedo, cameraPos;

	public AbstractDeferredShader()
	{
		super("deferred");
	}

	public AbstractDeferredShader(String path)
	{
		super(path);
	}

	public AbstractDeferredShader(Shader shader)
	{
		super(shader);
	}

	@Override
	protected void createUniforms()
	{
		addUniform("gPosition", position = new Type(EnumUniformType.INT_1));
		addUniform("gNormal", normal = new Type(EnumUniformType.INT_1));
		addUniform("gAlbedo", albedo = new Type(EnumUniformType.INT_1));

		addUniform("cameraPos", cameraPos = new Type(EnumUniformType.FLOAT_3));
	}
}
