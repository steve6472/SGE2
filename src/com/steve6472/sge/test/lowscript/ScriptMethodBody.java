package com.steve6472.sge.test.lowscript;

import java.util.ArrayList;
import java.util.List;

class ScriptMethodBody
{
	String[] body;
	List<BodyLine> lines;
	ScriptMethod sm;

	ScriptMethodBody(ScriptMethod sm)
	{
		this.sm = sm;
		lines = new ArrayList<>();
	}

	void parse()
	{
		for (String s : body)
		{
			if (!s.equals("{") && !s.equals("}"))
			{
				lines.add(new BodyLine(this, s));
			}
		}
	}
}