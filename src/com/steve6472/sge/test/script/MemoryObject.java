package com.steve6472.sge.test.script;

class MemoryObject
{
	String name;
	Object value;
	DataType type;
	int address;

	MemoryObject(String name, Object value, DataType type)
	{
		this.name = name;
		this.value = value;
		this.type = type;
	}

	Pointer createPointer(boolean copy, boolean isGlobal)
	{
		return new Pointer(address, copy, isGlobal);
	}

	public MemoryObject copy()
	{
		MemoryObject mo = new MemoryObject(name, value, type);
		mo.address = address;
		return mo;
	}

	@Override
	public String toString()
	{
		return "MemoryObject{" + "name='" + name + '\'' + ", value=" + value + ", type=" + type + ", address=" + address + '}';
	}
}