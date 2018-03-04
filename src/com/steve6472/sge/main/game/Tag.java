package com.steve6472.sge.main.game;

import java.io.Serializable;

public class Tag implements Serializable
{
	private static final long serialVersionUID = -1131095891329117005L;
	public String name = null;
	public Object value = null;
	
	public Tag(String name, Object value)
	{
		this.name = name;
		this.value = value;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	protected Object get()
	{
		return value;
	}
	
	public void setValue(Object value)
	{
		this.value = value;
	}
	
	public Object getValue()
	{
		return value;
	}

	public boolean getBoolean()
	{
		if (get() == null)
			return false;
		return (boolean) get();
	}

	public char getChar()
	{
		if (get() == null)
			return ' ';
		return ((String) get()).charAt(0);
	}
	
	public String getString()
	{
		if (get() instanceof Integer)
			return String.valueOf(get());
		
		return (String) get();
	}

	public double getDouble()
	{
		if (get() == null)
			return 0;
		return (Double) get();
	}

	public int getInt()
	{
		if (get() == null)
			return 0;
		
		if (get() instanceof Integer)
			return (int) get();
		
		return new Integer((String) get());
	}
	
	public Tag getTag()
	{
		if (get() == null)
			return null;
		return (Tag) get();
	}
	
	public Tag[] getTagArray()
	{
		if (get() == null)
			return null;
		return (Tag[]) get();
	}

	@Override
	public String toString()
	{
		return "Tag [name=" + name + ", value=" + value + "]";
	}
	
	
	
	/*
		try
		{
			return new Double((String) get());
		} catch (Exception ex)
		{
			System.err.println("Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage() + "\nIn:");
			for (StackTraceElement w : ex.getStackTrace())
			{
				System.err.println("\t" + w.getClassName() + "." + w.getMethodName() + "(" + w.getFileName() + ":" + w.getLineNumber() + ")");
			}
			System.err.println("End of error log. Returning (double) 0 & Exitting level");
			return 0;
		}
		*/
}
