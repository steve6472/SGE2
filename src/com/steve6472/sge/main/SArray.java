/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 5. 4. 2018
* Project: SGE2
*
***********************/

package com.steve6472.sge.main;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class SArray implements Iterable<Object>
{
	List<Object> array;
	
	public SArray(int initialSize)
	{
		array = new ArrayList<Object>(initialSize);
	}
	
	public SArray()
	{
		array = new ArrayList<Object>();
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
	
	public void setSArray(int index, Object o) 		{ set(index, o); }
	public Object getSArray(int index) 				{ return get(SArray.class, index); }
	public void addSArray(Object o) 				{ add(o); }
	
	public void setSArrayArr(int index, SArray[] o) { set(index, o); }
	public SArray[] getSArrayArr(int index) 		{ return get(SArray[].class, index); }
	public void addSArrayArr(SArray[] o) 			{ add(o); }
	
	private void set(int index, Object o)
	{
		if (index <= array.size())
			array.add(null);
		array.set(index, o);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T get(Class<T> clazz, int index)
	{
		if (array.get(index) == null)
			throw new NullPointerException("Element at " + index + " is null, expected " + clazz.getName());
		
		if (clazz.isInstance(array.get(index)))
			return (T) array.get(index);
		else
			throw new ClassCastException(array.get(index).getClass().getName() + " is not instance of " + clazz.getName());
	}
	
	private void add(Object o)
	{
		array.add(o);
	}
	
	public void removeNulls()
	{
		SArray nulls = new SArray(0);
		
		for (int i = 0; i < array.size(); i++)
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
	
	public void reverseArray()
	{
		for (int i = 0; i < array.size() / 2; i++)
		{
			Object temp = array.get(i);
			array.set(i, array.get(array.size() - i - 1));
			array.set(array.size() - i - 1, temp);
		}
	}

	public void remove(int index)
	{
		array.remove(index);
	}
	
	public void swap(int index1, int index2)
	{
		Object temp = getObject(index1);
		setObject(index1, getObject(index2));
		setObject(index2, temp);
	}
	
	public SArray copy()
	{
		SArray a = new SArray(array.size());
		for (Object o : array)
		{
			a.add(o);
		}
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
	
	public int getSize()
	{
		return array.size();
	}
	
	private class Itr implements Iterator<Object>
	{
		int index;
		
		@Override
		public boolean hasNext()
		{
			return index != array.size();
		}

		@Override
		public Object next()
		{
			int i = index;
			
            if (i >= array.size())
                throw new ConcurrentModificationException();
            
            index = i + 1;
            
			return array.get(i);
		}
		
		@Override
		public void remove()
		{
			throw new ConcurrentModificationException();
		}
		
	}
}
