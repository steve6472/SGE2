package com.steve6472.sge.main;

import com.steve6472.sge.main.util.RandomUtil;
import org.joml.Vector2f;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Util
{
	public static final int HOVERED_OVERLAY = 0x807f87be;
	public static final int SELECTED_OVERLAY = 0x806d76ad;
	public static final double PYThAGORASRATIO = 1.4142135623730950488016887242097;
	private static final Random random;
	//1,4142135623730950488016887242097
	
	static
	{
		random = new Random();
	}
	
	public static String getFormatedTime()
	{
		return String.format("%tY.%tm.%te-%tH.%tM.%tS", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance());
	}

	public static boolean isEven(int number)
	{
		return number % 2 == 0;
	}

	public static double getDistance(int fromX, int fromY, int toX, int toY)
	{
		double a = -((fromY) - (toY));
		double b = -((fromX) - (toX));
		
		return Math.sqrt((a * a) + (b * b));
	}

	public static double getDistance(double fromX, double fromY, double toX, double toY)
	{
		double a = -((fromY) - (toY));
		double b = -((fromX) - (toX));

		return Math.sqrt((a * a) + (b * b));
	}

	public static float getDistance(float fromX, float fromY, float toX, float toY)
	{
		float a = -((fromY) - (toY));
		float b = -((fromX) - (toX));

		return (float) Math.sqrt((a * a) + (b * b));
	}

	/**
	 * @return distance between 2 3d points
	 * Copied from https://stackoverflow.com/a/30599011
	 */
	public static double getDistance(double fx, double fy, double fz, double tx, double ty, double tz)
	{
		return Math.sqrt(Math.pow(fx - tx, 2) + Math.pow(fy - ty, 2) + Math.pow(fz - tz, 2));
	}

	/**
	 * @return distance between 2 3d points
	 * Copied from https://stackoverflow.com/a/30599011
	 */
	public static float getDistance(float fx, float fy, float fz, float tx, float ty, float tz)
	{
		return (float) Math.sqrt(Math.pow(fx - tx, 2) + Math.pow(fy - ty, 2) + Math.pow(fz - tz, 2));
	}


	/**
	 * 
	 * @param fromX From X
	 * @param fromY From Y
	 * @param toX To X
	 * @param toY To Y
	 * @return angle
	 */
	public static double countAngle(double fromX, double fromY, double toX, double toY)
	{
		return -Math.toDegrees(Math.atan2(fromX - toX, fromY - toY));
	}
	
	public static boolean isInRectangle(int rminx, int rminy, int rmaxx, int rmaxy, int px, int py)
	{
		return px >= rminx && px <= rmaxx && py >= rminy && py <= rmaxy;
	}
	
	public static boolean isInRectangle(double rminx, double rminy, double rmaxx, double rmaxy, double px, double py)
	{
		return px >= rminx && px <= rmaxx && py >= rminy && py <= rmaxy;
	}

	public static boolean isCursorInRectangle(MainApp main, int x, int y, int w, int h)
	{
		return (main.getMouseX() >= x && main.getMouseX() <= w + x) && (main.getMouseY() >= y && main.getMouseY() <= h + y);
	}
	
	public static int getBiggestClosetsSqrt(int count)
	{
		int r = count;
		double temp = Math.sqrt(r);
		int iteration = 0;
		while (true)
		{
			if (temp == Math.floor(temp))
			{
				return (int) temp;
			} else
			{
				r++;
				temp = Math.sqrt(r);
			}
			iteration++;
			if (iteration >= 256)
			{
				System.err.println("More than 256 iterations!");
				return count;
			}
		}
	}
	
	public static boolean isNumber(String l)
	{
		try
		{
			Long.valueOf(l);
			return true;
		} catch (NumberFormatException ex)
		{
			return false;
		}
	}

	public static boolean isPointNumber(String l)
	{
		try
		{
			Double.parseDouble(l);
			return true;
		} catch (NumberFormatException ex)
		{
			return false;
		}
	}
	
	public static boolean isNumberDouble(String l)
	{
		try
		{
			Double.valueOf(l);
			return true;
		} catch (NumberFormatException ex)
		{
			return false;
		}
	}
	
	public static boolean isNumberInRange(double min, double max, double number)
	{
		return (number >= min && number <= max);
	}

	public static boolean isNumberInRange(int min, int max, int number)
	{
		return (number >= min && number <= max);
	}
	
	public static int getIntFromHex(String hex)
	{
		return (int) Long.parseLong(hex, 16);
	}

	public static String substringEnd(String string, int endIndex)
	{
		return string.substring(0, string.length() - endIndex);
	}

	public static Object[] combine(Object[] arr1, Object[] arr2)
	{
		int length = arr1.length + arr2.length;
		Object[] res = new Object[length];
		System.arraycopy(arr1, 0, res, 0, arr1.length);
		System.arraycopy(arr2, 0, res, arr1.length, arr2.length);
		return res;
	}
	
	/**
	 * Not recomended
	 */
	public static void sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static double clamp(double min, double max, double number)
	{
		return Math.min(Math.max(number, min), max);
	}

	public static float clamp(float min, float max, float number)
	{
		return Math.min(Math.max(number, min), max);
	}

	public static int clamp(int min, int max, int number)
	{
		return Math.min(Math.max(number, min), max);
	}

	public static String[] loadDataFromFile(String path)
	{
		return loadDataFromFile(new File(path));
	}
	
	public static String[] loadDataFromFile(File path)
	{
		List<String> lines = new ArrayList<>();

		if (!path.exists())
		{
			try
			{
				path.createNewFile();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			boolean endOfTheFile = false;
			while (!endOfTheFile)
			{
				String line = br.readLine();

				if (line == null)
				{
					endOfTheFile = true;
				} else 
				{
					lines.add(line);
				}
			}

			br.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return lines.toArray(new String[0]);
	}
	
	public static void save(File file, String...text)
	{
		try (PrintWriter out = new PrintWriter(file))
		{
			for (String s : text)
			{
				out.println(s);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void saveS(File file, Serializable...text)
	{
		try (PrintWriter out = new PrintWriter(file))
		{
			for (Serializable s : text)
			{
				out.println(toString(s));
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static int[] getIntArrayWithRandomValues(int sizeX, int sizeY, int min, int max)
	{
		int[] i = new int[sizeX * sizeY];
		
		for (int j = 0; j < i.length; j++)
		{
			i[j] = RandomUtil.randomInt(min, max);
		}
		
		return i;
	}


	/**
	 * Stolen from https://stackoverflow.com/a/134918
	 * @param s overlays
	 */
	public static Object fromString(String s)
	{
		byte[] data = Base64.getDecoder().decode(s);
		ObjectInputStream ois;
		Object o = null;
		try
		{
			ois = new ObjectInputStream(new ByteArrayInputStream(data));
			o = ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * Stolen from https://stackoverflow.com/a/134918
	 * @param o
	 * @return
	 * @throws IOException
	 */
	public static String toString(Serializable o)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try
		{
			oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			oos.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(baos.toByteArray());
	}

	public static void printObjects(Object...os)
	{
		for (Object s : os)
		{
			System.out.print(s + " ");
		}
		System.out.println();
	}

	public static void printObjectsHash(Object...os)
	{
		for (Object s : os)
		{
			if (s == null)
				System.out.print(s + " ");
			else
				System.out.print(s.hashCode() + " ");
		}
		System.out.println();
	}
	
	public static void removeNullsFromArray(Object[] arr)
	{
		List<Object> arr2 = new ArrayList<Object>();
		
		for (Object o : arr)
		{
			if (o != null)
				arr2.add(o);
		}
		
		arr = arr2.toArray();
	}

	
	@FunctionalInterface
	public interface DoubleInterface<C, I>
	{
		public void apply(C c, I i);
	}


	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param maxX
	 * @param maxY
	 * @param minX
	 * @param minY
	 * @param di x + i, y + j
	 */
	public static void fillRect(int x, int y, int width, int height, int maxX, int maxY, int minX, int minY, DoubleInterface<Integer, Integer> di)
	{
		
		// Ignore 0 width or height
		if (width == 0 || height == 0)
			return;
			
		// If Width is less than zero -> invert
		if (width < 0)
		{
			x = width + x;
			width = -width;
		}
			
		// If Height is less than zero -> invert
		if (height < 0)
		{
			y = height + y;
			height = -height;
		}
		
		// Don't renderSprite if x or y are outside selected box
		// Can be here even thou original width or height were negative cuz I changed it.
		if (x > maxX) return;
		if (y > maxY) return;

		// Limit to screen size
		if (x < minX) { width  = width  - minX + x; x = minX; }
		if (y < minY) { height = height - minY + y; y = minY; }
		if (x + width  > maxX) { width  -= x   + width  - maxX; }
		if (y + height > maxY) { height -= y   + height - maxY; }
		
		// Ignore 0 width or height again cuz of that math up there could mess it up
		if (width == 0 || height == 0)
			return;

		// ComponentRender
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				di.apply(i + x, j + y);
			}
		}
	}

	public static long locationValue(int x, int y)
	{
		long seed = y;
		seed = x + (seed << 32); // make x and z semi-independent parts of the seed
		Random r = new Random(seed);
		return RandomUtil.randomLong(Long.MIN_VALUE, Long.MAX_VALUE, r.nextLong());
	}
	
	public static int maxi(int a, int b)
	{
		return Math.max(a, b);
	}
	
	public static double maxd(double a, double b)
	{
		return Math.max(a, b);
	}
	
	public static float maxf(float a, float b)
	{
		return Math.max(a, b);
	}
	
	public static long maxl(long a, long b)
	{
		return Math.max(a, b);
	}
	
	public static int mini(int a, int b)
	{
		return Math.min(a, b);
	}
	
	public static double mind(double a, double b)
	{
		return Math.min(a, b);
	}
	
	public static float minf(float a, float b)
	{
		return Math.min(a, b);
	}
	
	public static long minl(long a, long b)
	{
		return Math.min(a, b);
	}
	
	public static double getRandomAngle()
	{
		return RandomUtil.randomDouble(0, 360);
	}

	public static double getRandomAngleRAD()
	{
		return RandomUtil.randomDouble(0, 2 * Math.PI);
	}

	/**
	 * Code stolen from https://gist.github.com/yfnick/227e0c12957a329ad138
	 * Thank you for creating this code!
	 * @param data
	 * @return
	 */
	public static byte[] compress(String data)
	{
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data.getBytes());
			gzip.close();
			byte[] compressed = bos.toByteArray();
			bos.close();
			return compressed;
		} catch (IOException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Code stolen from https://gist.github.com/yfnick/227e0c12957a329ad138
	 * Thank you for creating this code!
	 * @param compressed
	 * @return
	 */
	public static String decompress(byte[] compressed)
	{
		try
		{
			ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
			GZIPInputStream gis = new GZIPInputStream(bis);
			BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
			}
			br.close();
			gis.close();
			bis.close();
			return sb.toString();
		} catch (IOException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Stolen from @author https://stackoverflow.com/a/80503
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte[] combineArrays(byte[] a, byte[] b) 
	{
	    int aLen = a.length;
	    int bLen = b.length;

	    byte[] c = (byte[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
	/**
	 * Stolen from @author https://stackoverflow.com/a/80503
	 * @param a
	 * @param b
	 * @return
	 */
	public static float[] combineArrays(float[] a, float[] b) 
	{
	    int aLen = a.length;
	    int bLen = b.length;

	    float[] c = (float[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
	public static float[] combineArrays(float[] a, float[]...fs)
	{
		float[] arr = a;
		for (float[] f : fs)
		{
			arr = combineArrays(arr, f);
		}
		return arr;
	}
	
	/**
	 * Stolen from @author https://stackoverflow.com/a/80503
	 * @param a
	 * @param b
	 * @return
	 */
	public static int[] combineArrays(int[] a, int[] b) 
	{
	    int aLen = a.length;
	    int bLen = b.length;

	    int[] c = (int[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
	/**
	 * Stolen from @author https://stackoverflow.com/a/80503
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> T[] combineArrays(T[] a, T[] b) 
	{
	    int aLen = a.length;
	    int bLen = b.length;

	    @SuppressWarnings("unchecked")
		T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
	public static float calculateValue(float time, float endTime, float valueStart, float valueEnd)
	{
//		Util.printObjects("### ", time, endTime, valueStart, valueEnd);
		return (time / endTime) * valueEnd - (time / endTime) * valueStart + valueStart;
	}
	
	public static float bezierCurve(float p0, float p1, float p2, float time, float timeEnd)
	{
		float t0 = time;
		float t1 = timeEnd;

		float y0 = calculateValue(t0, t1, p0, p1);

		float y1 = calculateValue(t0, t1, p1, p2);
		
		float py = calculateValue(t0, t1, y0, y1);
		
		return py;
	}
	
	public static Vector2f bezierCurve(float p0x, float p0y, float p1x, float p1y, float p2x, float p2y, float time, float timeEnd)
	{
		float t0 = time;
		float t1 = timeEnd;

		float x0 = calculateValue(t0, t1, p0x, p1x);
		float y0 = calculateValue(t0, t1, p0y, p1y);

		float x1 = calculateValue(t0, t1, p1x, p2x);
		float y1 = calculateValue(t0, t1, p1y, p2y);
		
		float px = calculateValue(t0, t1, x0, x1);
		float py = calculateValue(t0, t1, y0, y1);
		
		return new Vector2f(px, py);
	}
	
	public static boolean toBoolean(int i)
	{
		return i == 0 ? false : true;
	}
	
	public static int toInt(boolean b)
	{
		return b ? 1 : 0;
	}
	
	public static float normalise(float x, float minX, float maxX)
	{
		return (x - minX) / (maxX - minX);
	}

	public static <T> ArrayList<T> insert(T t, List<T> original, int index)
	{
		List<T> cutLeft = new ArrayList<>();
		for (int i = 0; i < index; i++)
		{
			cutLeft.add(original.get(i));
		}

		List<T> cutRight = new ArrayList<>();
		for (int i = index; i < original.size(); i++)
		{
			cutRight.add(original.get(i));
		}

		ArrayList<T> newArray = new ArrayList<>(cutLeft);
		newArray.add(t);
		newArray.addAll(cutRight);
		
		return newArray;
	}

	public static String getExtension(String fileName)
	{
		char ch;
		int len;
		if (fileName == null ||
				(len = fileName.length()) == 0 ||
				(ch = fileName.charAt(len - 1)) == '/' || ch=='\\' || //in the case of a directory
				ch == '.' ) //in the case of . or ..
			return "";
		int dotInd = fileName.lastIndexOf('.'),
				sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
		if (dotInd <= sepInd)
			return "";
		else
			return fileName.substring(dotInd+1).toLowerCase();
	}

/*
	public static double getRandomCircleX(double radius)
	{
//		double angle = Math.toRadians(Math.random()*Math.PI*2);
//		return (radius * Math.cos(angle * Math.PI / 180d));
		double angle = Math.toRadians(getRandomDouble(360, 0));
		double r = radius * new Random().nextDouble();
		return r * Math.cos(angle);
	}

	public static double getRandomCircleY(double radius)
	{
//		double angle = Math.toRadians(Math.random()*Math.PI*2);
//		return (radius * Math.sin(angle * Math.PI / 180d));
		double angle = Math.toRadians(getRandomDouble(360, 0));
		double r = radius * new Random().nextDouble();
		return r * Math.sin(angle);
	}*/
}
