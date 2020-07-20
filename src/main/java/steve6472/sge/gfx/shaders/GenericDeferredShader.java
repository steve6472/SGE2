package steve6472.sge.gfx.shaders;

import java.util.function.Supplier;

import static org.lwjgl.opengl.GL20.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.02.2020
 * Project: SJP
 *
 ***********************/
public class GenericDeferredShader<T extends ILightUniform> extends StaticShader2D
{
	public static Type position, normal, albedo, cameraPos;
	public T[] lights;

	public GenericDeferredShader()
	{
		super("deferred");
	}

	public GenericDeferredShader(String path)
	{
		super(path);
	}

	public GenericDeferredShader(Shader shader)
	{
		super(shader);
	}

	public void createLights(Supplier<T[]> array, Supplier<T> lightUniformConstructor)
	{
		lights = array.get();

		for (int i = 0; i < lights.length; i++)
		{
			T newUniform = lightUniformConstructor.get();
			for (ILightUniform.Uniform uniform : newUniform.getUniforms())
			{
				addUniform(newUniform.getArrayName() + "[" + i + "]." + uniform.getName(), uniform.setUniform(new Type(uniform.getUniformType())));
			}
			lights[i] = newUniform;
		}
	}

	public void setUniform(ILightUniform.Uniform type, Object... variables)
	{
		if (type.getUniform().getId() == -1) return;

		Type uniformType = type.getUniform();

		switch (type.getUniform().uniformType)
		{
			case FLOAT_1 -> set1f(uniformType, (Float) variables[0]);
			case FLOAT_2 -> set2f(uniformType, (Float) variables[0], (Float) variables[1]);
			case FLOAT_3 -> set3f(uniformType, (Float) variables[0], (Float) variables[1], (Float) variables[2]);
			case FLOAT_4 -> set4f(uniformType, (Float) variables[0], (Float) variables[1], (Float) variables[2], (Float) variables[3]);

			case INT_1 -> set1i(uniformType, (Integer) variables[0]);
		}
	}

	@Override
	protected void createUniforms()
	{
		addUniform("gPosition", position = new Type(EnumUniformType.INT_1));
		addUniform("gNormal", normal = new Type(EnumUniformType.INT_1));
		addUniform("gAlbedo", albedo = new Type(EnumUniformType.INT_1));

		addUniform("cameraPos", cameraPos = new Type(EnumUniformType.FLOAT_3));
	}







	private void set1f(Type type, float f0)
	{
		glUniform1f(type.getId(), f0);
	}

	private void set2f(Type type, float f0, float f1)
	{
		glUniform2f(type.getId(), f0, f1);
	}

	private void set3f(Type type, float f0, float f1, float f2)
	{
		glUniform3f(type.getId(), f0, f1, f2);
	}

	private void set4f(Type type, float f0, float f1, float f2, float f3)
	{
		glUniform4f(type.getId(), f0, f1, f2, f3);
	}

	private void set1i(Type type, int i0)
	{
		glUniform1i(type.getId(), i0);
	}
}
