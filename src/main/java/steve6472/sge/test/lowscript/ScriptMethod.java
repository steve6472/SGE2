package steve6472.sge.test.lowscript;

import steve6472.sge.main.smartsave.DataType;

import java.util.Arrays;

class ScriptMethod
{
	String name;
	DataType returnType;
	ScriptArgument[] arguments;
	String methodString;
	ScriptMethodBody body;

	ScriptMethod()
	{
	}

	public void check()
	{
		for (String b : body.body)
		{
			String part = b.trim();
			if (part.startsWith("int") && part.contains("=") && part.endsWith(";"))
			{
				System.out.println("Declaration of INT: " + part);
			}
		}
	}

	@Override
	public String toString()
	{
		return "ScriptMethod{" + "name='" + name + '\'' + ", returnType=" + returnType + ", arguments=" + Arrays.toString(arguments) + ", body=" + Arrays.toString(body.body) + '}';
	}
}