package steve6472.sge.main.game.registry;

import steve6472.sge.main.game.Id;

import java.util.function.Supplier;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/17/2021
 * Project: VoxWorld
 *
 ***********************/
public class RegistryObject<T extends ID> implements Comparable<RegistryObject<T>>
{
	private final Supplier<T> constructor;
	private T object;
	private Id id;

	public RegistryObject(Supplier<T> constructor)
	{
		this.constructor = constructor;
	}

	void setId(Id id)
	{
		this.id = id;
	}

	public Id getId()
	{
		return id;
	}

	public void create()
	{
		set(constructor.get());
		object.setId(id);
	}

	public T get()
	{
		return object;
	}

	public void set(T t)
	{
		object = t;
	}

	@Override
	public String toString()
	{
		return "RegistryObject{" + "constructor=" + (constructor == null ? null : "<...>") + ", id=" + id + ", object=" + object + '}';
	}

	public static <T extends ID> RegistryObject<T> of(Id id, Supplier<Class<T>> classSupplier)
	{
		return Registry.getRegistry_(classSupplier.get()).getObject(id);
	}

	public static <T extends ID> RegistryObject<T> of(Id id, Registry<T> registry)
	{
		return registry.getObject(id);
	}

	@Override
	public int compareTo(RegistryObject<T> o)
	{
		return o.getId().toString().compareTo(id.toString());
	}
}
