package steve6472.sge.test.lowscript.instructions.functions;

import steve6472.sge.test.lowscript.LowScript;
import steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class Print extends Instruction
{
	public Print(LowScript ls)
	{
		super(ls);
	}

	private Print(Instruction i)
	{
		super(i);
	}

	private boolean isString;
	private String string;
	private Param param;

	@Override
	public String getName()
	{
		return "print";
	}

	@Override
	public void parse(String[] a, String t)
	{
		if (t.substring(6).startsWith("'") && t.substring(6).endsWith("'"))
		{
			isString = true;
			string = t.substring(7, t.length() - 1);
		} else
		{
			param = new Param(a[1]);
		}
	}

	@Override
	public Instruction copy()
	{
		Print p = new Print(this);
		p.isString = isString;
		p.string = string;
		p.param = param.copy();
		return p;
	}

	@Override
	public void run()
	{
		if (isString)
		{
			System.out.print(string);
		} else
		{
			System.out.print(param.get());
		}
	}
}
