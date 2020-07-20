package steve6472.sge.test.script;

import steve6472.sge.main.Util;
import steve6472.sge.main.util.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static steve6472.sge.test.script.DataType.UNKNOWN;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 13.04.2019
 * Project: SJP
 *
 ***********************/
public class ScriptTest
{
	public static void main(String[] args)
	{
		new ScriptTest().test();
	}

	static List<ScriptMethod> methods;
	static HashMap<Integer, MemoryObject> globalMemory;

	public void test()
	{
		methods = new ArrayList<>();
		globalMemory = new HashMap<>();

		String[] file = Util.loadDataFromFile("test.java");
		int index = 0;

		for (String s : file)
		{
			s = s.trim();
			if (!s.isBlank() && !s.startsWith("//"))
			{
				ScriptMethod sm;
				if ((sm = checkForMethod(s)) != null)
				{
					methods.add(sm);
					checkForMethodBody(file, index, sm);
					System.out.println(sm);
				}

				if (sm == null)
				{
					if (tryNewGlobalVar(s))
					{
						System.out.println("Global Var Dec: " + s);
					}
				}
			}
			index++;
		}

		System.out.println("\n\n\n\n");

		for (ScriptMethod method : methods)
		{
			if (method.name.equals("main") && method.returnType == DataType.VOID)
			{
				method.initNewVariable("r0", rng(), DataType.STRING);
				method.initNewVariable("r1", rng(), DataType.STRING);
				method.call();
			}
		}
	}

	static boolean tryNewGlobalVar(String s)
	{
		if (s.startsWith("static"))
		{
			DataType dt = parseDataType(s.substring(s.indexOf(' ') + 1, s.indexOf(' ', 7)));
			if (dt != UNKNOWN)
			{
				String name = s.substring(8 + dt.name().length(), s.indexOf(' ', 8 + dt.name().length())).trim();
				String val = s.substring(s.indexOf('=') + 1, s.length() - 1).trim();
				initNewGlobalVariable(name, val, dt);
				return true;
			}
		}

		return false;
	}

	private static MemoryObject lastMemoryObject;

	static boolean isParameterGlobal(String name)
	{
		for (MemoryObject value : globalMemory.values())
		{
		    if (value.name.equals(name))
		    {
		    	lastMemoryObject = value;
			    return true;
		    }
		}
		return false;
	}

	static MemoryObject getLastMemoryObject()
	{
		return lastMemoryObject;
	}

	static int initNewGlobalVariable(String name, Object value, DataType type)
	{
		MemoryObject mo = new MemoryObject(name, value, type);
		globalMemory.put(globalMemory.size() + 1, mo);
		mo.address = globalMemory.size();
		return globalMemory.size();
	}

	static int rng()
	{
		int random = RandomUtil.randomInt(0, Integer.MAX_VALUE - 1);
		random = (RandomUtil.flipACoin() ? random : -random);
		return random;
	}

	static boolean checkForMethodBody(String[] file, int index, ScriptMethod sm)
	{
		String line = file[index].trim();

		boolean methodStart = false;
		List<String> methodBody = new ArrayList<>();

		if (sm != null)
		{
			if (line.substring(sm.methodString.length()).trim().startsWith("{"))
			{
				methodStart = true;
				methodBody.add("{");
			} else if (file[index + 1].trim().startsWith("{"))
			{
				methodStart = true;
				sm = methods.get(methods.size() - 1);
			}
		}

		if (methodStart)
		{
			for (int i = index + 1; i < file.length; i++)
			{
				String l = file[i].trim();
				if (l.isBlank())
					continue;
				methodBody.add(l);
				if (l.charAt(l.length() - 1) == '}')
				{
					break;
				}
			}

			if (sm != null)
			{
				sm.body.body = methodBody.toArray(new String[0]);
				sm.body.parse();
			}

			return true;
		}


		return false;
	}

	static ScriptMethod checkForMethod(String line)
	{
		ScriptMethod sm;
		try
		{
			String method = getMethod(line);

			String returnType = getReturnType(method);
			String name = getMethodName(method);
			String[] stringArguments = getArguments(method);
			ScriptArgument[] arguments = parseArguments(stringArguments);

			sm = new ScriptMethod();
			sm.name = name;
			sm.returnType = parseDataType(returnType);
			sm.arguments = arguments;
			sm.methodString = method;
			sm.body = new ScriptMethodBody(sm);

		} catch (Exception ex)
		{
//			System.err.println(line);
//			ex.printStackTrace();
			return null;
		}
		return sm;
	}

	static String getMethod(String line)
	{
		return line.substring(0, line.lastIndexOf(')') + 1);
	}

	static String getReturnType(String line)
	{
		return line.substring(0, line.indexOf(' ')).trim();
	}

	static String getMethodName(String line)
	{
		return line.substring(line.indexOf(' '), line.indexOf('(')).trim();
	}

	static String[] getArguments(String line)
	{
		String s = line.substring(line.indexOf('(') + 1, line.length() - 1).trim();

		String[] r = s.split(",");
		for (int i = 0; i < r.length; i++)
		{
			r[i] = r[i].trim();
		}

		return r;
	}

	static ScriptArgument[] parseArguments(String[] stringArguments)
	{
		ScriptArgument[] sa = new ScriptArgument[stringArguments.length];

		for (int i = 0; i < stringArguments.length; i++)
		{
			String t = stringArguments[i];

			if (t.isBlank())
				return new ScriptArgument[0];

			String stringData = t.substring(0, t.indexOf(' ')).trim();
			String varName = t.substring(t.indexOf(' ')).trim();

			ScriptArgument s = new ScriptArgument();
			s.name = varName;
			s.type = parseDataType(stringData);

			sa[i] = s;
		}

		return sa;
	}

	static DataType parseDataType(String type)
	{
		return switch (type.toLowerCase())
				{
					case "void" -> DataType.VOID;
					case "int" -> DataType.INT;
					case "double" -> DataType.DOUBLE;
					case "string" -> DataType.STRING;
					case "boolean" -> DataType.BOOLEAN;
					default -> {
						System.err.println("Unknown Variable '" + type + "'");
						yield DataType.UNKNOWN;
					}
				};
	}

	public static DataType[] parseDataTypes(String[] types)
	{
		DataType[] dataTypes = new DataType[types.length];

		for (int i = 0; i < types.length; i++)
		{
			dataTypes[i] = parseDataType(types[i]);
		}

		return dataTypes;
	}

	public static String[] getParameters(String s)
	{
		String l = s.substring(s.indexOf('(') + 1, s.lastIndexOf(')'));
		return l.split(",");
	}

	public static DataType getParameterType(String s)
	{
		if (s.equals("true") || s.equals("false"))
			return DataType.BOOLEAN;

		if (Util.isPointNumber(s))
		{
			if (s.contains("."))
				return DataType.DOUBLE;
			else
				return DataType.INT;
		}
		else if (s.startsWith("\"") && s.endsWith("\""))
		{
			return DataType.STRING;
		}

		return UNKNOWN;
	}

	static boolean checkBasicMethod(String s, String methodName)
	{
		return s.startsWith(methodName + "(") && s.endsWith(");");
	}

}












