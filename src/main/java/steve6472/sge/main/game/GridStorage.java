package steve6472.sge.main.game;

import java.util.HashMap;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 30.9.2018
 * Project: 3DTest
 *
 ***********************/
public class GridStorage<T>
{
	private HashMap<Long, T> objects;

	public GridStorage()
	{
		this.objects = new HashMap<>();
	}

	public static long getKey(int x, int z)
	{
		boolean zNegative = z < 0;

		if (zNegative) z = -z;

		long l = (long) x << 32 | (long) z;
		if (zNegative) l = -l;

		return l;
	}

	public T get(int x, int z)
	{
		return objects.get(getKey(x, z));
	}

	public T get(long key)
	{
		return objects.get(key);
	}

	public void put(int x, int z, T obj)
	{
		objects.put(getKey(x, z), obj);
	}

	public void remove(int x, int z)
	{
		objects.remove(getKey(x, z));
	}

	public HashMap<Long, T> getMap()
	{
		return objects;
	}
}
