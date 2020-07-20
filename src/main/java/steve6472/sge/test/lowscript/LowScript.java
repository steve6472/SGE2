package steve6472.sge.test.lowscript;

import steve6472.sge.main.Util;
import steve6472.sge.main.smartsave.DataType;
import steve6472.sge.test.lowscript.instructions.Instruction;
import steve6472.sge.test.lowscript.instructions.flow.Goto;
import steve6472.sge.test.lowscript.instructions.flow.Stop;
import steve6472.sge.test.lowscript.instructions.flow.WaitTime;
import steve6472.sge.test.lowscript.instructions.functions.IInput;
import steve6472.sge.test.lowscript.instructions.functions.Print;
import steve6472.sge.test.lowscript.instructions.functions.Println;
import steve6472.sge.test.lowscript.instructions.functions.SInput;
import steve6472.sge.test.lowscript.instructions.math.*;
import steve6472.sge.test.lowscript.instructions.var.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 20.06.2019
 * Project: Low Script
 *
 ***********************/
public class LowScript
{
	public static HashMap<Integer, Integer> integerValues;
	public static HashMap<Integer, String> stringValues;
	public static HashMap<String, Integer> names;
	public static HashMap<Integer, DataType> types;
	public static int index;

	public static long waitTime;

	private static List<Instruction> instructions;
	private static List<Instruction> lines;

	public static void main(String[] args)
	{
		new LowScript().main();
	}

	private void main()
	{
		integerValues = new HashMap<>();
		stringValues = new HashMap<>();
		names = new HashMap<>();
		types = new HashMap<>();

		lines = new ArrayList<>();
		instructions = new ArrayList<>();

		/* Flow */
		instructions.add(new Goto(this));
		instructions.add(new Stop(this));
		instructions.add(new WaitTime(this));

		/* Functions */
		instructions.add(new IInput(this));
		instructions.add(new Print(this));
		instructions.add(new Println(this));
		instructions.add(new SInput(this));

		/* Math */
		instructions.add(new Add(this));
		instructions.add(new Mul(this));
		instructions.add(new Pow(this));
		instructions.add(new Sub(this));
		instructions.add(new Div(this));
		instructions.add(new Sqrt(this));

		/* Var */
		instructions.add(new IAssign(this));
		instructions.add(new ISet(this));
		instructions.add(new Name(this));
		instructions.add(new Remove(this));
		instructions.add(new RemoveName(this));
		instructions.add(new Rename(this));
		instructions.add(new SAssign(this));
		instructions.add(new SSet(this));
		instructions.add(new Swap(this));

		String[] file = Util.loadDataFromFile("lowTest.txt");

		for (index = 0; index < file.length; index++)
		{
			String s = file[index];
			s = s.trim();
			if (!s.isBlank() && !s.startsWith("//"))
			{
				try
				{
					parse(s);
				} catch (Exception e)
				{
					System.err.println(index);
					System.err.println(s);
					e.printStackTrace();
					System.exit(-1);
				}

				if (waitTime > 0)
					Util.sleep(waitTime);
			} else
			{
				lines.add(null);
			}
		}

		for (index = 0; index < lines.size(); index++)
		{
			Instruction i = lines.get(index);
			if (i != null)
			{
				i.run();
			}
		}
	}

	private static void parse(String s)
	{
		String[] S = s.split(" ");
		for (Instruction instruction : instructions)
		{
			if (instruction.tryParse(S, s))
			{
				lines.add(instruction.copy());
				break;
			}
		}
	}
}
