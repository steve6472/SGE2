package com.steve6472.sge.test.lowscript.instructions.functions;

import com.steve6472.sge.test.lowscript.LowScript;
import com.steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class Println extends Instruction
{
	public Println(LowScript ls)
	{
		super(ls);
	}

	private Println(Instruction i)
	{
		super(i);
	}

	private boolean isString;
	private String string;
	private Param param;


	@Override
	public String getName()
	{
		return "println";
	}

	@Override
	public void parse(String[] a, String t)
	{
		if (t.substring(8).startsWith("'") && t.substring(8).endsWith("'"))
		{
			isString = true;
			string = t.substring(9, t.length() - 1);
		} else
		{
			param = new Param(a[1]);
		}

	}

	@Override
	public Instruction copy()
	{
		Println p = new Println(this);
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
			System.out.println(string);
		} else
		{
			System.out.println(param.get());
		}
	}
}
