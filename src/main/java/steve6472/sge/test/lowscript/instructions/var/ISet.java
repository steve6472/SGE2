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
public class ISet extends Instruction
{
	public ISet(LowScript ls)
	{
		super(ls);
	}

	private ISet(Instruction i)
	{
		super(i);
	}

	@Override
	public String getName()
	{
		return "iset";
	}

	private int value, id;

	@Override
	public void parse(String[] a, String t)
	{
		value = a.length >= 3 ? Integer.parseInt(a[2]) : 0;
		id = Integer.parseInt(a[1]);
	}

	@Override
	public Instruction copy()
	{
		ISet i = new ISet(this);
		i.value = value;
		i.id = id;
		return i;
	}

	@Override
	public void run()
	{
		integerValues.put(id, value);
		types.put(id, DataType.INT);
	}
}
