/**********************
* Created by steve6472
* On date: 11. 1. 2018
* Project: SSS2
*
***********************/

package steve6472;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class SSS implements Iterable<ValueHolder>
{
	List<ValueHolder> values;
	
	public SSS(String path)
	{
		this(new File(path));
	}

	public SSS(String[] rawText)
	{
		values = new ArrayList<>();
		List<String> arrayVars = new ArrayList<>();

		boolean multilinedComment = false;
		boolean inArray = false;
		boolean comment = false;
		String arrayName = "";

		for (String s : rawText)
		{
			if	(s.trim().isEmpty())
				continue;

			if (s.startsWith("/*"))
				multilinedComment = true;

			if (s.endsWith("*/"))
			{
				multilinedComment = false;
				continue;
			}

			if (s.trim().startsWith("/") || s.trim().startsWith("#"))
				comment = true;

			// Detect One line variable
			if (s.contains("=") && !multilinedComment && !inArray && !comment)
			{
				String[] arr = s.split("=", 2);
				values.add(new ValueHolder(arr[0].trim(), arr[1].trim()));
			}

			// Detect Array
			if (s.contains(":") && !s.contains("=") && !comment && !multilinedComment && !inArray)
			{
				arrayName = s.split(":", 2)[0].trim();

				if (s.contains("{") && s.contains(":") && !s.contains("=") && !s.contains("\\{"))
				{
					inArray = true;
				}
			}

			if (s.trim().startsWith("{") && !comment && !multilinedComment && !s.contains("\\{"))
			{
				inArray = true;
			}

			if (s.trim().endsWith("}") && !comment && !multilinedComment && !s.contains("\\}"))
			{
				inArray = false;
			}

			// Array.. thing
			if (inArray && !multilinedComment && !comment && !s.trim().startsWith("{"))
			{
				arrayVars.add(s.trim());
			}

			if (!arrayVars.isEmpty() && !inArray)
			{
				//				System.out.println(Arrays.toString(arrayVars.toArray()));
				values.add(new ValueHolder(arrayName, arrayVars.toArray(new String[0])));
				arrayVars.clear();
			}

			comment = false;
		}
	}
	
	public SSS(File file)
	{
		this(Util.readFile(file.getAbsolutePath()));
	}

	public SSS(File file, String child)
	{
		this(Util.readFile(new File(file, child).getAbsolutePath()));
	}
	
	public void save(File file)
	{
		try (PrintWriter out = new PrintWriter(file))
		{
			for (ValueHolder vh : values)
			{
				if (vh.getValue() instanceof String[])
				{
					out.println(vh.getName() + ":");
					out.println("{");
					for (String s : vh.getStringArray())
						out.println("\t" + s);
					out.println("}");
				} else
				{
					out.println(vh.name + " = " + vh.getValue());
				}
			}
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds variable
	 * @param name name of new entry
	 * @param value value of new entry
	 */
	public void add(String name, Object value)
	{
		values.add(new ValueHolder(name, value));
	}

	public void remove(String name)
	{
		values.removeIf(v -> v.getName().equals(name));
	}
	
	public void addArray(String name, Object[] array)
	{
		values.add(new ValueHolder(name, array));
	}
	
	public void addArray(String name, List<?> array)
	{
		values.add(new ValueHolder(name, array.toArray(new String[0])));
	}
	
	public List<String> getAllNames()
	{
		List<String> names = new ArrayList<>();
		for (ValueHolder l : values)
		{
			names.add(l.name);
		}
		return names;
	}

	public boolean containsName(String name)
	{
		return getAllNames().contains(name);
	}
	
	public int getValueCount()
	{
		return values.size();
	}
	
	public void clear()
	{
		values.clear();
	}
	
	/**
	 * 
	 * @param name name of value
	 * @param newValue new value to be set
	 * @return true if value was found and replaced. False otherwise
	 */
	public boolean replace(String name, Object newValue)
	{
		for (ValueHolder v : values)
		{
			if (v.name.equals(name))
			{
				v.setValue(newValue);
				return true;
			}
		}

		return false;
	}
	
	public boolean hasValue(String name)
	{
		for (ValueHolder v : values)
		{
			if (v.name.equals(name))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public Object getObject(String name)
	{
		for (ValueHolder v : values)
		{
			if (v.name.equals(name))
				return v.getValue();
		}
		
		return null;
//		throw new NullPointerException(name + " doesn't exist!");
	}
	
	private ValueHolder get(String name)
	{
		for (ValueHolder v : values)
		{
			if (v.name.equals(name))
				return v;
		}
		
		throw new NullPointerException(name + " doesn't exist!");
	}
	
	public boolean getBoolean(String name)
	{
		return get(name).getBoolean();
	}
	
	public char getChar(String name)
	{
		return get(name).getChar();
	}
	
	public String getString(String name)
	{
		return get(name).getString();
	}
	
	public double getDouble(String name)
	{
		return get(name).getDouble();
	}
	
	public int getInt(String name)
	{
		return get(name).getInt();
	}
	
	public byte getByte(String name)
	{
		return get(name).getByte();
	}
	
	public float getFloat(String name)
	{
		return get(name).getFloat();
	}
	
	public long getLong(String name)
	{
		return get(name).getLong();
	}
	
	public short getShort(String name)
	{
		return get(name).getShort();
	}
	
	public int getHexInt(String name)
	{
		return get(name).getHexInt();
	}
	
	/*
	 * Arrays
	 */
	
	public String[] getStringArray(String name)
	{
		return (String[]) getObject(name);
	}

	public void printData()
	{
		for (ValueHolder l : values)
		{
			if (l.getValue() instanceof String[])
			{
				System.out.println(String.format("%s = %s", l.name, Arrays.toString((String[]) l.getValue())));
			} else
			{
				System.out.println(String.format("%s = %s", l.name, l.getValue()));
			}
		}
	}

	public static void main(String[] args)
	{
		SSS sss = new SSS(new File("test.txt"));
		sss.printData();
		System.out.println();
		System.out.println(Arrays.toString(sss.getStringArray("arr1")));
		System.out.println(Arrays.toString(sss.getStringArray("arr2")));
	}

	@Override
	public Iterator<ValueHolder> iterator()
	{
		return new Itr();
	}

	private class Itr implements Iterator<ValueHolder>
	{
		int index;

		@Override
		public boolean hasNext()
		{
			return index != values.size();
		}

		@Override
		public ValueHolder next()
		{
			int i = index;

			if (i >= values.size())
				throw new ConcurrentModificationException();

			index = i + 1;

			return values.get(i);
		}

		@Override
		public void remove()
		{
			throw new ConcurrentModificationException();
		}

	}
}
