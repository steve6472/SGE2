package steve6472.sge.test.lowscript.instructions;

import steve6472.sge.main.Util;
import steve6472.sge.main.smartsave.DataType;
import steve6472.sge.test.lowscript.LowScript;

import java.util.HashMap;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public abstract class Instruction
{
	protected HashMap<Integer, Integer> integerValues;
	protected HashMap<Integer, String> stringValues;
	protected HashMap<String, Integer> names;
	protected HashMap<Integer, DataType> types;

	public Instruction(LowScript ls)
	{
		this.integerValues = ls.integerValues;
		this.stringValues = ls.stringValues;
		this.names = ls.names;
		this.types = ls.types;
	}

	public Instruction(Instruction i)
	{
		this.integerValues = i.integerValues;
		this.stringValues = i.stringValues;
		this.names = i.names;
		this.types = i.types;
	}

	protected class Param
	{
		boolean star;

		String name;
		int number;

		Param()
		{

		}

		public Param(String s)
		{
			if (s.startsWith("*"))
			{
				star = true;
				if (Util.isNumber(s.substring(1)))
				{
					number = Integer.parseInt(s.substring(1));
				} else
				{
					name = s.substring(1);
				}
			}
			else if (Util.isNumber(s))
			{
				number = Integer.parseInt(s);
			} else
			{
				name = s;
			}
		}

		public Object get()
		{
			int num = 0;
			if (star)
			{
				if (name != null)
				{
					return names.get(name);
				} else
				{
					num = number;
				}
			} else if (name == null)
			{
				return number;
			} else
			{
				num = names.get(name);
			}

			DataType t = types.get(num);

			if (t == DataType.INT)
				return integerValues.get(num);
			if (t == DataType.STRING)
				return stringValues.get(num);

			return null;
		}

		public int getInt()
		{
			return (int) get();
		}

		public String getString()
		{
			return (String) get();
		}

		public Param copy()
		{
			Param p = new Param();
			p.number = number;
			p.name = name;
			p.star = star;
			return p;
		}
	}

	/**
	 *
	 * 'number' is named variable
	 *
	 * number returns value of named varaiable
	 * *number returns key of named variable
	 * 0 returns raw number (itself)
	 * *0 returns value of variable
	 *
	 *
	 * add number 1 *number     ==     number = number + 1;
	 *
	 * @param s parameter
	 * @return number or key
	 */
	protected Object parseNumber(String s)
	{
		int num;
		if (s.startsWith("*"))
		{
			if (Util.isNumber(s.substring(1)))
			{
				num = Integer.parseInt(s.substring(1));
			} else
			{
				return names.get(s.substring(1));
			}
		}
		else if (Util.isNumber(s))
		{
			return Integer.parseInt(s);
		} else
		{
			num = names.get(s);
		}

		DataType t = types.get(num);

		if (t == DataType.INT)
			return integerValues.get(num);
		if (t == DataType.STRING)
			return stringValues.get(num);

		return null;
	}

	public boolean tryParse(String[] a, String t)
	{
		if (t.startsWith(getName() + " "))
		{
			parse(a, t);
			return true;
		}
		return false;
	}

	public abstract String getName();

	public abstract void parse(String[] a, String t);

	public abstract Instruction copy();

	public abstract void run();
}
