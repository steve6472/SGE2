/**********************
* Created by steve6472
* On date: 11. 1. 2018
* Project: SSS2
* 
* (Copied and modified from SGE)
*
***********************/

package steve6472;

public class ValueHolder
{
	public Object value;
	
	public String name;
	
	public ValueHolder(String name, Object value)
	{
		this.value = value;
		this.name = name;
		
//		System.out.println(name + "=" + value);
	}

	public String getName()
	{
		return name;
	}

	protected final Object get()
	{
		return value;
	}
	
	public final void setValue(Object value)
	{
		this.value = value;
	}
	
	public final Object getValue()
	{
		return value;
	}

	public final boolean getBoolean()
	{
		if (get() == null)
			return false;
		
		if (get() instanceof Boolean)
			return (boolean) get();
		
		return Boolean.parseBoolean((String) get());
	}

	public final char getChar()
	{
		if (get() == null)
			return ' ';
		return ((String) get()).charAt(0);
	}
	
	public final String getString()
	{
		if (get() instanceof Integer)
			return String.valueOf(get());
		
		return (String) get();
	}

	public final String[] getStringArray()
	{
		return (String[]) get();
	}

	public final double getDouble()
	{
		if (get() == null)
			return 0;
		
		if (get() instanceof Double)
			return (double) get();
		
		if (get() instanceof Integer)
			return Double.parseDouble((String) get());
		
		if (get() instanceof String)
			return Double.parseDouble((String) get());
		
		return Double.NaN;
	}

	public final int getInt()
	{
		if (get() == null)
			return 0;
		
		if (get() instanceof Integer)
			return (int) get();
		
		return Integer.parseInt((String) get());
	}
	
	public final byte getByte()
	{
		if (get() == null)
			return 0;
		
		if (get() instanceof Byte)
			return (byte) get();
		
		return Byte.parseByte((String) get());
	}

	public final float getFloat()
	{
		if (get() == null)
			return 0;
		
		if (get() instanceof Float)
			return (float) get();
		
		if (get() instanceof Integer)
			return Float.parseFloat((String) get());

		return Float.parseFloat((String) get());
	}

	public final long getLong()
	{
		if (get() == null)
			return 0;
		
		if (get() instanceof Long)
			return (long) get();
		
		if (get() instanceof Integer)
			return Long.parseLong((String) get());
		
		return 0;
	}

	public final short getShort()
	{
		if (get() == null)
			return 0;
		
		if (get() instanceof Short)
			return (short) get();
		
		if (get() instanceof Integer)
			return Short.parseShort((String) get());
		
		return 0;
	}
	
	public final int getHexInt()
	{
		if (get() == null)
			return 0;
		
		if (get() instanceof String)
		{
			if (getString().startsWith("0x"))
				return (int) Long.parseLong(getString().substring(2), 16);
			return (int) Long.parseLong((String) get(), 16);
		}

		return 0;
	}
}
