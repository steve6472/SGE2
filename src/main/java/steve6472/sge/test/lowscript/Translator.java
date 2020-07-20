package steve6472.sge.test.lowscript;

import steve6472.sge.main.Util;
import steve6472.sge.main.smartsave.DataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 23.06.2019
 * Project: SJP
 *
 ***********************/
public class Translator
{
	static HashMap<Integer, Boolean> usedIds = new HashMap<>();
	static List<ScriptMethod> methods;

	/*

	0 - 15 -> passed variables
	16 - 271 -> global variables
	272 - 782 -> method variables

	 */

	public static void main(String[] args)
	{
		List<String> code = new ArrayList<>();
		methods = new ArrayList<>();

		String[] file = Util.loadDataFromFile("lowTestTranslate.java");

		for (int i = 0; i < file.length; i++)
		{
			String s = file[i];
			s = s.trim();
			if (!s.isBlank() && !s.startsWith("//"))
			{
				ScriptMethod sm;
				if ((sm = checkForMethod(s)) != null)
				{
					methods.add(sm);
					checkForMethodBody(file, i, sm);
					System.out.println(sm);
					sm.check();
				}
			}
		}

		code.forEach(System.out::println);

	}

	private static int getMethodId()
	{
		for (int i = 272; i < 782; i++)
		{
			if (usedIds.containsKey(i))
			{
				if (!usedIds.get(i))
				{
					usedIds.put(i, true);
					return i;
				}
			} else
			{
				usedIds.put(i, true);
				return i;
			}
		}

		return -1;
	}

	private static boolean checkForMethodBody(String[] file, int index, ScriptMethod sm)
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

	private static ScriptMethod checkForMethod(String line)
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

	private static String getMethod(String line)
	{
		return line.substring(0, line.lastIndexOf(')') + 1);
	}

	private static String getReturnType(String line)
	{
		return line.substring(0, line.indexOf(' ')).trim();
	}

	private static String getMethodName(String line)
	{
		return line.substring(line.indexOf(' '), line.indexOf('(')).trim();
	}

	private static String[] getArguments(String line)
	{
		String s = line.substring(line.indexOf('(') + 1, line.length() - 1).trim();

		String[] r = s.split(",");
		for (int i = 0; i < r.length; i++)
		{
			r[i] = r[i].trim();
		}

		return r;
	}

	private static ScriptArgument[] parseArguments(String[] stringArguments)
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

	private static DataType parseDataType(String type)
	{
		return switch (type.toLowerCase())
				{
					case "void" -> null;
					case "int" -> DataType.INT;
					case "double" -> DataType.DOUBLE;
					case "string" -> DataType.STRING;
					case "boolean" -> DataType.BOOLEAN;
					default -> {
						System.err.println("Unknown Variable '" + type + "'");
						yield  null;
					}
				};
	}

}
