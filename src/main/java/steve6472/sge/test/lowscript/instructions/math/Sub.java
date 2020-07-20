package steve6472.sge.test.lowscript.instructions.math;

import steve6472.sge.test.lowscript.LowScript;
import steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class Sub extends Instruction
{
	public Sub(LowScript ls)
	{
		super(ls);
	}

	private Sub(Instruction i)
	{
		super(i);
	}

	private Param left, right, is;

	@Override
	public String getName()
	{
		return "sub";
	}

	@Override
	public void parse(String[] a, String t)
	{
		left = new Param(a[1]);
		right = new Param(a[2]);
		is = new Param(a[3]);
	}

	@Override
	public Instruction copy()
	{
		Sub a = new Sub(this);
		a.left = left.copy();
		a.right = right.copy();
		a.is = is.copy();
		return a;
	}

	@Override
	public void run()
	{
		integerValues.replace(is.getInt(), left.getInt() - right.getInt());
	}
}
