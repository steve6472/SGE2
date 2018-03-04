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
		data = new ArrayList<Object>();
	}
	
	/*
	 * Writing
	 */
	
	public void writeBoolean(boolean o) { data.add(o); }
	public void writeBooleanArr(boolean[] a) { data.add(a); }
	
	public void writeChar(char o) { data.add(o); }
	public void writeCharArr(char[] a) { data.add(a); }
	
	public void writeString(String o) { data.add(o); }
	public void writeStringArr(String[] a) { data.add(a); }
	
	public void writeDouble(double o) { data.add(o); }
	public void writeDoubleArr(double[] a) { data.add(a); }
	
	public void writeInt(int o) { data.add(o); }
	public void writeIntArr(int[] a) { data.add(a); }
	
	public void writeLong(long o) { data.add(o); }
	public void writeLongArr(long[] a) { data.add(a); }
	
	public void writeObject(Object o) { data.add(o); }
	public void writeObjectArr(Object[] a) { data.add(a); }
	
	/*
	 * Reading
	 */
	
	public boolean readBoolean() { return (boolean) read(Boolean.class); }
	public boolean[] readBooleanArr() { return (boolean[]) read(boolean[].class); }
	
	public char readChar() { return (char) read(Character.class); }
	public char[] readCharArr() { return (char[]) read(char[].class); }
	
	public String readString() { return (String) read(String.class); }
	public String[] readStringArr() { return (String[]) read(String[].class); }
	
	public double readDouble() { return (double) read(Double.class); }
	public double[] readDoubleArr() { return (double[]) read(double[].class); }
	
	public int readInt() { return (int) read(Integer.class); }
	public int[] readIntArr() { return (int[]) read(int[].class); }
	
	public long readLong() { return (long) read(Long.class); }
	public long[] readLongArr() { return (long[]) read(long[].class); }
	
	public Object readObject() { return (Object) read(Object.class); }
	public Object[] readObjectArr() { return (Object[]) read(Object[].class); }
	
	/*
	 * Methods
	 */
	
	private Object read(Class<?> clazz)
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
				return o;
			}
		}
		return null;
	}
	
	public void printData()
	{
		System.out.println(Arrays.toString(data.toArray()));
	}
	
	

}
