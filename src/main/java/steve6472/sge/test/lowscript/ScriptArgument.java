package steve6472.sge.test.lowscript;

import steve6472.sge.main.smartsave.DataType;

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