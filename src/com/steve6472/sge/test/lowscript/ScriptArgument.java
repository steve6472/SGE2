package com.steve6472.sge.test.lowscript;

import com.steve6472.sge.main.game.DataType;

class ScriptArgument
{
	String name;
	DataType type;

	@Override
	public String toString()
	{
		return "ScriptArgument{" + "name='" + name + '\'' + ", type=" + type + '}';
	}
}