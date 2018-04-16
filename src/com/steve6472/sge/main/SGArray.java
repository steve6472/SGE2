/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 5. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SGArray<T> implements Iterable<T>
{
	int size;
	Object[] array;
	boolean isDynamic;
	boolean fillNull;
	
	public SGArray(int initialSize, boolean isDynamic, boolean fillNull)
	{
		this.size = initialSize;
		this.array = new Object[initialSize];
		this.isDynamic = isDynamic;
		this.fillNull = fillNull;
	}
	
	/**
	 * Default configuration
	 * initialSize = 0
	 * isDynamic = true
	 * fullNull = false
	 */
	public SGArray()
	{
		this.size = 0;
		this.array = new Object[0];
		this.isDynamic = true;
		this.fillNull = false;
	}
	
	public void setObject(int index, T o) 	{ set(index, o); }
	public T getObject(int index) 			{ return get(index); }
	public void addObject(T o) 				{ add(o); }
	
	private void set(int index, T o)
	{
		checkSize(index);
		array[index] = o;
	}
	
	@SuppressWarnings("unchecked")
	private T get(int index)
	{
		checkSize(index);
		return (T) array[index];
	}
	
	private void add(T o)
	{
		if (fillNull)
		{
			int nullIndex = -1;
			for (int i = 0; i < size - 1; i++)
			{
				if (array[i] == null)
				{
					nullIndex = i;
					break;
				}
			}
			if (nullIndex == -1)
			{
				setSize(size + 1);
				array[size - 1] = o;
			} else
			{
				array[nullIndex] = o;
			}
		}
		else
		{
			setSize(size + 1);
			array[size - 1] = o;
		}
	}
	
	public void removeNulls()
	{
		SArray nulls = new SArray(0, true, false);
		
		for (int i = 0; i < size; i++)
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
	
	public void reverseArray()
	{
		for (int i = 0; i < array.length / 2; i++)
		{
			Object temp = array[i];
			array[i] = array[size - i - 1];
			array[size - i - 1] = temp;
		}
	}
	
	private void checkSize(int index)
	{
		if (size < 0 || (!isDynamic && index > this.size))
			throw new ArrayIndexOutOfBoundsException(index);
		
		if (isDynamic && index > this.size)
			setSize(index);
	}
	
	public void setSize(int newSize)
	{
		this.size = newSize;
		
		Object[] newArray = new Object[newSize];
		
		System.arraycopy(array, 0, newArray, 0, newSize - 1);
		
		array = newArray;
	}
	
	public void remove(int index)
	{
		if (index == 0)
		{
			Object[] newArray = new Object[size - 1];
			
			System.arraycopy(array, 1, newArray, 0, size - 1);
			
			this.array = newArray;
		} else if (index == size - 1)
		{
			Object[] newArray = new Object[size - 1];
			
			System.arraycopy(array, 0, newArray, 0, size - 1);
			
			this.array = newArray;
		} else
		{
			Object[] newArray = new Object[size - 1];
			
			int left = -((size - 1) - (size - 1) - index);
			int right = (size - 1) - index;

//			Util.printObjects("Left:", left, "\nRight:", right);
			
			Object[] leftArray = new Object[left];
			Object[] rightArray = new Object[right];

			System.arraycopy(array, 0, leftArray, 0, left);
			System.arraycopy(array, index + 1, rightArray, 0, right);

			newArray = Util.combineArrays(leftArray, rightArray);
			
			this.array = newArray;
		}
		
		size -= 1;
	}
	
	public SGArray<T> copy()
	{
		SGArray<T> a = new SGArray<T>(size, isDynamic, fillNull);
		a.array = array.clone();
		return a;
	}
	
	public void printContent()
	{
		for (Object o : array)
			System.out.println(o);
	}

	@Override
	public Iterator<T> iterator()
	{
		return new Itr();
	}
	
	public void setFillNull(boolean fillNull)
	{
		this.fillNull = fillNull;
	}
	
	public void setDynamic(boolean isDynamic)
	{
		this.isDynamic = isDynamic;
	}
	
	public boolean isDynamic()
	{
		return isDynamic;
	}
	
	public boolean isFillNull()
	{
		return fillNull;
	}
	
	public int getSize()
	{
		return size;
	}
	
	private class Itr implements Iterator<T>
	{
		int index;
		
		@Override
		public boolean hasNext()
		{
			return index != size;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T next()
		{
			int i = index;
			
			if (i >= size)
				throw new NoSuchElementException();
			
            if (i >= array.length)
                throw new ConcurrentModificationException();
            
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
