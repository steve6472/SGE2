package steve6472.sge.test.lowscript.instructions.flow;

import steve6472.sge.test.lowscript.LowScript;
import steve6472.sge.test.lowscript.instructions.Instruction;

import static steve6472.sge.test.lowscript.instructions.flow.Goto.Sign.*;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 24.06.2019
 * Project: SJP
 *
 ***********************/
public class Goto extends Instruction
{
	public Goto(LowScript ls)
	{
		super(ls);
	}

	private Goto(Instruction i)
	{
		super(i);
	}

	private Param index;

	private boolean hasIf, hasElse;
	private Sign sign;
	private Param left, right;
	private Param elseIndex;

	@Override
	public String getName()
	{
		return "goto";
	}

	@Override
	public void parse(String[] a, String t)
	{
		index = new Param(a[1]);
		if (t.contains("if"))
		{
			hasIf = true;
			left = new Param(a[3]);
			right = new Param(a[5]);
			this.sign = switch (a[4])
					{
						case "!=" -> NOT_EQUALS;
						case "==" -> EQUALS;
						case "<=" -> LESS_OR_EQUAL;
						case "<" -> LESS;
						case ">=" -> GREATER_OR_EQUAL;
						case ">" -> GREATER;
						default -> throw new IllegalStateException("Unexpected value: " + sign);
					};
			if (t.contains("else"))
			{
				hasElse = true;
				elseIndex = new Param(a[7]);
			}
		}
	}

	@Override
	public void run()
	{
		if (hasIf)
		{
			int l = (int) left.get();
			int r = (int) right.get();

			boolean b = switch (sign)
					{
						case NOT_EQUALS -> l != r;
						case EQUALS -> l == r;
						case LESS_OR_EQUAL -> l <= r;
						case LESS -> l < r;
						case GREATER_OR_EQUAL -> l >= r;
						case GREATER -> l > r;
					};
			if (b)
			{
				LowScript.index = index.getInt() - 2;
			} else
			{
				if (hasElse)
				{
					LowScript.index = elseIndex.getInt() - 2;
				}
			}
		} else
		{
			LowScript.index = index.getInt() - 2;
		}
	}

	enum Sign
	{
		NOT_EQUALS, EQUALS, LESS_OR_EQUAL, LESS, GREATER_OR_EQUAL, GREATER
	}

	@Override
	public Instruction copy()
	{
		Goto g = new Goto(this);
		g.index = index.copy();
		g.hasIf = hasIf;
		if (hasIf)
		{
			g.sign = sign;
			g.left = left.copy();
			g.right = right.copy();
			g.hasElse = hasElse;
			if (hasElse)
			{
				g.elseIndex = elseIndex.copy();
			}
		}
		return g;
	}
}
