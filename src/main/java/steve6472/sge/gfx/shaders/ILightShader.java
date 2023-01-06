package steve6472.sge.gfx.shaders;

import java.util.function.Supplier;

import static org.lwjgl.opengl.GL20.*;

/**********************
 * Created by steve6472
 * On date: 06.09.2020
 * Project: StevesGameEngine
 *
 ***********************/
public interface ILightShader<T extends ILightUniform>
{
	T[] getLights();

	void setLights(T[] lights);

	/**
	 * @return Ideally should return {@code this}
	 */
	StaticShaderBase getBase();

	default void createLights(Supplier<T[]> array, Supplier<T> lightUniformConstructor)
	{
		T[] lights = array.get();
		setLights(lights);

		for (int i = 0; i < lights.length; i++)
		{
			T newUniform = lightUniformConstructor.get();
			for (ILightUniform.Uniform uniform : newUniform.getUniforms())
			{
				getBase().addUniform(newUniform.getArrayName() + "[" + i + "]." + uniform.getName(), uniform.setUniform(new StaticShaderBase.Type(uniform.getUniformType())));
			}
			lights[i] = newUniform;
		}
	}

	default void setUniform(ILightUniform.Uniform type, int i0)
	{
		if (type.getUniform().getId() == -1) return;
		if (type.getUniform().uniformType == StaticShaderBase.EnumUniformType.INT_1) glUniform1i(type.getUniform().getId(), i0);
	}

	default void setUniform(ILightUniform.Uniform type, float f0)
	{
		if (type.getUniform().getId() == -1) return;
		if (type.getUniform().uniformType == StaticShaderBase.EnumUniformType.FLOAT_1) glUniform1f(type.getUniform().getId(), f0);
	}

	default void setUniform(ILightUniform.Uniform type, float f0, float f1)
	{
		if (type.getUniform().getId() == -1) return;
		if (type.getUniform().uniformType == StaticShaderBase.EnumUniformType.FLOAT_2) glUniform2f(type.getUniform().getId(), f0, f1);
	}

	default void setUniform(ILightUniform.Uniform type, float f0, float f1, float f2)
	{
		if (type.getUniform().getId() == -1) return;
		if (type.getUniform().uniformType == StaticShaderBase.EnumUniformType.FLOAT_3) glUniform3f(type.getUniform().getId(), f0, f1, f2);
	}

	default void setUniform(ILightUniform.Uniform type, float f0, float f1, float f2, float f3)
	{
		if (type.getUniform().getId() == -1) return;
		if (type.getUniform().uniformType == StaticShaderBase.EnumUniformType.FLOAT_4) glUniform4f(type.getUniform().getId(), f0, f1, f2, f3);
	}
}
