package com.steve6472.sge.test.lowscript.instructions.var;

import com.steve6472.sge.test.lowscript.LowScript;
import com.steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class RemoveName extends Instruction
{
	public RemoveName(LowScript ls)
	{
		super(ls);
	}

	private RemoveName(Instruction i)
	{
		super(i);
	}

	@Override
	public String getName()
	{
		return "removeName";
	}

	private String s;

	@Override
	public void parse(String[] a, String t)
	{
		s = a[1];
	}

	@Override
	public Instruction copy()
	{
		RemoveName i = new RemoveName(this);
		i.s = s;
		return i;
	}

	@Override
	public void run()
	{
		names.remove(s);
	}
}
