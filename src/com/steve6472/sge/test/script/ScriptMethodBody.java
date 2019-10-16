package com.steve6472.sge.test.script;

import java.util.ArrayList;
import java.util.List;

import static com.steve6472.sge.test.script.ScriptTest.*;

class ScriptMethodBody
{
	String[] body;
	List<BodyLine> lines;
	ScriptMethod sm;

	ScriptMethodBody(ScriptMethod sm)
	{
		this.sm = sm;
		lines = new ArrayList<>();
	}

	void parse()
	{
		main : for (String s : body)
		{
			if (!s.equals("{") && !s.equals("}"))
			{
				for (ScriptMethod method : methods)
				{
					if (matchesMethod(s, method))
					{
						lines.add(new BodyLine(this, s).method(method));
						continue main;
					}
				}

				if (matchesBuiltinMethod(s))
				{
					lines.add(new BodyLine(this, s).builtinMethod());
					// continue;
				}
				else if (matchesVarDeclaration(s))
				{
					lines.add(new BodyLine(this, s).varDec());
				}
			}
		}
	}

	boolean matchesVarDeclaration(String s)
	{
		return (s.startsWith("int")
				|| s.startsWith("double")
				|| s.startsWith("String")
				|| s.startsWith("boolean"))
				&& (s.endsWith(";") && s.contains("="));
	}

	boolean matchesBuiltinMethod(String s)
	{
		if (checkBasicMethod(s, "print") || checkBasicMethod(s, "printErr"))
		{
			return true;
		}

		return false;
	}

	boolean matchesMethod(String s, ScriptMethod sm)
	{
		// If is a method
		if (checkBasicMethod(s, sm.name))
		{
			String[] parameters = getParameters(s);

			// If methods have the same amount of parameters
			if (sm.arguments.length != parameters.length)
				return false;

			// If parameters are the same type
			DataType[] scriptParameters = new DataType[parameters.length];
			for (int i = 0; i < parameters.length; i++)
			{
				scriptParameters[i] = getParameterType(parameters[i]);
				if (sm.arguments[i].type != scriptParameters[i] && scriptParameters[i] != DataType.UNKNOWN)
					return false;
			}

			return true;
		} else
		{
			return false;
		}
	}
}