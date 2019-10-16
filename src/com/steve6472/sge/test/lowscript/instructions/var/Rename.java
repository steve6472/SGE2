package com.steve6472.sge.test.lowscript.instructions.var;

import com.steve6472.sge.test.lowscript.LowScript;
import com.steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class Rename extends Instruction
{
	public Rename(LowScript ls)
	{
		super(ls);
	}

	private Rename(Instruction i)
	{
		super(i);
	}

	@Override
	public String getName()
	{
		return "rename";
	}

	private String what, that;

	@Override
	public void parse(String[] a, String t)
	{
		what = a[1];
		that = a[2];
	}

	@Override
	public Instruction copy()
	{
		Rename r = new Rename(this);
		r.what = what;
		r.that = that;
		return r;
	}

	@Override
	public void run()
	{
		names.put(that, Integer.parseInt(what));
	}
}
