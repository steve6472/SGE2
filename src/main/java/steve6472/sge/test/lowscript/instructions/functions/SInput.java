package steve6472.sge.test.lowscript.instructions.functions;

import steve6472.sge.test.lowscript.LowScript;
import steve6472.sge.test.lowscript.instructions.Instruction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class SInput extends Instruction
{
	public SInput(LowScript ls)
	{
		super(ls);
	}

	private SInput(Instruction i)
	{
		super(i);
	}

	private Param id;

	@Override
	public String getName()
	{
		return "sinput";
	}

	@Override
	public void parse(String[] a, String t)
	{
		id = new Param(a[1]);
	}

	@Override
	public Instruction copy()
	{
		SInput i = new SInput(this);
		i.id = id.copy();
		return i;
	}

	@Override
	public void run()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String text = null;
		try
		{
			text = br.readLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		stringValues.put(id.getInt(), text);
	}
}
