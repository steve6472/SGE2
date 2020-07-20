package steve6472.sge.test;

import steve6472.sge.main.smartsave.SmartSave;

import java.io.*;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 15.04.2020
 * Project: StevesGameEngine
 *
 ***********************/
public class SSTest
{
	public static void main(String[] args) throws IOException
	{
		test2D();
	}

	private static void test2D() throws IOException
	{
		File test = new File("ss.txt");
		if (!test.exists())
			//noinspection ResultOfMethodCallIgnored
			test.createNewFile();

		int[][] a = {{8, 1, 2}, {2, 5, 1}, {1, 2, 0}};

		SmartSave.openOutput(test);
		SmartSave.writeData("array", a);
		SmartSave.closeOutput();

		SmartSave.openInput(test);
		SmartSave.readFull();
		int[][] array = (int[][]) SmartSave.get("array");
		SmartSave.closeInput();

		boolean compared;

		System.out.println("Comparing: " + (compared = compare(a, array)));

		if (compared)
		{
			for (int i = 0; i < a.length; i++)
			{
				System.out.println(Arrays.toString(a[i]));
			}
			System.out.println("-".repeat(16));
			for (int i = 0; i < array.length; i++)
			{
				System.out.println(Arrays.toString(array[i]));
			}
		}

		dump(test);

		test.delete();
	}

	private static void test3D() throws IOException
	{
		File test = new File("ss.txt");
		if (!test.exists())
			//noinspection ResultOfMethodCallIgnored
			test.createNewFile();

		int[][][] a = {{{0, 1, 2},{3, 4, 5},{6, 7, 8}}, {{9, 10, 11},{12, 13, 14},{15, 16, 17}}, {{18, 19, 20},{21, 22, 23},{24, 25, 26}}};

		SmartSave.openOutput(test);
		SmartSave.writeData("array", a);
		SmartSave.closeOutput();

		SmartSave.openInput(test);
		SmartSave.readFull();
		int[][][] array = (int[][][]) SmartSave.get("array");
		SmartSave.closeInput();

		boolean compared;

		System.out.println("Comparing: " + (compared = compare(a, array)));

		if (compared)
		{
			for (int i = 0; i < a.length; i++)
			{
				for (int j = 0; j < a[i].length; j++)
				{
					System.out.println(Arrays.toString(a[i][j]));
				}
			}
			System.out.println("-".repeat(16));
			for (int i = 0; i < array.length; i++)
			{
				for (int j = 0; j < array[i].length; j++)
				{
					System.out.println(Arrays.toString(array[i][j]));
				}
			}
		}

		dump(test);

		test.delete();
	}

	private static void dump(File path) throws IOException
	{
		DataInputStream in = new DataInputStream(new GZIPInputStream(new FileInputStream(path)));

		StringBuilder hex = new StringBuilder();
		hex.append("\n");

		int i = 0;

		while (in.available() > 0)
		{
			byte b = in.readByte();
			hex.append(String.format("%02X ", b));
			i++;
			if (i > 15)
			{
				i = 0;
				hex.append("\n");
			}
		}

		System.out.println(hex.toString());
	}

	private static boolean compare(int[][] a0, int[][] a1)
	{
		if (a0.length != a1.length)
			return false;

		for (int i = 0; i < a0.length; i++)
		{
			if (a0[i].length != a1[i].length)
				return false;

			for (int j = 0; j < a0[i].length; j++)
			{
				if (a0[i][j] != a1[i][j])
					return false;
			}
		}

		return true;
	}

	private static boolean compare(int[][][] a0, int[][][] a1)
	{
		if (a0.length != a1.length)
			return false;

		for (int i = 0; i < a0.length; i++)
		{
			if (a0[i].length != a1[i].length)
				return false;

			for (int j = 0; j < a0[i].length; j++)
			{
				if (a0[i][j].length != a1[i][j].length)
					return false;

				for (int k = 0; k < a0[i][j].length; k++)
				{
					if (a0[i][j][k] != a1[i][j][k])
						return false;
				}
			}
		}

		return true;
	}
}
