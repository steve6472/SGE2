package com.steve6472.sge.test.lowscript.instructions.flow;

import com.steve6472.sge.test.lowscript.LowScript;
import com.steve6472.sge.test.lowscript.instructions.Instruction;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class Stop extends Instruction
{
	public Stop(LowScript ls)
	{
		super(ls);
	}

	private Stop(Instruction i)
	{
		super(i);
	}

	@Override
	public String getName()
	{
		return "stop";
	}

	private Param status;

	@Override
	public void parse(String[] a, String t)
	{
		status = new Param(a[1]);
	}

	@Override
	public Instruction copy()
	{
		Stop s = new Stop(this);
		s.status = status.copy();
		return s;
	}

	@Override
	public void run()
	{
		System.out.println();

		for (int i = 0; i < types.size(); i++)
			System.out.println("Types: " + types.keySet().toArray()[i] + " " + types.values().toArray()[i]);

		for (int i = 0; i < integerValues.size(); i++)
			System.out.println("Integer: " + integerValues.keySet().toArray()[i] + " " + integerValues.values().toArray()[i]);

		for (int i = 0; i < stringValues.size(); i++)
			System.out.println("String: " + stringValues.keySet().toArray()[i] + " " + stringValues.values().toArray()[i]);

		for (int i = 0; i < names.size(); i++)
			System.out.println("Name: " + names.keySet().toArray()[i] + " " + names.values().toArray()[i]);

		System.exit(status.getInt());
	}
}
