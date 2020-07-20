package steve6472.sge.test.lowscript.instructions.var;

import steve6472.sge.test.lowscript.LowScript;
import steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class IAssign extends Instruction
{
	public IAssign(LowScript ls)
	{
		super(ls);
	}

	private IAssign(Instruction i)
	{
		super(i);
	}

	private Param left, right;

	@Override
	public String getName()
	{
		return "iassign";
	}

	@Override
	public void parse(String[] a, String t)
	{
		left = new Param(a[1]);
		right = new Param(a[2]);
	}

	@Override
	public Instruction copy()
	{
		IAssign i = new IAssign(this);
		i.left = left.copy();
		i.right = right.copy();
		return i;
	}

	@Override
	public void run()
	{
		integerValues.put(right.getInt(), left.getInt());
	}
}
