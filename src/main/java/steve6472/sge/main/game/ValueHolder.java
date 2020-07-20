/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 25. 12. 2017
* Project: SGE
*
***********************/

package steve6472.sge.main.game;

import java.io.Serializable;

public class ValueHolder implements Serializable
{
	public Object value = null;
	
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
		
		if (get() instanceof String)
			return Boolean.valueOf((String) get());
			
		return (boolean) get();
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
		
		if (get() instanceof Double)
			return String.valueOf(get());
		
		return (String) get();
	}

	public final double getDouble()
	{
		if (get() == null)
			return 0;
		
		if (get() instanceof Double)
			return (double) get();
		
		if (get() instanceof Integer)
			return (Integer) get();

		return 0;
	}

	public final int getInt()
	{
		if (get() == null)
			return 0;
		
		if (get() instanceof Integer)
			return (int) get();
		
		return Integer.valueOf((String) get());
	}
	
	public final int getHexInt()
	{
		if (get() == null)
			return 0;
		
		if (get() instanceof String)
			return (int) Long.parseLong((String) get(), 16);

		return 0;
	}
	
	public final String[] getStringArray()
	{
		if (get() == null)
			return null;
		
		if (get() instanceof String[])
			return (String[]) get();
		
		return null;
	}
}
