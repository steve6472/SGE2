package com.steve6472.sge.test.script;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 20.06.2019
 * Project: SJP
 *
 ***********************/
class Pointer
{
	int address;
	boolean copy;
	boolean isGlobal;

	Pointer(int address, boolean copy, boolean isGlobal)
	{
		this.address = address;
		this.copy = copy;
		this.isGlobal = isGlobal;
	}

	MemoryObject getGlobalMemoryObject()
	{
		return ScriptTest.globalMemory.get(address);
	}

	@Override
	public String toString()
	{
		return "Pointer{" + "address=" + address + ", copy=" + copy + ", isGlobal=" + isGlobal + '}';
	}
}
