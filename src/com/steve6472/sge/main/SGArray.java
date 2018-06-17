/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 5. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SGArray<T> implements Iterable<T>
{
	Object[] array;
	int size = 0;
	
	public SGArray(int initialSize)
	{
		this.array = new Object[initialSize];
	}
	
	/**
	 * Default configuration
	 * initialSize = 0
	 * isDynamic = true
	 * fullNull = false
	 */
	public SGArray()
	{
		this.array = new Object[0];
	}
	
	public void setObject(int index, T o) 	{ set(index, o); }
	public T getObject(int index) 			{ return get(index); }
	public void addObject(T o) 				{ add(o); }
	
	public void set(int index, T o)
	{
		checkSize(index);
		array[index] = o;
	}
	
	@SuppressWarnings("unchecked")
	public T get(int index)
	{
		checkSize(index);
		return (T) array[index];
	}
	
	public void add(T o)
	{
		setSize(size + 1);
		array[size++] = o;
	}
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    
	public void setSize(int newSize)
	{
        int oldCapacity = array.length;
        
        if (oldCapacity - newSize > 0)
        	return;
        
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        
        if (newCapacity - newSize < 0)
            newCapacity = newSize;
        
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(newSize);
        
		array = Arrays.copyOf(array, newCapacity);
	}

	private static int hugeCapacity(int minCapacity)
	{
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}
	
	public void removeNulls()
	{
		SArray nulls = new SArray(0, true, false);
		
		for (int i = 0; i < getSize(); i++)
		{
			if (array[i] == null)
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
		array = new Object[0];
		size = 0;
	}
	
	public void clear(int newSize)
	{
		array = new Object[newSize];
		size = 0;
	}
	
	public void reverseArray()
	{
		for (int i = 0; i < array.length / 2; i++)
		{
			Object temp = array[i];
			array[i] = array[getSize() - i - 1];
			array[getSize() - i - 1] = temp;
		}
	}
	
	private void checkSize(int index)
	{
		if (index > size || index < 0)
			throw new ArrayIndexOutOfBoundsException(index);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> toList()
	{
		List<T> list = new ArrayList<T>();
		for (Object t : array)
		{
			list.add((T) t);
		}
		return list;
	}

	
	public void remove(int index)
	{
		int numMoved = size - index - 1;
		
		if (numMoved > 0)
			System.arraycopy(array, index + 1, array, index, numMoved);
		
		array[--size] = null;
	}
	
	public SGArray<T> copy()
	{
		SGArray<T> a = new SGArray<T>(getSize());
		a.array = array.clone();
		return a;
	}
	
	public void printContent()
	{
		for (Object o : array)
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

	@Override
	public Iterator<T> iterator()
	{
		return new Itr();
	}
	
	public int getSize()
	{
		return size;
	}
	
	private int iterIndex = 0;
	
	public int getIterIndex()
	{
		return iterIndex;
	}
	
	private class Itr implements Iterator<T>
	{
		int index;
		
		@Override
		public boolean hasNext()
		{
			return index != getSize();
		}

		@SuppressWarnings("unchecked")
		@Override
		public T next()
		{
			int i = index;
			
			if (i >= getSize())
				throw new NoSuchElementException();
			
            if (i >= array.length)
                throw new ConcurrentModificationException();
            
            iterIndex = index;
            
            index = i + 1;
            
			return (T) array[i];
		}
		
		@Override
		public void remove()
		{
			throw new ConcurrentModificationException();
		}
		
	}
}
