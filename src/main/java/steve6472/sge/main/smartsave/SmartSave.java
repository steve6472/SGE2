package steve6472.sge.main.smartsave;

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
	private static DataInputStream is;
	private static DataOutputStream os;

	private static boolean readFully;
	private static final HashMap<String, Object> readData;

	static
	{
		readData = new HashMap<>();
	}

	public static void openInput(File path) throws IOException
	{
		closeOutput();
		is = new DataInputStream(new GZIPInputStream(new FileInputStream(path)));
	}

	public static void openOutput(File path) throws IOException
	{
		closeInput();
		os = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(path)));
	}

	public static void setDataInputStream(DataInputStream dataInputStream)
	{
		SmartSave.is = dataInputStream;
	}

	public static void setDataOutputStream(DataOutputStream dataOutputStream)
	{
		SmartSave.os = dataOutputStream;
	}

	public static void closeInput() throws IOException
	{
		readFully = false;
		readData.clear();
		if (is != null)
		is.close();
	}

	public static void closeOutput() throws IOException
	{
		if (os != null)
		os.close();
	}

	public static void writeData(String name, Object data) throws IOException
	{
		byte[] nameBytes = name.getBytes();

		// Write data type
		os.writeByte(DataType.getDataType(data).ordinal());

		// Write name length
		os.writeShort(nameBytes.length);

		// Write name
		os.write(nameBytes);

		// Write data
		writeData(os, data);
	}

	public static void readFull() throws IOException
	{
		while (is.available() > 0)
		{
			int type = is.readByte();
			int nameLength = is.readShort();

			byte[] nameBytes = new byte[nameLength];
			is.readFully(nameBytes);
			String name = new String(nameBytes);

			DataType dataType = DataType.values()[type];

			Object val = switch (dataType)
					{
						case BOOLEAN ->      readBoolean();
						case BYTE ->         readByte();
						case CHAR ->         readChar();
						case SHORT ->        readShort();
						case INT ->          readInt();
						case LONG ->         readLong();
						case FLOAT ->        readFloat();
						case DOUBLE ->       readDouble();
						case STRING ->       readString();
						case STRINGARRAY ->  readStringArray();
						case INTARRAY ->     readIntArray();
						case INTARRAY2D ->   readIntArray2D();
						case INTARRAY3D ->   readIntArray3D();
						case SHORTARRAY ->   readShortArray();
						case SHORTARRAY2D -> readShortArray2D();
						case SHORTARRAY3D -> readShortArray3D();
						default -> throw new IllegalStateException("Unexpected value: " + dataType);
					};
			readData.put(name, val);
		}

		readFully = true;
	}

	public static boolean readBoolean() throws IOException
	{
		is.readByte();
		return is.readBoolean();
	}

	public static byte readByte() throws IOException
	{
		is.readByte();
		return is.readByte();
	}

	public static char readChar() throws IOException
	{
		is.readByte();
		return is.readChar();
	}

	public static short readShort() throws IOException
	{
		is.readByte();
		return is.readShort();
	}

	public static int readInt() throws IOException
	{
		is.readByte();
		return is.readInt();
	}

	public static long readLong() throws IOException
	{
		is.readByte();
		return is.readLong();
	}

	public static float readFloat() throws IOException
	{
		is.readByte();
		return is.readFloat();
	}

	public static double readDouble() throws IOException
	{
		is.readByte();
		return is.readDouble();
	}

	public static String readString() throws IOException
	{
		int length = is.readInt();
		byte[] s = new byte[length];

		is.readFully(s);
		return new String(s);
	}

	public static String[] readStringArray() throws IOException
	{
		int length = is.readInt();
		String[] a = new String[length];

		for (int i = 0; i < length; i++)
		{
			int len = is.readInt();
			byte[] bytes = new byte[len];
			is.readFully(bytes);
			a[i] = new String(bytes);
		}

		return a;
	}

	public static int[] readIntArray() throws IOException
	{
		int length = is.readInt();
		int[] i = new int[length];

		for (int j = 0; j < i.length; j++)
		{
			i[j] = is.readInt();
		}
		return i;
	}

	public static int[][] readIntArray2D() throws IOException
	{
		int len0 = is.readInt();
		int[][] a0 = new int[len0][];

		for (int i = 0; i < len0; i++)
		{
			int len1 = is.readInt();
			int[] a1 = new int[len1];

			for (int j = 0; j < len1; j++)
			{
				a1[j] = is.readInt();
			}
			a0[i] = a1;
		}
		return a0;
	}

	public static int[][][] readIntArray3D() throws IOException
	{
		int len0 = is.readInt();
		int[][][] a0 = new int[len0][][];

		for (int i = 0; i < len0; i++)
		{
			int len1 = is.readInt();
			int[][] a1 = new int[len1][];

			for (int j = 0; j < len1; j++)
			{
				int len2 = is.readInt();
				int[] a2 = new int[len2];

				for (int k = 0; k < len2; k++)
				{
					a2[k] = is.readInt();
				}
				a1[j] = a2;
			}
			a0[i] = a1;
		}
		return a0;
	}

	public static short[] readShortArray() throws IOException
	{
		int length = is.readInt();
		short[] i = new short[length];

		for (int j = 0; j < i.length; j++)
		{
			i[j] = is.readShort();
		}
		return i;
	}

	public static short[][] readShortArray2D() throws IOException
	{
		int len0 = is.readInt();
		short[][] a0 = new short[len0][];

		for (int i = 0; i < len0; i++)
		{
			int len1 = is.readInt();
			short[] a1 = new short[len1];

			for (int j = 0; j < len1; j++)
			{
				a1[j] = is.readShort();
			}
			a0[i] = a1;
		}
		return a0;
	}

	public static short[][][] readShortArray3D() throws IOException
	{
		int len0 = is.readInt();
		short[][][] a0 = new short[len0][][];

		for (int i = 0; i < len0; i++)
		{
			int len1 = is.readInt();
			short[][] a1 = new short[len1][];

			for (int j = 0; j < len1; j++)
			{
				int len2 = is.readInt();
				short[] a2 = new short[len2];

				for (int k = 0; k < len2; k++)
				{
					a2[k] = is.readShort();
				}
				a1[j] = a2;
			}
			a0[i] = a1;
		}
		return a0;
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
			case BOOLEAN ->      writeBoolean(value);
			case BYTE ->         writeByte(value);
			case CHAR ->         writeChar(value);
			case SHORT ->        writeShort(value);
			case INT ->          writeInt(value);
			case LONG ->         writeLong(value);
			case FLOAT ->        writeFloat(value);
			case DOUBLE ->       writeDouble(value);
			case STRING ->       writeString(value);
			case STRINGARRAY ->  writeStringArray(value);
			case INTARRAY ->     writeIntArray(value);
			case INTARRAY2D ->   writeIntArray2D(value);
			case INTARRAY3D ->   writeIntArray3D(value);
			case SHORTARRAY ->   writeShortArray(value);
			case SHORTARRAY2D -> writeShortArray2D(value);
			case SHORTARRAY3D -> writeShortArray3D(value);
			default -> throw new IllegalStateException("Unexpected value: " + DataType.getDataType(value));
		}
	}

	public static void writeBoolean(Object value) throws IOException
	{
		os.writeByte(1);
		os.writeBoolean((boolean) value);
	}

	public static void writeByte(Object value) throws IOException
	{
		os.writeByte(1);
		os.writeByte((byte) value);
	}

	public static void writeChar(Object value) throws IOException
	{
		os.writeByte(2);
		os.writeChar((char) value);
	}

	public static void writeShort(Object value) throws IOException
	{
		os.writeByte(2);
		os.writeShort((short) value);
	}

	public static void writeInt(Object value) throws IOException
	{
		os.writeByte(4);
		os.writeInt((int) value);
	}

	public static void writeLong(Object value) throws IOException
	{
		os.writeByte(8);
		os.writeLong((long) value);
	}

	public static void writeFloat(Object value) throws IOException
	{
		os.writeByte(4);
		os.writeFloat((float) value);
	}

	public static void writeDouble(Object value) throws IOException
	{
		os.writeByte(8);
		os.writeDouble((double) value);
	}

	public static void writeString(Object value) throws IOException
	{
		String s = (String) value;
		byte[] bytes = s.getBytes();

		os.writeInt(bytes.length);
		os.write(bytes);
	}

	public static void writeStringArray(Object value) throws IOException
	{
		String[] a = (String[]) value;

		os.writeInt(a.length);
		for (String s : a)
		{
			byte[] bytes = s.getBytes();
			os.writeInt(bytes.length);
			os.write(bytes);
		}
	}

	public static void writeIntArray(Object value) throws IOException
	{
		int[] a = (int[]) value;
		os.writeInt(a.length);

		for (int item : a)
		{
			os.writeInt(item);
		}
	}

	public static void writeIntArray2D(Object value) throws IOException
	{
		int[][] a = (int[][]) value;
		os.writeInt(a.length);

		for (int i = 0; i < a.length; i++)
		{
			os.writeInt(a[i].length);
			for (int j = 0; j < a[i].length; j++)
			{
				os.writeInt(a[i][j]);
			}
		}
	}

	public static void writeIntArray3D(Object value) throws IOException
	{
		int[][][] a = (int[][][]) value;
		os.writeInt(a.length);

		for (int i = 0; i < a.length; i++)
		{
			os.writeInt(a[i].length);
			for (int j = 0; j < a[i].length; j++)
			{
				os.writeInt(a[i][j].length);
				for (int k = 0; k < a[i].length; k++)
				{
					os.writeInt(a[i][j][k]);
				}
			}
		}
	}

	public static void writeShortArray(Object value) throws IOException
	{
		short[] a = ((short[]) value);
		os.writeInt(a.length);

		for (int item : a)
		{
			os.writeShort(item);
		}
	}

	public static void writeShortArray2D(Object value) throws IOException
	{
		short[][] a = (short[][]) value;
		os.writeInt(a.length);

		for (int i = 0; i < a.length; i++)
		{
			os.writeInt(a[i].length);
			for (int j = 0; j < a[i].length; j++)
			{
				os.writeShort(a[i][j]);
			}
		}
	}

	public static void writeShortArray3D(Object value) throws IOException
	{
		short[][][] a = (short[][][]) value;
		os.writeInt(a.length);

		for (int i = 0; i < a.length; i++)
		{
			os.writeInt(a[i].length);
			for (int j = 0; j < a[i].length; j++)
			{
				os.writeInt(a[i][j].length);
				for (int k = 0; k < a[i].length; k++)
				{
					os.writeShort(a[i][j][k]);
				}
			}
		}
	}
}
