package steve6472.sge.test.lowscript.instructions.var;

import steve6472.sge.main.util.DataType;
import steve6472.sge.test.lowscript.LowScript;
import steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class SSet extends Instruction
{
	public SSet(LowScript ls)
	{
		super(ls);
	}

	@Override
	public String getName()
	{
		return "sset";
	}

	@Override
	public void parse(String[] a, String t)
	{
		String text;
		if (a.length >= 3)
		{
			String f = t.substring(a[1].length() + 6);
			if (f.startsWith("'") && f.endsWith("'"))
				text = f.substring(1, f.length() - 1);
			else
				text = f;
		} else
			text = "";

		stringValues.put(Integer.parseInt(a[1]), text);
		types.put(Integer.parseInt(a[1]), DataType.STRING);
	}

	@Override
	public Instruction copy()
	{
		return null;
	}

	@Override
	public void run()
	{

	}
}
