package steve6472.sge.gfx.shaders;

/**********************
 * Created by steve6472
 * On date: 27.03.2020
 * Project: StevesGameEngine
 *
 ***********************/
public abstract class ILightUniform
{
	public abstract String getArrayName();

	public abstract Uniform[] getUniforms();

	public static class Uniform
	{
		private final String name;
		private final StaticShaderBase.EnumUniformType uniformType;

		private StaticShaderBase.Type uniform;

		public Uniform(String name, StaticShaderBase.EnumUniformType uniformType)
		{
			this.name = name;
			this.uniformType = uniformType;
		}

		public StaticShaderBase.Type setUniform(StaticShaderBase.Type type)
		{
			this.uniform = type;
			return uniform;
		}

		public String getName()
		{
			return name;
		}

		public StaticShaderBase.Type getUniform()
		{
			return uniform;
		}

		public StaticShaderBase.EnumUniformType getUniformType()
		{
			return uniformType;
		}
	}
}
