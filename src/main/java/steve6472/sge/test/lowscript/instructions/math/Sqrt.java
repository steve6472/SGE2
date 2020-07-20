package steve6472.sge.test.lowscript.instructions.math;

import steve6472.sge.test.lowscript.LowScript;
import steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class Sqrt extends Instruction
{
	public Sqrt(LowScript ls)
	{
		super(ls);
	}

	private Sqrt(Instruction i)
	{
		super(i);
	}

	@Override
	public String getName()
	{
		return "sqrt";
	}

	private Param id, sqrt;

	@Override
	public void parse(String[] a, String t)
	{
		id = new Param(a[2]);
		sqrt = new Param(a[1]);
	}

	@Override
	public Instruction copy()
	{
		Sqrt i = new Sqrt(this);
		i.id = id.copy();
		i.sqrt = sqrt.copy();
		return i;
	}

	@Override
	public void run()
	{
		integerValues.replace(id.getInt(), (int) Math.sqrt(sqrt.getInt()));
	}
}
