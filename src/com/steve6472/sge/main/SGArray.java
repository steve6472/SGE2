/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 5. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SGArray<T> implements Iterable<T>
{
	List<T> array;
	
	public SGArray(int initialSize)
	{
		this.array = new ArrayList<T>(initialSize);
	}
	
	public SGArray()
	{
		this.array = new ArrayList<T>();
	}
	
	public void setObject(int index, T o) 	{ set(index, o); }
	public T getObject(int index) 			{ return get(index); }
	public void addObject(T o) 				{ add(o); }
	
	public void set(int index, T o)
	{
		if (index <= array.size())
			array.add(null);
		
		array.set(index, o);
	}
	
	public T get(int index)
	{
		return array.get(index);
	}
	
	public void add(SGArray<T> arr)
	{
		for (int i = 0; i < arr.getSize(); i++)
		{
			add(arr.get(i));
		}
	}
	
	public void add(T o)
	{
		array.add(o);
	}
	
	public void removeNulls()
	{
		SArray nulls = new SArray();
		
		for (int i = 0; i < getSize(); i++)
		{
			if (array.get(i) == null)
				nulls.addObject(i);
		}
		
		nulls.reverseArray();
		
		for (int i = 0; i < nulls.getSize(); i++)
		{
			remove(nulls.getInt(i));
		}
	}
	
	public void clear()
	{
		array.clear();
	}
	
	public void reverseArray()
	{
		for (int i = 0; i < array.size() / 2; i++)
		{
			T temp = array.get(i);
			array.set(i, array.get(array.size() - i - 1));
			array.set(getSize() - i - 1, temp);
		}
	}
	
	public List<T> toList()
	{
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < getSize(); i++)
		{
			list.add(get(i));
		}
		return list;
	}

	public void remove(Object object)
	{
		array.remove(object);
	}
	
	public void remove(int index)
	{
		array.remove(index);
	}
	
	public SGArray<T> copy()
	{
		SGArray<T> a = new SGArray<T>(getSize());
		for (T o : array)
		{
			a.add(o);
		}
		return a;
	}
	
	public void printContent()
	{
		for (int i = 0; i < array.size(); i++)
		{
			Object o = array.get(i);
			
			if (o instanceof float[])
				System.out.println(Arrays.toString((float[]) o));
			else if (o instanceof double[])
				System.out.println(Arrays.toString((double[]) o));
			else if (o instanceof byte[])
				System.out.println(Arrays.toString((byte[]) o));
			else if (o instanceof int[])
				System.out.println(Arrays.toString((int[]) o));
			else if (o instanceof String[])
				System.out.println(Arrays.toString((String[]) o));
			else if (o instanceof Object[])
				System.out.println(Arrays.toString((Object[]) o));
			else if (o instanceof long[])
				System.out.println(Arrays.toString((long[]) o));
			else if (o instanceof short[])
				System.out.println(Arrays.toString((short[]) o));
			else
				System.out.println(o);
		}

	}

	@Override
	public Iterator<T> iterator()
	{
		return new Itr();
	}
	
	public int getSize()
	{
		return array.size();
	}
	
	private int iterIndex = 0;
	
	public int getIterIndex()
	{
		return iterIndex;
	}
	
	private class Itr implements Iterator<T>
	{
		int index;
		int lastRet = -1;
		
		@Override
		public boolean hasNext()
		{
			return index != getSize();
		}

		@Override
		public T next()
		{
			int i = index;
			
			if (i >= getSize())
				throw new NoSuchElementException();
			
            iterIndex = index;
            
            index = i + 1;
            
            lastRet = index;
            
			return (T) array.get(i);
		}
		
		@Override
		public void remove()
		{
			SGArray.this.remove(lastRet);
			lastRet = -1;
		}
		
	}
}
