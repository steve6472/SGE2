package com.steve6472.sge.test.script;

import java.util.Arrays;
import java.util.HashMap;

class ScriptMethod
{
	String name;
	DataType returnType;
	ScriptArgument[] arguments;
	String methodString;
	ScriptMethodBody body;

	ScriptMethod()
	{
		methodMemory = new HashMap<>();
	}

	HashMap<Integer, MemoryObject> methodMemory;

	void call(Pointer... pointers)
	{
//		Arrays.stream(parameters).map(parameter -> ">> " + parameter).forEach(System.out::println);
		body.lines.forEach(bodyLine -> bodyLine.call(pointers));
	}

	int initNewVariable(String name, Object value, DataType type)
	{
		MemoryObject mo = new MemoryObject(name, value, type);
		mo.address = methodMemory.size() + 1;
		methodMemory.put(methodMemory.size() + 1, mo);
//		System.out.println("Method (" + this.name + ") Var: " + mo);
		return methodMemory.size();
	}

	private static MemoryObject lastMemoryObject;

	boolean isParameter(String name)
	{
		for (MemoryObject value : methodMemory.values())
		{
//			System.out.println("### " + name + " " + value);
			if (value.name.equals(name))
			{
				lastMemoryObject = value;
				return true;
			}
		}
		return false;
	}

	MemoryObject getLastMemoryObject()
	{
		return lastMemoryObject;
	}

	@Override
	public String toString()
	{
		return "ScriptMethod{" + "name='" + name + '\'' + ", returnType=" + returnType + ", arguments=" + Arrays.toString(arguments) + ", body=" + Arrays.toString(body.body) + '}';
	}
}