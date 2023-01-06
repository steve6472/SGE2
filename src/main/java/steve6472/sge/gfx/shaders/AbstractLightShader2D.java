package steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472
 * On date: 13.02.2020
 * Project: SJP
 *
 ***********************/
public abstract class AbstractLightShader2D<T extends ILightUniform> extends StaticShader2D implements ILightShader<T>
{
	public T[] lights;

	public AbstractLightShader2D(String path)
	{
		super(path);
	}

	public AbstractLightShader2D(Shader shader)
	{
		super(shader);
	}

	@Override
	public T[] getLights()
	{
		return lights;
	}

	@Override
	public void setLights(T[] lights)
	{
		this.lights = lights;
	}

	@Override
	public StaticShaderBase getBase()
	{
		return this;
	}
}
