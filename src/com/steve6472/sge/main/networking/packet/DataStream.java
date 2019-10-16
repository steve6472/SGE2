/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 10. 2. 2018
* Project: SCR2
*
***********************/

package com.steve6472.sge.main.networking.packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DataStream implements Serializable
{
	private static final long serialVersionUID = 6171502508214339154L;
	
	private List<Object> data;
	
	public DataStream()
	{
		data = new ArrayList<>();
	}
	
	/*
	 * Writing
	 */
	
	public DataStream writeByte(byte o) 			{ data.add(o); return this; }
	public DataStream writeByteArr(byte[] a) 		{ data.add(a); return this; }
	
	public DataStream writeBoolean(boolean o) 		{ data.add(o); return this; }
	public DataStream writeBooleanArr(boolean[] a) 	{ data.add(a); return this; }
	
	public DataStream writeChar(char o) 			{ data.add(o); return this; }
	public DataStream writeCharArr(char[] a) 		{ data.add(a); return this; }
	
	public DataStream writeString(String o) 		{ data.add(o); return this; }
	public DataStream writeStringArr(String[] a) 	{ data.add(a); return this; }
	
	public DataStream writeDouble(double o) 		{ data.add(o); return this; }
	public DataStream writeDoubleArr(double[] a)	{ data.add(a); return this; }
	
	public DataStream writeFloat(float o) 			{ data.add(o); return this; }
	public DataStream writeFloatArr(float[] a)		{ data.add(a); return this; }
	
	public DataStream writeInt(int o) 				{ data.add(o); return this; }
	public DataStream writeIntArr(int[] a) 			{ data.add(a); return this; }
	
	public DataStream writeLong(long o) 			{ data.add(o); return this; }
	public DataStream writeLongArr(long[] a) 		{ data.add(a); return this; }
	
	public DataStream writeObject(Object o) 		{ data.add(o); return this; }
	public DataStream writeObjectArr(Object[] a) 	{ data.add(a); return this; }
	
	/*
	 * Reading
	 */
	
	public byte readByte() 				{ return read(Byte.class); }
	public byte[] readByteArr() 		{ return read(byte[].class); }
	
	public boolean readBoolean() 		{ return read(Boolean.class); }
	public boolean[] readBooleanArr() 	{ return read(boolean[].class); }
	
	public char readChar() 				{ return read(Character.class); }
	public char[] readCharArr() 		{ return read(char[].class); }
	
	public String readString() 			{ return read(String.class); }
	public String[] readStringArr() 	{ return read(String[].class); }
	
	public double readDouble() 			{ return read(Double.class); }
	public double[] readDoubleArr() 	{ return read(double[].class); }
	
	public float readFloat() 			{ return read(Float.class); }
	public float[] readFloatArr() 		{ return read(float[].class); }
	
	public int readInt() 				{ return read(Integer.class); }
	public int[] readIntArr() 			{ return read(int[].class); }
	
	public long readLong() 				{ return read(Long.class); }
	public long[] readLongArr() 		{ return read(long[].class); }
	
	public Object readObject() 			{ return read(Object.class); }
	public Object[] readObjectArr() 	{ return read(Object[].class); }
	
	/*
	 * Methods
	 */
	
	@SuppressWarnings("unchecked")
	private <T> T read(Class<T> clazz)
	{
		for (Iterator<Object> iter = data.iterator(); iter.hasNext();)
		{
			Object o = iter.next();
			
			if (o == null)
			{
				iter.remove();
				return null;
			}
			
			if (clazz.isInstance(o))
			{
				iter.remove();
				return (T) o;
			}
		}
		return null;
	}
	
	public boolean isEmpty()
	{
		return data.isEmpty();
	}
	
	public void printData()
	{
		System.out.println(Arrays.toString(data.toArray()));
	}
	
	public List<Object> getData()
	{
		return data;
	}
	
	

}
