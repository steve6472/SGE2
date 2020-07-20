package steve6472.sge.test.lowscript.instructions.flow;

import steve6472.sge.test.lowscript.LowScript;
import steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class WaitTime extends Instruction
{
	public WaitTime(LowScript ls)
	{
		super(ls);
	}

	private WaitTime(Instruction i)
	{
		super(i);
	}

	@Override
	public String getName()
	{
		return "waitTime";
	}

	private long waitTime;

	@Override
	public void parse(String[] a, String t)
	{
		waitTime = Long.parseLong(a[1]);
	}

	@Override
	public Instruction copy()
	{
		WaitTime wt = new WaitTime(this);
		wt.waitTime = waitTime;
		return wt;
	}

	@Override
	public void run()
	{
		LowScript.waitTime = waitTime;
	}
}
