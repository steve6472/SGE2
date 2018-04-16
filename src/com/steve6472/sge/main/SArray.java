/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 5. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.steve6472.sge.main.game.Vec2;

public class SArray implements Iterable<Object>
{
	int size;
	Object[] array;
	boolean isDynamic;
	boolean fillNull;
	
	public static void main(String[] args)
	{
		SArray arr = new SArray(5, true, false);
		
		arr.setObject(2, new Vec2(0, 0));
		
		arr.setObjectArr(1, new Object[] {0, 5, 9, new Vec2(9, 1)});
		
		arr.addObject(new Vec2(1, 1));
		
		arr.setFillNull(true);
		
		arr.addObject(new Vec2(2, 2));
		
		arr.printContent();
		
		arr.remove(2);
		arr.removeNulls();
		
		System.out.println("\n------REMOVE-------\n");
		
		
		for (Object o : arr)
		{
			if (o instanceof Object[])
				System.out.println(Arrays.toString((Object[]) o));
			else
				System.out.println(o);
		}
	}
	
	public SArray(int initialSize, boolean isDynamic, boolean fillNull)
	{
		this.size = initialSize;
		this.array = new Object[initialSize];
		this.isDynamic = isDynamic;
	}

	public void setByte(int index, byte o) 			{ set(index, o); }
	public byte getByte(int index) 					{ return get(Byte.class, index); }
	public void addByte(byte o) 					{ add(o); }
	public byte matByte(int index, byte o)			{ set(index, getByte(index) + o); return getByte(index); }

	public void setByteArr(int index, byte[]o) 		{ set(index, o); }
	public byte[] getByteArr(int index) 			{ return get(byte[].class, index); }
	public void addByteArr(byte o) 					{ add(o); }
	
	public void setBoolean(int index, boolean o) 	{ set(index, o); }
	public boolean getBoolean(int index)			{ return get(Boolean.class, index); }
	public void addBoolean(boolean o) 				{ add(o); }
	
	public void setBooleanArr(int index, boolean[] o){ set(index, o); }
	public boolean[] getBooleanArr(int index)		{ return get(boolean[].class, index); }
	public void addBooleanArr(boolean o) 			{ add(o); }
	
	public void setChar(int index, char o) 			{ set(index, o); }
	public char getChar(int index) 					{ return get(Character.class, index); }
	public void addChar(char o) 					{ add(o); }
	
	public void setCharArr(int index, char[] o) 	{ set(index, o); }
	public char[] getCharArr(int index) 			{ return get(char[].class, index); }
	public void addCharArr(char o) 					{ add(o); }
	
	public void setString(int index, String o) 		{ set(index, o); }
	public String getString(int index) 				{ return get(String.class, index); }
	public void addString(String o) 				{ add(o); }
	public String matString(int index, String o)	{ set(index, getString(index) + o); return getString(index); }
	
	public void setStringArr(int index, String[] o) { set(index, o); }
	public String[] getStringArr(int index) 		{ return get(String[].class, index); }
	public void addStringArr(String o) 				{ add(o); }
	
	public void setDouble(int index, double o) 		{ set(index, o); }
	public double getDouble(int index) 				{ return get(Double.class, index); }
	public void addDouble(double o)					{ add(o); }
	public double matDouble(int index, double o)	{ set(index, getDouble(index) + o); return getDouble(index); }
	
	public void setDoubleArr(int index, double[] o) { set(index, o); }
	public double[] getDoubleArr(int index) 		{ return get(double[].class, index); }
	public void addDoubleArr(double o)				{ add(o); }
	
	public void setFloat(int index, float o) 		{ set(index, o); }
	public float getFloat(int index) 				{ return get(Float.class, index); }
	public void addFloat(float o)					{ add(o); }
	public float matFloat(int index, float o)		{ set(index, getFloat(index) + o); return getFloat(index); }
	
	public void setFloatArr(int index, float[] o) 	{ set(index, o); }
	public float[] getFloatArr(int index) 			{ return get(float[].class, index); }
	public void addFloatArr(double o)				{ add(o); }
	
	public void setInt(int index, int o) 			{ set(index, o); }
	public int getInt(int index) 					{ return get(Integer.class, index); }
	public void addInt(int o) 						{ add(o); }
	public int matInt(int index, int o)				{ set(index, getInt(index) + o); return getInt(index); }
	
	public void setIntArr(int index, int[] o) 		{ set(index, o); }
	public int[] getIntArr(int index) 				{ return get(int[].class, index); }
	public void addIntArr(int o) 					{ add(o); }
	
	public void setLong(int index, long o) 			{ set(index, o); }
	public long getLong(int index) 					{ return get(Long.class, index); }
	public void addLong(long o) 					{ add(o); }
	public long matLong(int index, long o)			{ set(index, getLong(index) + o); return getLong(index); }
	
	public void setLongArr(int index, long[] o) 	{ set(index, o); }
	public long[] getLongArr(int index) 			{ return get(long[].class, index); }
	public void addLongArr(long o) 					{ add(o); }
	
	public void setObject(int index, Object o) 		{ set(index, o); }
	public Object getObject(int index) 				{ return get(Object.class, index); }
	public void addObject(Object o) 				{ add(o); }
	
	public void setObjectArr(int index, Object[] o) { set(index, o); }
	public Object[] getObjectArr(int index) 		{ return get(Object[].class, index); }
	public void addObjectArr(Object[] o) 			{ add(o); }
	
	private void set(int index, Object o)
	{
		checkSize(index);
		array[index] = o;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T get(Class<T> clazz, int index)
	{
		checkSize(index);
		
		if (array[index] == null)
			throw new NullPointerException("Element at " + index + " is null, expected " + clazz.getName());
		
		if (clazz.isInstance(array[index]))
			return (T) array[index];
		else
			throw new ClassCastException(array[index].getClass().getName() + " is not instance of " + clazz.getName());
	}
	
	private void add(Object o)
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
	
	public SArray copy()
	{
		SArray a = new SArray(size, isDynamic, fillNull);
		a.array = array.clone();
		return a;
	}
	
	public void printContent()
	{
		for (Object o : array)
			System.out.println(o);
	}

	@Override
	public Iterator<Object> iterator()
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
	
	private class Itr implements Iterator<Object>
	{
		int index;
		
		@Override
		public boolean hasNext()
		{
			return index != size;
		}

		@Override
		public Object next()
		{
			int i = index;
			
			if (i >= size)
				throw new NoSuchElementException();
			
            if (i >= array.length)
                throw new ConcurrentModificationException();
            
            index = i + 1;
            
			return array[i];
		}
		
		@Override
		public void remove()
		{
			throw new ConcurrentModificationException();
		}
		
	}
}
