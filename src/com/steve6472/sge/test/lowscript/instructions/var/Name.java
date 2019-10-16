package com.steve6472.sge.test.lowscript.instructions.var;

import com.steve6472.sge.test.lowscript.LowScript;
import com.steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class Name extends Instruction
{
	public Name(LowScript ls)
	{
		super(ls);
	}

	private Name(Instruction i)
	{
		super(i);
	}

	private String name;
	private int id;

	@Override
	public String getName()
	{
		return "name";
	}

	@Override
	public void parse(String[] a, String t)
	{
		name = a[2];
		id = Integer.parseInt(a[1]);
	}

	@Override
	public Instruction copy()
	{
		Name i = new Name(this);
		i.name = name;
		i.id = id;
		return i;
	}

	@Override
	public void run()
	{
		names.put(name, id);
	}
}
