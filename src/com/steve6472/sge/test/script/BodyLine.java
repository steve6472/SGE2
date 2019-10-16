package com.steve6472.sge.test.script;

import java.util.Arrays;

import static com.steve6472.sge.test.script.ScriptTest.*;

class BodyLine
{
	String text;
	Object type;
	ScriptMethodBody smb;

	BodyLine(ScriptMethodBody smb, String text)
	{
		this.smb = smb;
		this.text = text;
	}

	BodyLine method(ScriptMethod sm)
	{
		MethodCall mc = new MethodCall();
		mc.method = sm;

		mc.parameters = getParameters(text);
//
//		mc.parameters = new MemoryObject[parameters.length];
//
//		for (int i = 0; i < parameters.length; i++)
//		{
//			mc.parameters[i] = parseScriptParameter(parameters[i], sm, i);
//		}

		type = mc;

		return this;
	}

	BodyLine builtinMethod()
	{
		BuiltinMethodCall bmc = new BuiltinMethodCall();

		if (checkBasicMethod(text, "print")) bmc.bmt = BMT.PRINT;
		if (checkBasicMethod(text, "printErr")) bmc.bmt = BMT.PRINT_ERR;

		bmc.parameters = getParameters(text);

		type = bmc;

		return this;
	}

	BodyLine varDec()
	{
		VarDec vd = new VarDec();

		String t = text.substring(0, text.indexOf(' '));
		vd.type = parseDataType(t);

		String n = text.substring(t.length() + 1, text.indexOf('='));
		vd.name = n.trim();

		if (vd.type == DataType.STRING)
		{
			String v = text.substring(text.indexOf('=') + 1, text.lastIndexOf(';'));
			vd.value = v.trim().substring(1, v.trim().length() - 1);
		} else
		{
			String v = text.substring(text.indexOf('=') + 1, text.lastIndexOf(';'));
			vd.value = v.trim();
		}

		type = vd;

		return this;
	}

//	MemoryObject parseScriptParameter(String s, ScriptMethod sm, int index)
//	{
//		MemoryObject sp = new MemoryObject();
//
//		DataType dt = getParameterType(s);
//		sp.type = dt;
//		sp.name = s;
//
//		if (dt == DataType.UNKNOWN)
//		{
//			sp.isReference = true;
//			sp.type = sm.arguments[index].type;
//		}
//
//		System.err.println("<< " + sp);
//
//		return sp;
//	}

	void call(Pointer... pointers)
	{
		if (type instanceof MethodCall)
		{
			MethodCall mc = (MethodCall) type;

			Pointer[] ps = new Pointer[mc.parameters.length];

			if (pointers == null || pointers.length == 0)
			{
				for (int i = 0; i < mc.parameters.length; i++)
				{
					if (ScriptTest.isParameterGlobal(mc.parameters[i]))
					{
						Pointer p = ScriptTest.getLastMemoryObject().createPointer(true, true);
						ps[i] = p;
					} else
					{
						if (smb.sm.isParameter(mc.parameters[i]))
						{
							Pointer p = smb.sm.getLastMemoryObject().createPointer(true, false);
							if (p == null)
							{
								throw new NullPointerException("No variable with the name '" + mc.parameters[i] + "' has been found!");
							}
							ps[i] = p;
						}
					}
				}
			} else
			{
				ps = pointers;
			}

			mc.method.call(ps);
		}
		else
		if (type instanceof BuiltinMethodCall)
		{
			BuiltinMethodCall bmc = (BuiltinMethodCall) type;

			MemoryObject mo;

			Pointer[] ps = new Pointer[bmc.parameters.length];

			if (pointers == null || pointers.length == 0)
			{
				for (int i = 0; i < bmc.parameters.length; i++)
				{
					if (ScriptTest.isParameterGlobal(bmc.parameters[i]))
					{
						Pointer p = ScriptTest.getLastMemoryObject().createPointer(true, true);
						ps[i] = p;
					} else
					{
						if (smb.sm.isParameter(bmc.parameters[i]))
						{
							Pointer p = smb.sm.getLastMemoryObject().createPointer(true, false);
							if (p == null)
							{
								throw new NullPointerException("No variable with the name '" + bmc.parameters[i] + "' has been found!");
							}
							ps[i] = p;
						}
					}
				}
			} else
			{
				ps = pointers;
			}

			Pointer p = ps[0];
			System.out.println(p);
			if (p.isGlobal && p.copy) mo = globalMemory.get(p.address).copy();
			else if (p.isGlobal) mo = globalMemory.get(p.address);
			else if (p.copy) mo = smb.sm.methodMemory.get(p.address).copy();
			else mo = smb.sm.methodMemory.get(p.address);

			switch (bmc.bmt)
			{
				case PRINT -> System.out.println(mo.value);
				case PRINT_ERR -> System.err.println(mo.value);
			}
		}
		else if (type instanceof VarDec)
		{
			VarDec vd = (VarDec) type;

			smb.sm.initNewVariable(vd.name, vd.value, vd.type);
		}
	}

	class MethodCall
	{
		ScriptMethod method;
		String[] parameters;

		@Override
		public String toString()
		{
			return "MethodCall{" + "method=" + method + ", parameters=" + Arrays.toString(parameters) + '}';
		}
	}

	class BuiltinMethodCall
	{
		BMT bmt;
		String[] parameters;

		@Override
		public String toString()
		{
			return "BuiltinMethodCall{" + "bmt=" + bmt + ", parameters=" + Arrays.toString(parameters) + '}';
		}
	}

	class VarDec
	{
		String name;
		String value;
		DataType type;

		@Override
		public String toString()
		{
			return "VarDec{" + "name='" + name + '\'' + ", value='" + value + '\'' + ", type=" + type + '}';
		}
	}
}