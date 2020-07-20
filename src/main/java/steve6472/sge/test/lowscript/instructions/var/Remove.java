package steve6472.sge.test.lowscript.instructions.var;

import steve6472.sge.main.smartsave.DataType;
import steve6472.sge.test.lowscript.LowScript;
import steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class Remove extends Instruction
{
	public Remove(LowScript ls)
	{
		super(ls);
	}

	private Remove(Remove i)
	{
		super(i);
	}

	@Override
	public String getName()
	{
		return "remove";
	}

	private String p;

	@Override
	public void parse(String[] a, String t)
	{
		p = a[1];
	}

	@Override
	public Instruction copy()
	{
		Remove r = new Remove(this);
		r.p = p;
		return r;
	}

	@Override
	public void run()
	{
		DataType dataType = types.get(parseNumber(p));
		switch (dataType)
		{
			case INT -> integerValues.remove(parseNumber(p));
			case STRING -> stringValues.remove(parseNumber(p));
		}
	}
}
