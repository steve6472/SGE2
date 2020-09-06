package steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.02.2020
 * Project: SJP
 *
 ***********************/
public abstract class AbstractLightShader3D<T extends ILightUniform> extends StaticShader3D implements ILightShader<T>
{
	public T[] lights;

	public AbstractLightShader3D(String path)
	{
		super(path);
	}

	public AbstractLightShader3D(Shader shader)
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
