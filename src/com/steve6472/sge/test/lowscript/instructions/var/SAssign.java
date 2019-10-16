package com.steve6472.sge.test.lowscript.instructions.var;

import com.steve6472.sge.test.lowscript.LowScript;
import com.steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class SAssign extends Instruction
{
	public SAssign(LowScript ls)
	{
		super(ls);
	}

	@Override
	public String getName()
	{
		return "sassign";
	}

	@Override
	public void parse(String[] a, String t)
	{
		stringValues.put((Integer) parseNumber(a[2]), (String) parseNumber(a[1]));
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
