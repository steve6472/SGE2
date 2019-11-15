package com.steve6472.sge.main.smartsave;

import com.steve6472.sge.main.game.DataType;

import java.io.*;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 04.07.2019
 * Project: SJP
 *
 ***********************/
public class SmartSave
{
	private static DataInputStream dataInputStream;
	private static DataOutputStream dataOutputStream;

	private static boolean readFully;
	private static HashMap<String, Object> readData;

	static
	{
		readData = new HashMap<>();
	}

	public static void openInput(File path) throws IOException
	{
		closeOutput();
		dataInputStream = new DataInputStream(new GZIPInputStream(new FileInputStream(path)));
	}

	public static void openOutput(File path) throws IOException
	{
		closeInput();
		dataOutputStream = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(path)));
	}

	public static void closeInput() throws IOException
	{
		readFully = false;
		readData.clear();
		if (dataInputStream != null)
		dataInputStream.close();
	}

	public static void closeOutput() throws IOException
	{
		if (dataOutputStream != null)
		dataOutputStream.close();
	}

	public static void writeData(String name, Object data) throws IOException
	{
		byte[] nameBytes = name.getBytes();

		dataOutputStream.writeByte(DataType.getDataType(data).ordinal());
		dataOutputStream.writeShort(nameBytes.length);
		dataOutputStream.write(nameBytes);
		writeData(dataOutputStream, data);
	}

	public static void readFull() throws IOException
	{
		readFully = true;

		while (dataInputStream.available() > 0)
		{
			int type = dataInputStream.readByte();
			int nameLength = dataInputStream.readShort();
			byte[] nameBytes = new byte[nameLength];
			dataInputStream.readFully(nameBytes);
			String name = new String(nameBytes);
			int valueLength = dataInputStream.readInt();
			Object val = switch (type)
					{
						case 0 -> dataInputStream.readBoolean();
						case 1 -> dataInputStream.readByte();
						case 2 -> dataInputStream.readChar();
						case 3 -> dataInputStream.readShort();
						case 4 -> dataInputStream.readInt();
						case 5 -> dataInputStream.readLong();
						case 6 -> dataInputStream.readFloat();
						case 7 -> dataInputStream.readDouble();
						case 8 -> {
							byte[] s = new byte[valueLength];
							dataInputStream.readFully(s);
							break new String(s);
						}
						case 11 -> {
							int[] i = new int[valueLength];
							for (int j = 0; j < i.length; j++)
							{
								i[j] = dataInputStream.readInt();
							}
							break i;
						}
						case 16 -> {
							short[] i = new short[valueLength];
							for (int j = 0; j < i.length; j++)
							{
								i[j] = dataInputStream.readShort();
							}
							break i;
						}
						default -> null;
					};
			readData.put(name, val);
		}
	}

	public static Object get(String name)
	{
		if (!readFully) throw new IllegalStateException("Data has not been fully read!");
		return readData.get(name);
	}

	private static void writeData(DataOutputStream os, Object value) throws IOException
	{
		switch(DataType.getDataType(value))
		{
			case BOOLEAN -> {os.writeInt(1); os.writeBoolean((boolean) value);}
			case BYTE -> {os.writeInt(1); os.writeByte((byte) value);}
			case CHAR -> {os.writeInt(2); os.writeChar((char) value);}
			case SHORT -> {os.writeInt(2); os.writeShort((short) value);}
			case INT -> {os.writeInt(4); os.writeInt((int) value);}
			case LONG -> {os.writeInt(8); os.writeLong((long) value);}
			case FLOAT -> {os.writeInt(4); os.writeFloat((float) value);}
			case DOUBLE -> {os.writeInt(8); os.writeDouble((double) value);}
			case STRING -> {
				os.writeInt(((String) value).getBytes().length);
				os.write(((String) value).getBytes());
			}
			case INTARRAY -> {
				int[] a = ((int[]) value);
				os.writeInt(a.length);
				for (int item : a)
				{
					os.writeInt(item);
				}
			}
			case SHORTARRAY -> {
				short[] a = ((short[]) value);
				os.writeInt(a.length);
				for (int item : a)
				{
					os.writeShort(item);
				}
			}
			default -> throw new IllegalStateException("Unexpected value: " + DataType.getDataType(value));
		}
	}
}
