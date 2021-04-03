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
public class Swap extends Instruction
{
	public Swap(LowScript ls)
	{
		super(ls);
	}

	@Override
	public String getName()
	{
		return "swap";
	}

	@Override
	public void parse(String[] a, String t)
	{
		DataType dataType = types.get(parseNumber(a[1]));
		switch (dataType)
		{
			case INT -> {

				int l = integerValues.get(Integer.parseInt(a[1]));
				int r = integerValues.get(Integer.parseInt(a[2]));
				integerValues.replace(Integer.parseInt(a[1]), r);
				integerValues.replace(Integer.parseInt(a[2]), l);
			}
			case STRING -> {

				String l = stringValues.get(Integer.parseInt(a[1]));
				String r = stringValues.get(Integer.parseInt(a[2]));
				stringValues.replace(Integer.parseInt(a[1]), r);
				stringValues.replace(Integer.parseInt(a[2]), l);
			}
		}
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
