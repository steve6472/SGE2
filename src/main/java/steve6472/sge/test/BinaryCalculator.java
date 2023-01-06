package steve6472.sge.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**********************
 * Created by steve6472
 * On date: 24.11.2018
 * Project: SJP
 *
 ***********************/
public class BinaryCalculator
{
	public static void main(String[] args)
	{
		Number n = new Number(toBooleanArray("10111000111"));

		System.out.println("010111000111");
		System.out.println("001010101101");
		n.add(toBooleanArray("001010101101"));
		System.out.println(n.toBinaryString());
		System.out.println(n.toDecimalString());



		System.out.println();
		System.out.println("010111000111  #");
		System.out.println("001010101101  #");
		System.out.println("100001110100  #");

		System.out.println("\n------------\n");
		n.set(Number.set(toBooleanArray("10111000111")));

		System.out.println("10111000111");
		System.out.println("01010101101");
		n.sub(toBooleanArray("01010101101"));
		System.out.println(n.toBinaryString());
		System.out.println(n.toDecimalString());

		System.out.println();
		System.out.println("10111000111  #");
		System.out.println("01010101101  #");
		System.out.println("01100011010  #");

		System.out.println(Number.toBinaryString(Number.set(fromHexToBin("A2"))));
	}

	private static boolean[] toBooleanArray(String binaryNumber)
	{
		boolean[] b = new boolean[binaryNumber.length()];
		for (int i = 0; i < binaryNumber.length(); i++)
		{
			char c = binaryNumber.charAt(i);
			b[i] = c != '0';
		}
		return b;
	}

	private static boolean[] fromHexToBin(String hex)
	{
		boolean[] b = new boolean[hex.length() * 4];

		hex.toUpperCase();
		for (int i = 0; i < hex.length(); i++)
		{
			char c = hex.charAt(i);
			switch (c)
			{
				case '0': { b[4 * i] = false; b[4 * i + 1] = false; b[4 * i + 2] = false; b[4 * i + 3] = false; break; }
				case '1': { b[4 * i] = false; b[4 * i + 1] = false; b[4 * i + 2] = false; b[4 * i + 3] = true; break; }
				case '2': { b[4 * i] = false; b[4 * i + 1] = false; b[4 * i + 2] = true; b[4 * i + 3] = false; break; }
				case '3': { b[4 * i] = false; b[4 * i + 1] = false; b[4 * i + 2] = true; b[4 * i + 3] = true; break; }
				case '4': { b[4 * i] = false; b[4 * i + 1] = true; b[4 * i + 2] = false; b[4 * i + 3] = false; break; }
				case '5': { b[4 * i] = false; b[4 * i + 1] = true; b[4 * i + 2] = false; b[4 * i + 3] = true; break; }
				case '6': { b[4 * i] = false; b[4 * i + 1] = true; b[4 * i + 2] = true; b[4 * i + 3] = false; break; }
				case '7': { b[4 * i] = false; b[4 * i + 1] = true; b[4 * i + 2] = true; b[4 * i + 3] = true; break; }
				case '8': { b[4 * i] = true; b[4 * i + 1] = false; b[4 * i + 2] = false; b[4 * i + 3] = false; break; }
				case '9': { b[4 * i] = true; b[4 * i + 1] = false; b[4 * i + 2] = false; b[4 * i + 3] = true; break; }
				case 'A': { b[4 * i] = true; b[4 * i + 1] = false; b[4 * i + 2] = true; b[4 * i + 3] = false; break; }
				case 'B': { b[4 * i] = true; b[4 * i + 1] = false; b[4 * i + 2] = true; b[4 * i + 3] = true; break; }
				case 'C': { b[4 * i] = true; b[4 * i + 1] = true; b[4 * i + 2] = false; b[4 * i + 3] = false; break; }
				case 'D': { b[4 * i] = true; b[4 * i + 1] = true; b[4 * i + 2] = false; b[4 * i + 3] = true; break; }
				case 'E': { b[4 * i] = true; b[4 * i + 1] = true; b[4 * i + 2] = true; b[4 * i + 3] = false; break; }
				case 'F': { b[4 * i] = true; b[4 * i + 1] = true; b[4 * i + 2] = true; b[4 * i + 3] = true; break; }
			}
		}

		return b;
	}
}

class Number
{
	private List<Boolean> booleans;

	Number(boolean... basicValue)
	{
		booleans = set(basicValue);
	}

	void set(List<Boolean> value)
	{
		booleans = value;
	}

	static List<Boolean> set(boolean... value)
	{
		List<Boolean> booleans = new ArrayList<>();

		for (boolean b : value)
			booleans.add(b);
		return booleans;
	}

	void sub(boolean... number)
	{
		boolean[] inverted = invert(number);
		add(inverted);
		booleans.remove(0);
		add(true);
	}

	/**
	 *
	 * @param a num0
	 * @param b num1
	 * @return boolean[] { S, C }
	 */
	private boolean[] halfSum(boolean a, boolean b)
	{
		return new boolean[] {a ^ b, a && b};
	}

	/**
	 *
	 * @param a num0
	 * @param b num1
	 * @param c carry
	 * @return boolean[] { S, C }
	 */
	private boolean[] sum(boolean a, boolean b, boolean c)
	{
		boolean x = a ^ b;
		return new boolean[] { x ^ c, (b && a) || (c && x) };
	}

	void add(boolean... number)
	{
		List<Boolean> bs = new ArrayList<>(booleans);
		List<Boolean> ns = new ArrayList<>();
		for (boolean b : number)
			ns.add(b);

		Collections.reverse(bs);
		Collections.reverse(ns);

		List<Boolean> smaller;
		int difference;
		if (bs.size() < ns.size())
		{
			difference = ns.size() - bs.size();
			smaller = bs;
		} else
		{
			difference = bs.size() - ns.size();
			smaller = ns;
		}

		for (int i = 0; i < difference; i++)
		{
			smaller.add(false);
		}

		List<Boolean> out = new ArrayList<>(bs.size());

		boolean carry;

		boolean[] temp = halfSum(bs.get(0), ns.get(0));
		carry = temp[1];
		out.add(temp[0]);

		for (int i = 1; i < bs.size(); i++)
		{
			boolean b = bs.get(i);
			boolean n = ns.get(i);

			temp = sum(b, n, carry);
			carry = temp[1];
			out.add(temp[0]);
		}

		if (carry)
			out.add(true);

		Collections.reverse(out);
		booleans = out;
	}

	private boolean[] invert(boolean... booleans)
	{
		for (int i = 0; i < booleans.length; i++)
		{
			booleans[i] = !booleans[i];
		}

		return booleans;
	}

	private char toChar(boolean b)
	{
		return b ? '1' : '0';
	}

	static String toBinaryString(List<Boolean> booleans)
	{
		StringBuilder sb = new StringBuilder();
		for (boolean b : booleans)
		{
			sb.append(b ? "1" : "0");
		}
		return sb.toString();
	}

	String toBinaryString()
	{
		return toBinaryString(booleans);
	}

	public String toDecimalString()
	{
		String one = "0";

		Collections.reverse(booleans);

		for (int i = 0; i < booleans.size(); i++)
		{
			if (booleans.get(i))
			{
				one = StringNumberManipulator.plus(one, StringNumberManipulator.pow("2", "" + i));
			}
		}

		Collections.reverse(booleans);

		return one;
	}
}

class StringNumberManipulator
{
	public static String pow(String one, String two)
	{
		String r = one;
		String loop = "0";

		//		System.out.println(one + " pow " + two);

		if (two.equals("0"))
		{
			//			System.out.println("Returning one");
			return "1";
		}

		if (two.equals("1"))
		{
			return one;
		}

		for (;;)
		{
			loop = plus("1", loop);

			if (loop.equals(two))
				break;

			//			System.out.println(loop + "/" + two + "   " + r);

			r = multiply(r, one);

			if (two.equals("0"))
				break;
		}

		return r;
	}

	public static String multiply(String one, String two)
	{
		String r = one;
		String loop = "0";

		for (;;)
		{
			loop = plus("1", loop);

			if (loop.equals(two))
				break;

			r = plus(r, one);
		}

		return r;
	}

	public static String plus(String one, String two)
	{
		//First is always the initial smaller one
		StringBuilder first;

		//Second is always the initial bigger one
		String second;

		if (one.length() > two.length())
		{
			first = new StringBuilder(new StringBuilder(two).reverse().toString());
			second = new StringBuilder(one).reverse().toString();
		} else
		{
			first = new StringBuilder(new StringBuilder(one).reverse().toString());
			second = new StringBuilder(two).reverse().toString();
		}

		int loop = second.length() - first.length();

		for (int i = 0; i < loop; i++)
		{
			first.append("0");
		}

		int buffer = 0;

		StringBuilder result = new StringBuilder();

		for (int i = 0; i < second.length(); i++)
		{
			int o = Integer.parseInt("" + first.charAt(i));
			int t = Integer.parseInt("" + second.charAt(i));

			String r = "" + (o + t + buffer);

			if (r.length() == 1)
			{
				buffer = 0;
			}

			if (r.length() > 1 && i < (second.length() - 1))
			{
				buffer = Integer.parseInt("" + r.charAt(0));

				r = "" + r.charAt(1);
			}

			result.insert(0, r);
		}

		return result.toString();
	}
}